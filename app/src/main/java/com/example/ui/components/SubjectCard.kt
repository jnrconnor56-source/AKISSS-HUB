package com.example.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CourseTrack
import com.example.data.DownloadState
import com.example.data.SubjectItem
import com.example.ui.theme.AkisssBlue
import com.example.ui.theme.AkisssBlueContainer
import com.example.ui.theme.EducationGreen
import com.example.ui.theme.GhanaGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Immutable
private data class TrackVisualStyle(
    val bgColor: Color,
    val textColor: Color,
    val icon: ImageVector
)

private val TopicChipShape = RoundedCornerShape(12.dp)
private val CardShape = RoundedCornerShape(16.dp)
private val BadgeShape = RoundedCornerShape(6.dp)
private val IconBoxShape = RoundedCornerShape(12.dp)
private val ActionButtonShape = RoundedCornerShape(10.dp)
private val TopicChipColor = Color(0xFFF1F5F9)
private val TopicTextColor = Color(0xFF334155)
private val DefaultBorderColor = Color(0xFFE2E8F0)
private val CompletedBorderColor = EducationGreen.copy(alpha = 0.5f)
private val OutlineBorderColor = Color(0xFFCBD5E1)
private val ErrorColor = Color(0xFFDC2626)

private fun trackVisualStyleFor(track: CourseTrack): TrackVisualStyle = when (track) {
    CourseTrack.CORE -> TrackVisualStyle(AkisssBlueContainer, AkisssBlue, Icons.Default.School)
    CourseTrack.GENERAL_SCIENCE -> TrackVisualStyle(Color(0xFFECFDF5), EducationGreen, Icons.Default.Science)
    CourseTrack.GENERAL_ARTS -> TrackVisualStyle(Color(0xFFFEF3C7), GhanaGold, Icons.Default.MenuBook)
    CourseTrack.BUSINESS -> TrackVisualStyle(Color(0xFFEFF6FF), Color(0xFF1D4ED8), Icons.Default.Business)
    CourseTrack.VISUAL_ARTS -> TrackVisualStyle(Color(0xFFF3E8FF), Color(0xFF7E22CE), Icons.Default.Palette)
    CourseTrack.HOME_ECONOMICS -> TrackVisualStyle(Color(0xFFFCE7F3), Color(0xFFBE185D), Icons.Default.Restaurant)
    CourseTrack.AGRICULTURE -> TrackVisualStyle(Color(0xFFDCFCE7), Color(0xFF15803D), Icons.Default.Eco)
    CourseTrack.ALL -> TrackVisualStyle(AkisssBlueContainer, AkisssBlue, Icons.Default.School)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubjectCard(
    subject: SubjectItem,
    downloadState: DownloadState,
    onDownloadClick: () -> Unit,
    onOpenClick: () -> Unit,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable(subject.id) { mutableStateOf(false) }
    val style = remember(subject.courseTrack) { trackVisualStyleFor(subject.courseTrack) }
    val displayTopics = remember(subject.id, isExpanded) {
        if (isExpanded) subject.topics else subject.topics.take(3)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("card_${subject.id}"),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (downloadState == DownloadState.COMPLETED) CompletedBorderColor else DefaultBorderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
                .padding(16.dp)
        ) {
            // Header: Subject Icon, Title, Year Badge & Program Track Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(IconBoxShape)
                        .background(style.bgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = style.icon,
                        contentDescription = subject.title,
                        tint = style.textColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = subject.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Surface(
                            shape = BadgeShape,
                            color = style.bgColor
                        ) {
                            Text(
                                text = subject.courseTrack.displayName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = style.textColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = subject.curriculumCode,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "• Year ${subject.year}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AkisssBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Short Description
            Text(
                text = subject.description,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp,
                maxLines = if (isExpanded) 10 else 2,
                overflow = TextOverflow.Ellipsis
            )

            // Topics preview
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                displayTopics.forEach { topic ->
                    Box(
                        modifier = Modifier
                            .clip(TopicChipShape)
                            .background(TopicChipColor)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = topic,
                            fontSize = 11.sp,
                            color = TopicTextColor
                        )
                    }
                }
                if (!isExpanded && subject.topics.size > 3) {
                    Text(
                        text = "+${subject.topics.size - 3} more",
                        fontSize = 11.sp,
                        color = AkisssBlue,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clip(BadgeShape)
                            .clickable { isExpanded = true }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = TopicChipColor)
            Spacer(modifier = Modifier.height(10.dp))

            // Meta Row: File size, source domain, and download status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "PDF • ${subject.fileSizeFormatted} • ${subject.pagesCount} pages",
                    fontSize = 11.sp,
                    color = TextTertiary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    when (downloadState) {
                        DownloadState.READY -> {
                            Text(
                                text = "curriculumresources.edu.gh",
                                fontSize = 11.sp,
                                color = TextTertiary
                            )
                        }
                        DownloadState.DOWNLOADING -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                strokeWidth = 2.dp,
                                color = AkisssBlue
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Downloading…",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AkisssBlue
                            )
                        }
                        DownloadState.COMPLETED -> {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Downloaded",
                                tint = EducationGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Saved in Downloads",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = EducationGreen
                            )
                        }
                        DownloadState.FAILED -> {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Failed",
                                tint = ErrorColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Retry Needed",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = ErrorColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons (48.dp minimum interactive height for smooth tapping)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onDetailsClick,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp)
                        .testTag("outline_button_${subject.id}"),
                    shape = ActionButtonShape,
                    border = BorderStroke(1.dp, OutlineBorderColor)
                ) {
                    Text(
                        text = "Outline & Tips",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TopicTextColor
                    )
                }

                if (downloadState == DownloadState.COMPLETED) {
                    Button(
                        onClick = onOpenClick,
                        modifier = Modifier
                            .weight(1.2f)
                            .heightIn(min = 48.dp)
                            .testTag("open_button_${subject.id}"),
                        shape = ActionButtonShape,
                        colors = ButtonDefaults.buttonColors(containerColor = EducationGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FileOpen,
                            contentDescription = "Open PDF",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Open PDF",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Button(
                        onClick = onDownloadClick,
                        enabled = downloadState != DownloadState.DOWNLOADING,
                        modifier = Modifier
                            .weight(1.2f)
                            .heightIn(min = 48.dp)
                            .testTag("download_button_${subject.id}"),
                        shape = ActionButtonShape,
                        colors = ButtonDefaults.buttonColors(containerColor = AkisssBlue)
                    ) {
                        if (downloadState == DownloadState.DOWNLOADING) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Download PDF",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Download PDF",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
