package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CourseTrack
import com.example.data.CurriculumRepository
import com.example.data.DownloadState
import com.example.data.SubjectItem
import com.example.download.CurriculumDownloadManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AkisssViewModel(application: Application) : AndroidViewModel(application) {

    val downloadManager = CurriculumDownloadManager(application.applicationContext)

    private val _selectedYear = MutableStateFlow(1)
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTrack = MutableStateFlow(CourseTrack.ALL)
    val selectedTrack: StateFlow<CourseTrack> = _selectedTrack.asStateFlow()

    private val _downloadStates = MutableStateFlow<Map<String, DownloadState>>(emptyMap())
    val downloadStates: StateFlow<Map<String, DownloadState>> = _downloadStates.asStateFlow()

    private val _selectedSubjectForDetail = MutableStateFlow<SubjectItem?>(null)
    val selectedSubjectForDetail: StateFlow<SubjectItem?> = _selectedSubjectForDetail.asStateFlow()

    private val _downloadHistory = MutableStateFlow<List<SubjectItem>>(emptyList())
    val downloadHistory: StateFlow<List<SubjectItem>> = _downloadHistory.asStateFlow()

    private val _userFeedbackMessage = MutableStateFlow<String?>(null)
    val userFeedbackMessage: StateFlow<String?> = _userFeedbackMessage.asStateFlow()

    // Pre-grouped subjects per year (1, 2, 3) filtered on Dispatchers.Default for lag-free horizontal paging
    val subjectsByYear: StateFlow<Map<Int, List<SubjectItem>>> = combine(
        _searchQuery,
        _selectedTrack
    ) { query, track ->
        val trimmedQuery = query.trim()
        val year1 = ArrayList<SubjectItem>(20)
        val year2 = ArrayList<SubjectItem>(20)
        val year3 = ArrayList<SubjectItem>(20)

        for (subject in CurriculumRepository.subjects) {
            val matchesTrack = track == CourseTrack.ALL || subject.courseTrack == track
            if (!matchesTrack) continue

            val matchesQuery = if (trimmedQuery.isEmpty()) {
                true
            } else {
                subject.title.contains(trimmedQuery, ignoreCase = true) ||
                    subject.curriculumCode.contains(trimmedQuery, ignoreCase = true) ||
                    subject.description.contains(trimmedQuery, ignoreCase = true) ||
                    subject.courseTrack.displayName.contains(trimmedQuery, ignoreCase = true) ||
                    subject.topics.any { it.contains(trimmedQuery, ignoreCase = true) }
            }

            if (matchesQuery) {
                when (subject.year) {
                    1 -> year1.add(subject)
                    2 -> year2.add(subject)
                    3 -> year3.add(subject)
                }
            }
        }

        mapOf(1 to year1, 2 to year2, 3 to year3)
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            mapOf(
                1 to CurriculumRepository.getSubjectsForYear(1),
                2 to CurriculumRepository.getSubjectsForYear(2),
                3 to CurriculumRepository.getSubjectsForYear(3)
            )
        )

    val filteredSubjects: StateFlow<List<SubjectItem>> = combine(
        _selectedYear,
        subjectsByYear
    ) { year, byYear ->
        byYear[year].orEmpty()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CurriculumRepository.getSubjectsForYear(1)
    )

    init {
        downloadManager.onDownloadStarted = { subject ->
            updateDownloadState(subject.id, DownloadState.DOWNLOADING)
            _userFeedbackMessage.value = "Download started: ${subject.title} (Year ${subject.year}) PDF"
        }

        downloadManager.onDownloadCompleted = { subject, _ ->
            updateDownloadState(subject.id, DownloadState.COMPLETED)
            addToHistory(subject)
            _userFeedbackMessage.value = "Download complete: ${subject.title} saved to Downloads!"
        }

        downloadManager.onDownloadFailed = { subject, reason ->
            updateDownloadState(subject.id, DownloadState.FAILED)
            _userFeedbackMessage.value = "Download failed: $reason"
        }

        refreshDownloadedFilesStatus()
    }

    fun selectYear(year: Int) {
        if (_selectedYear.value != year) {
            _selectedYear.value = year
        }
    }

    fun updateSearchQuery(query: String) {
        if (_searchQuery.value != query) {
            _searchQuery.value = query
        }
    }

    fun selectTrack(track: CourseTrack) {
        if (_selectedTrack.value != track) {
            _selectedTrack.value = track
        }
    }

    fun selectSubjectForDetail(subject: SubjectItem?) {
        _selectedSubjectForDetail.value = subject
    }

    fun clearFeedbackMessage() {
        _userFeedbackMessage.value = null
    }

    fun downloadSubjectPdf(subject: SubjectItem) {
        viewModelScope.launch {
            updateDownloadState(subject.id, DownloadState.DOWNLOADING)
            downloadManager.enqueueDownloadAsync(subject)
        }
    }

    fun openSubjectPdf(subject: SubjectItem) {
        val opened = downloadManager.openDownloadedPdf(subject)
        if (!opened) {
            _userFeedbackMessage.value = "File not yet downloaded or could not be opened."
        }
    }

    fun openSubjectUrlInBrowser(subject: SubjectItem) {
        downloadManager.openUrlInBrowser(subject.downloadUrl)
    }

    fun refreshDownloadedFilesStatus() {
        viewModelScope.launch {
            val freshStates = downloadManager.checkAllDownloadStatesAsync(CurriculumRepository.subjects)
            val current = _downloadStates.value
            // Preserve active DOWNLOADING states and only emit if values actually changed
            val merged = freshStates.toMutableMap()
            for ((id, state) in current) {
                if (state == DownloadState.DOWNLOADING) {
                    merged[id] = DownloadState.DOWNLOADING
                }
            }
            if (merged != current) {
                _downloadStates.value = merged
            }
        }
    }

    private fun updateDownloadState(subjectId: String, state: DownloadState) {
        val current = _downloadStates.value
        if (current[subjectId] == state) return
        _downloadStates.value = current.toMutableMap().apply {
            put(subjectId, state)
        }
    }

    private fun addToHistory(subject: SubjectItem) {
        val current = _downloadHistory.value
        if (!current.any { it.id == subject.id }) {
            _downloadHistory.value = listOf(subject) + current
        }
    }

    override fun onCleared() {
        super.onCleared()
        downloadManager.unregisterReceiver()
    }
}
