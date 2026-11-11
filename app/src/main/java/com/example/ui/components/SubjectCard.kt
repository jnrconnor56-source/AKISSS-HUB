package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    var isExpanded by remember { mutableStateOf(false) }

    val trackBg = when (subject.courseTrack) {
        CourseTrack.CORE -> AkisssBlueContainer
        CourseTrack.GENERAL_SCIENCE -> Color(0xFFECFDF5)
        CourseTrack.GENERAL_ARTS -> Color(0xFFFEF3C7)
        CourseTrack.BUSINESS -> Color(0xFFEFF6FF)
        CourseTrack.VISUAL_ARTS -> Color(0xFFF3E8FF)
        CourseTrack.HOME_ECONOMICS -> Color(0xFFFCE7F3)
        CourseTrack.AGRICULTURE -> Color(0xFFDCFCE7)
        else -> AkisssBlueContainer
    }

    val trackTextColor = when (subject.courseTrack) {
        CourseTrack.CORE -> AkisssBlue
        CourseTrack.GENERAL_SCIENCE -> EducationGreen
        CourseTrack.GENERAL_ARTS -> GhanaGold
        CourseTrack.BUSINESS -> Color(0xFF1D4ED8)
        CourseTrack.VISUAL_ARTS -> Color(0xFF7E22CE)
        CourseTrack.HOME_ECONOMICS -> Color(0xFFBE185D)
        CourseTrack.AGRICULTURE -> Color(0xFF15803D)
        else -> AkisssBlue
    }

    val trackIcon = when (subject.courseTrack) {
        CourseTrack.CORE -> Icons.Default.School
        CourseTrack.GENERAL_SCIENCE -> Icons.Default.Science
        CourseTrack.GENERAL_ARTS -> Icons.Default.MenuBook
        CourseTrack.BUSINESS -> Icons.Default.Business
        CourseTrack.VISUAL_ARTS -> Icons.Default.Palette
        CourseTrack.HOME_ECONOMICS -> Icons.Default.Restaurant
        CourseTrack.AGRICULTURE -> Icons.Default.Eco
        else -> Icons.Default.School
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("card_${subject.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (downloadState == DownloadState.COMPLETED) EducationGreen.copy(alpha = 0.5f) else Color(0xFFE2E8F0)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Subject Icon, Title, Year Badge & Program Track Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leading Badge
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(trackBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = trackIcon,
                        contentDescription = subject.title,
                        tint = trackTextColor,
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
                            shape = RoundedCornerShape(6.dp),
                            color = trackBg
                        ) {
                            Text(
                                text = subject.courseTrack.displayName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = trackTextColor,
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
                val displayTopics = if (isExpanded) subject.topics else subject.topics.take(3)
                displayTopics.forEach { topic ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = topic,
                            fontSize = 11.sp,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
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
                            .clickable { isExpanded = true }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
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

                // Status Badge
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
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Retry Needed",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFDC2626)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Secondary action: Curriculum outline & WASSCE guide
                OutlinedButton(
                    onClick = onDetailsClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("outline_button_${subject.id}"),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                ) {
                    Text(
                        text = "Outline & Tips",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155)
                    )
                }

                // Primary Action: Download PDF or Open PDF
                if (downloadState == DownloadState.COMPLETED) {
                    Button(
                        onClick = onOpenClick,
                        modifier = Modifier
                            .weight(1.2f)
                            .height(44.dp)
                            .testTag("open_button_${subject.id}"),
                        shape = RoundedCornerShape(10.dp),
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
                            .height(44.dp)
                            .testTag("download_button_${subject.id}"),
                        shape = RoundedCornerShape(10.dp),
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
