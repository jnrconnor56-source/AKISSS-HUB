package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.CourseTrack
import com.example.data.DownloadState
import com.example.data.SubjectItem
import com.example.ui.components.CurriculumDetailDialog
import com.example.ui.components.SubjectCard
import com.example.ui.theme.AkisssBlue
import com.example.ui.theme.AkisssBlueDark
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.GhanaGold
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private val YearTabs = listOf(
    1 to "Year 1 (SHS 1)",
    2 to "Year 2 (SHS 2)",
    3 to "Year 3 (SHS 3)"
)

private val AllTracks = CourseTrack.entries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AkisssMainScreen(
    viewModel: AkisssViewModel,
    onOpenDownloadsFolder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedYear by viewModel.selectedYear.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedTrack by viewModel.selectedTrack.collectAsStateWithLifecycle()
    val subjectsByYear by viewModel.subjectsByYear.collectAsStateWithLifecycle()
    val downloadStates by viewModel.downloadStates.collectAsStateWithLifecycle()
    val detailSubject by viewModel.selectedSubjectForDetail.collectAsStateWithLifecycle()
    val feedbackMessage by viewModel.userFeedbackMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = (selectedYear - 1).coerceIn(0, 2),
        pageCount = { YearTabs.size }
    )

    // Keep ViewModel selectedYear synced smoothly with HorizontalPager
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                viewModel.selectYear(page + 1)
            }
    }

    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearFeedbackMessage()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BackgroundLight,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // ================= HEADER (#1e3a8a) =================
            Surface(
                color = AkisssBlue,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_akisss_logo),
                            contentDescription = "AKISSS School Crest",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "AKISSS Student Hub",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 0.3.sp
                            )
                            Text(
                                text = "Official GES SHS Curriculum Center",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFBAE6FD)
                            )
                            Text(
                                text = "Akim Swedru Senior High School • Anansekrom",
                                fontSize = 10.sp,
                                color = Color(0xFF93C5FD),
                                maxLines = 1
                            )
                        }

                        IconButton(
                            onClick = onOpenDownloadsFolder,
                            modifier = Modifier
                                .testTag("btn_open_downloads_folder")
                                .size(48.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(Color(0xFF1E40AF), shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Folder,
                                    contentDescription = "Open Downloads Directory",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AkisssBlueDark,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Official Repo: curriculumresources.edu.gh",
                                fontSize = 11.sp,
                                color = Color(0xFFE0F2FE)
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = GhanaGold
                            ) {
                                Text(
                                    text = "Core & Elective Programs",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // ================= SMOOTH YEAR TABS =================
            Surface(
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.White,
                    contentColor = AkisssBlue,
                    indicator = {
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                            height = 3.dp,
                            color = AkisssBlue
                        )
                    }
                ) {
                    YearTabs.forEachIndexed { index, (yearNum, label) ->
                        val isSelected = pagerState.currentPage == index
                        Tab(
                            selected = isSelected,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = if (isSelected) AkisssBlue else TextSecondary,
                                    maxLines = 1
                                )
                            },
                            modifier = Modifier.testTag("tab_year_$yearNum")
                        )
                    }
                }
            }

            // ================= SEARCH & PROGRAM TRACK FILTERS =================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    placeholder = {
                        Text(
                            text = "Search Physics, Chemistry, Accounting, Economics...",
                            fontSize = 13.sp,
                            color = TextTertiary,
                            maxLines = 1
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = AkisssBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = AkisssBlue,
                        unfocusedBorderColor = Color(0xFFCBD5E1)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("input_search_subjects")
                )

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(AllTracks, key = { it.name }) { track ->
                        val isSelected = selectedTrack == track
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectTrack(track) },
                            label = {
                                Text(
                                    text = track.displayName,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AkisssBlue,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = TextSecondary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) AkisssBlue else Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier.testTag("chip_filter_${track.name}")
                        )
                    }
                }
            }

            // ================= SWIPEABLE YEAR PAGES =================
            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { pageIndex ->
                val pageYear = pageIndex + 1
                val pageSubjects = subjectsByYear[pageYear].orEmpty()

                YearSubjectsPage(
                    year = pageYear,
                    selectedTrack = selectedTrack,
                    searchQuery = searchQuery,
                    subjects = pageSubjects,
                    downloadStates = downloadStates,
                    onDownloadClick = viewModel::downloadSubjectPdf,
                    onOpenClick = viewModel::openSubjectPdf,
                    onDetailsClick = viewModel::selectSubjectForDetail
                )
            }
        }
    }

    // Detail Dialog
    detailSubject?.let { subject ->
        val state = downloadStates[subject.id] ?: DownloadState.READY
        CurriculumDetailDialog(
            subject = subject,
            downloadState = state,
            onDismiss = { viewModel.selectSubjectForDetail(null) },
            onDownloadClick = { viewModel.downloadSubjectPdf(subject) },
            onOpenClick = { viewModel.openSubjectPdf(subject) },
            onOpenBrowserClick = { viewModel.openSubjectUrlInBrowser(subject) }
        )
    }
}

@Composable
private fun YearSubjectsPage(
    year: Int,
    selectedTrack: CourseTrack,
    searchQuery: String,
    subjects: List<SubjectItem>,
    downloadStates: Map<String, DownloadState>,
    onDownloadClick: (SubjectItem) -> Unit,
    onOpenClick: (SubjectItem) -> Unit,
    onDetailsClick: (SubjectItem) -> Unit
) {
    val listState = rememberLazyListState()

    if (subjects.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (searchQuery.isNotBlank()) {
                        "No subjects match '$searchQuery'"
                    } else {
                        "No subjects available in ${selectedTrack.displayName}"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Try searching for Physics, Chemistry, Biology, Economics, Accounting...",
                    fontSize = 13.sp,
                    color = TextTertiary
                )
            }
        }
    } else {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 20.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag(if (year == 1) "list_core_subjects" else "list_subjects_year_$year")
        ) {
            item(key = "header_$year", contentType = "banner") {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFEFF6FF),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DownloadDone,
                            contentDescription = null,
                            tint = AkisssBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Year $year • ${if (selectedTrack == CourseTrack.ALL) "All Core & Elective Syllabuses" else selectedTrack.displayName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AkisssBlue
                            )
                            Text(
                                text = "Downloads are saved directly to device /Downloads with system notifications",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }

            items(
                items = subjects,
                key = { it.id },
                contentType = { "subject_card" }
            ) { subject ->
                val state = downloadStates[subject.id] ?: DownloadState.READY
                val downloadAction = remember(subject.id) { { onDownloadClick(subject) } }
                val openAction = remember(subject.id) { { onOpenClick(subject) } }
                val detailsAction = remember(subject.id) { { onDetailsClick(subject) } }

                SubjectCard(
                    subject = subject,
                    downloadState = state,
                    onDownloadClick = downloadAction,
                    onOpenClick = openAction,
                    onDetailsClick = detailsAction
                )
            }
        }
    }
}
