package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CourseTrack
import com.example.data.CurriculumRepository
import com.example.data.DownloadState
import com.example.data.SubjectItem
import com.example.download.CurriculumDownloadManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
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

    val filteredSubjects = combine(
        _selectedYear,
        _searchQuery,
        _selectedTrack
    ) { year, query, track ->
        CurriculumRepository.subjects.filter { subject ->
            val matchesYear = subject.year == year
            val matchesTrack = when (track) {
                CourseTrack.ALL -> true
                else -> subject.courseTrack == track
            }
            val matchesQuery = if (query.isBlank()) {
                true
            } else {
                subject.title.contains(query, ignoreCase = true) ||
                subject.curriculumCode.contains(query, ignoreCase = true) ||
                subject.description.contains(query, ignoreCase = true) ||
                subject.courseTrack.displayName.contains(query, ignoreCase = true) ||
                subject.topics.any { it.contains(query, ignoreCase = true) }
            }
            matchesYear && matchesTrack && matchesQuery
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CurriculumRepository.getSubjectsForYear(1)
    )

    init {
        // Setup download listener callbacks
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
        _selectedYear.value = year
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectTrack(track: CourseTrack) {
        _selectedTrack.value = track
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
            downloadManager.enqueueDownload(subject)
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
            val states = mutableMapOf<String, DownloadState>()
            for (subject in CurriculumRepository.subjects) {
                if (downloadManager.isFileDownloaded(subject)) {
                    states[subject.id] = DownloadState.COMPLETED
                } else {
                    states[subject.id] = DownloadState.READY
                }
            }
            _downloadStates.value = states
        }
    }

    private fun updateDownloadState(subjectId: String, state: DownloadState) {
        _downloadStates.value = _downloadStates.value.toMutableMap().apply {
            put(subjectId, state)
        }
    }

    private fun addToHistory(subject: SubjectItem) {
        val current = _downloadHistory.value.toMutableList()
        if (!current.any { it.id == subject.id }) {
            current.add(0, subject)
            _downloadHistory.value = current
        }
    }

    override fun onCleared() {
        super.onCleared()
        downloadManager.unregisterReceiver()
    }
}
