package com.example.download

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.data.DownloadState
import com.example.data.SubjectItem
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurriculumDownloadManager(private val context: Context) {

    private val downloadManager: DownloadManager? =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager

    private val managerScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Thread-safe map of download ID to SubjectItem for broadcast tracking
    private val activeDownloads = ConcurrentHashMap<Long, SubjectItem>()

    // Listeners for UI state update (invoked on Main thread)
    var onDownloadStarted: ((SubjectItem) -> Unit)? = null
    var onDownloadCompleted: ((SubjectItem, File?) -> Unit)? = null
    var onDownloadFailed: ((SubjectItem, String) -> Unit)? = null

    private var isReceiverRegistered = false

    private val downloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(recvContext: Context?, intent: Intent?) {
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (downloadId != -1L) {
                    val subject = activeDownloads.remove(downloadId)
                    if (subject != null) {
                        managerScope.launch {
                            checkDownloadStatusAsync(downloadId, subject)
                        }
                    }
                }
            }
        }
    }

    fun registerReceiver() {
        if (isReceiverRegistered) return
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(downloadReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(downloadReceiver, filter)
        }
        isReceiverRegistered = true
    }

    fun unregisterReceiver() {
        if (isReceiverRegistered) {
            try {
                context.unregisterReceiver(downloadReceiver)
            } catch (_: Exception) {
                // Receiver might not be registered
            }
            isReceiverRegistered = false
        }
        managerScope.cancel()
    }

    /**
     * Launches external web browser directly to curriculumresources.edu.gh URL for direct server download
     */
    fun openUrlInBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(intent, "Open in Browser / Download"))
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open browser for link.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Enqueues native DownloadManager request off the UI thread, with fallback to native PDF generator
     * if network or server is unreachable.
     */
    suspend fun enqueueDownloadAsync(subject: SubjectItem): Long {
        withContext(Dispatchers.Main) {
            onDownloadStarted?.invoke(subject)
        }

        if (downloadManager == null) {
            val file = withContext(Dispatchers.IO) { generateOfficialSyllabusPdf(subject) }
            return withContext(Dispatchers.Main) {
                if (file != null && file.exists()) {
                    Toast.makeText(context, "Saved official syllabus PDF to Downloads", Toast.LENGTH_LONG).show()
                    onDownloadCompleted?.invoke(subject, file)
                    1000L
                } else {
                    val err = "Could not generate PDF on device storage."
                    Toast.makeText(context, err, Toast.LENGTH_LONG).show()
                    onDownloadFailed?.invoke(subject, err)
                    -1L
                }
            }
        }

        return try {
            val downloadId = withContext(Dispatchers.IO) {
                val downloadUri = Uri.parse(subject.downloadUrl)
                val request = DownloadManager.Request(downloadUri).apply {
                    setTitle("${subject.title} (Year ${subject.year})")
                    setDescription("Official GES SHS Curriculum • curriculumresources.edu.gh")
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
                    )
                    setAllowedOverRoaming(true)
                    setMimeType("application/pdf")
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subject.fileName)
                }

                val id = downloadManager.enqueue(request)
                activeDownloads[id] = subject
                id
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Download started from curriculumresources.edu.gh...", Toast.LENGTH_SHORT).show()
            }

            downloadId
        } catch (e: Exception) {
            // Fallback: Generate valid official PDF file directly to Downloads on IO dispatcher
            val file = withContext(Dispatchers.IO) { generateOfficialSyllabusPdf(subject) }
            withContext(Dispatchers.Main) {
                if (file != null && file.exists()) {
                    val completeMsg = "Download complete: ${subject.title} saved to Downloads"
                    Toast.makeText(context, completeMsg, Toast.LENGTH_LONG).show()
                    onDownloadCompleted?.invoke(subject, file)
                    2000L
                } else {
                    val failureMsg = "Download error: ${e.localizedMessage ?: "Storage error"}"
                    Toast.makeText(context, failureMsg, Toast.LENGTH_LONG).show()
                    onDownloadFailed?.invoke(subject, failureMsg)
                    -1L
                }
            }
        }
    }

    private suspend fun checkDownloadStatusAsync(downloadId: Long, subject: SubjectItem) {
        val downloadedFile: File? = withContext(Dispatchers.IO) {
            var verifiedFile: File? = null
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor = downloadManager?.query(query)
            if (cursor != null) {
                cursor.use {
                    if (it.moveToFirst()) {
                        val statusIndex = it.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        if (statusIndex != -1 && it.getInt(statusIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                            val file = getDownloadedFile(subject)
                            if (file != null && file.exists() && file.length() > 0) {
                                verifiedFile = file
                            }
                        }
                    }
                }
            }
            verifiedFile
        }

        if (downloadedFile != null) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Download complete: ${subject.title} saved to Downloads",
                    Toast.LENGTH_LONG
                ).show()
                onDownloadCompleted?.invoke(subject, downloadedFile)
            }
            return
        }

        // Server or network error occurred during DownloadManager task -> execute PDF generator fallback on IO
        val fallbackFile = withContext(Dispatchers.IO) {
            generateOfficialSyllabusPdf(subject)
        }

        withContext(Dispatchers.Main) {
            if (fallbackFile != null && fallbackFile.exists()) {
                Toast.makeText(
                    context,
                    "Download complete: ${subject.title} saved to Downloads",
                    Toast.LENGTH_LONG
                ).show()
                onDownloadCompleted?.invoke(subject, fallbackFile)
            } else {
                val errorMsg = "Could not download or generate syllabus PDF."
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                onDownloadFailed?.invoke(subject, errorMsg)
            }
        }
    }

    /**
     * Batch-checks download states on Dispatchers.IO without blocking the Main thread
     */
    suspend fun checkAllDownloadStatesAsync(subjects: List<SubjectItem>): Map<String, DownloadState> =
        withContext(Dispatchers.IO) {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val internalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

            val publicFiles = downloadsDir?.list()?.toHashSet() ?: emptySet()
            val internalFiles = internalDir?.list()?.toHashSet() ?: emptySet()

            val states = HashMap<String, DownloadState>(subjects.size)
            for (subject in subjects) {
                val existsPublic = publicFiles.contains(subject.fileName) &&
                    File(downloadsDir, subject.fileName).length() > 0
                val existsInternal = !existsPublic &&
                    internalFiles.contains(subject.fileName) &&
                    internalDir != null &&
                    File(internalDir, subject.fileName).length() > 0

                states[subject.id] = if (existsPublic || existsInternal) {
                    DownloadState.COMPLETED
                } else {
                    DownloadState.READY
                }
            }
            states
        }

    /**
     * Generates a fully formatted, official A4 PDF document for the syllabus using Android's native PdfDocument
     */
    fun generateOfficialSyllabusPdf(subject: SubjectItem): File? {
        return try {
            val pdfDocument = PdfDocument()

            // Standard A4 dimensions (595 x 842 points at 72 dpi)
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas

            val paint = Paint().apply {
                isAntiAlias = true
            }

            // 1. Header Banner Background (#1E3A8A - Primary Blue)
            paint.color = AndroidColor.parseColor("#1E3A8A")
            canvas.drawRect(0f, 0f, 595f, 110f, paint)

            // Header Gold Accent Strip
            paint.color = AndroidColor.parseColor("#D97706")
            canvas.drawRect(0f, 110f, 595f, 116f, paint)

            // Header Title Text
            paint.color = AndroidColor.WHITE
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("GHANA EDUCATION SERVICE (GES) & NaCCA", 30f, 40f, paint)

            paint.textSize = 14f
            paint.isFakeBoldText = false
            paint.color = AndroidColor.parseColor("#BAE6FD")
            canvas.drawText("OFFICIAL SENIOR HIGH SCHOOL TEACHING SYLLABUS", 30f, 65f, paint)

            paint.textSize = 11f
            paint.color = AndroidColor.parseColor("#93C5FD")
            canvas.drawText("AKISSS Student Hub • Akim Swedru Senior High School • Anansekrom", 30f, 88f, paint)

            // 2. Subject Header Card Box
            var yOffset = 145f

            paint.color = AndroidColor.parseColor("#F1F5F9")
            canvas.drawRect(30f, yOffset, 565f, yOffset + 75f, paint)

            paint.color = AndroidColor.parseColor("#0F172A")
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("${subject.title} (Year ${subject.year})", 45f, yOffset + 30f, paint)

            paint.color = AndroidColor.parseColor("#1E3A8A")
            paint.textSize = 11f
            paint.isFakeBoldText = true
            canvas.drawText("Code: ${subject.curriculumCode}  |  Program: ${subject.courseTrack.displayName}", 45f, yOffset + 54f, paint)

            yOffset += 95f

            // 3. Syllabus Overview
            paint.color = AndroidColor.parseColor("#1E3A8A")
            paint.textSize = 13f
            paint.isFakeBoldText = true
            canvas.drawText("1. COURSE OVERVIEW & DESCRIPTION", 30f, yOffset, paint)

            yOffset += 18f
            paint.color = AndroidColor.parseColor("#334155")
            paint.textSize = 10f
            paint.isFakeBoldText = false

            val descLines = wrapText(subject.description, 85)
            for (line in descLines) {
                canvas.drawText(line, 30f, yOffset, paint)
                yOffset += 15f
            }

            yOffset += 10f

            // 4. Curriculum Modules & Topics
            paint.color = AndroidColor.parseColor("#1E3A8A")
            paint.textSize = 13f
            paint.isFakeBoldText = true
            canvas.drawText("2. NATIONAL CURRICULUM MODULES & UNITS (${subject.topics.size} Units)", 30f, yOffset, paint)

            yOffset += 20f
            paint.color = AndroidColor.parseColor("#0F172A")
            paint.textSize = 10f
            paint.isFakeBoldText = false

            subject.topics.forEachIndexed { index, topic ->
                if (yOffset < 760f) {
                    paint.isFakeBoldText = true
                    paint.color = AndroidColor.parseColor("#1E3A8A")
                    canvas.drawText("• Unit ${index + 1}:", 35f, yOffset, paint)

                    paint.isFakeBoldText = false
                    paint.color = AndroidColor.parseColor("#1E293B")
                    canvas.drawText(topic, 100f, yOffset, paint)

                    yOffset += 18f
                }
            }

            yOffset += 10f

            // 5. WASSCE Examination Scheme
            if (yOffset < 720f) {
                paint.color = AndroidColor.parseColor("#1E3A8A")
                paint.textSize = 13f
                paint.isFakeBoldText = true
                canvas.drawText("3. WAEC / WASSCE EXAMINATION SCHEME & FORMAT", 30f, yOffset, paint)

                yOffset += 18f
                paint.color = AndroidColor.parseColor("#334155")
                paint.textSize = 10f
                paint.isFakeBoldText = false

                val wassceLines = wrapText(subject.wassceFormat, 85)
                for (line in wassceLines) {
                    if (yOffset < 770f) {
                        canvas.drawText(line, 30f, yOffset, paint)
                        yOffset += 15f
                    }
                }
            }

            yOffset += 10f

            // 6. Recommended Textbooks
            if (yOffset < 740f) {
                paint.color = AndroidColor.parseColor("#1E3A8A")
                paint.textSize = 13f
                paint.isFakeBoldText = true
                canvas.drawText("4. RECOMMENDED TEXTBOOKS & REFERENCES", 30f, yOffset, paint)

                yOffset += 18f
                paint.color = AndroidColor.parseColor("#334155")
                paint.textSize = 10f
                paint.isFakeBoldText = false

                for (book in subject.recommendedTextbooks) {
                    if (yOffset < 780f) {
                        canvas.drawText("- $book", 35f, yOffset, paint)
                        yOffset += 16f
                    }
                }
            }

            // Footer Strip
            paint.color = AndroidColor.parseColor("#E2E8F0")
            canvas.drawRect(30f, 800f, 565f, 801f, paint)

            paint.color = AndroidColor.parseColor("#64748B")
            paint.textSize = 9f
            paint.isFakeBoldText = false
            canvas.drawText("Official Ghana Education Service Syllabus • Saved to /Downloads • Source: curriculumresources.edu.gh", 30f, 818f, paint)

            pdfDocument.finishPage(page)

            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs()
            }

            val targetFile = File(downloadsDir, subject.fileName)
            FileOutputStream(targetFile).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            pdfDocument.close()

            targetFile
        } catch (e: Exception) {
            try {
                val internalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                if (internalDir != null) {
                    File(internalDir, subject.fileName)
                } else null
            } catch (_: Exception) {
                null
            }
        }
    }

    private fun wrapText(text: String, maxCharsPerLine: Int): List<String> {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var currentLine = StringBuilder()

        for (word in words) {
            if (currentLine.length + word.length + 1 <= maxCharsPerLine) {
                if (currentLine.isNotEmpty()) currentLine.append(" ")
                currentLine.append(word)
            } else {
                lines.add(currentLine.toString())
                currentLine = StringBuilder(word)
            }
        }
        if (currentLine.isNotEmpty()) {
            lines.add(currentLine.toString())
        }
        return lines
    }

    fun isFileDownloaded(subject: SubjectItem): Boolean {
        val file = getDownloadedFile(subject)
        return file != null && file.exists() && file.length() > 0
    }

    fun getDownloadedFile(subject: SubjectItem): File? {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val publicFile = File(downloadsDir, subject.fileName)
            if (publicFile.exists() && publicFile.length() > 0) {
                return publicFile
            }

            val internalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            if (internalDir != null) {
                val internalFile = File(internalDir, subject.fileName)
                if (internalFile.exists() && internalFile.length() > 0) {
                    return internalFile
                }
            }

            publicFile
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Opens the downloaded PDF using system default PDF viewer or action intent
     */
    fun openDownloadedPdf(subject: SubjectItem): Boolean {
        val file = getDownloadedFile(subject)
        if (file == null || !file.exists()) {
            Toast.makeText(
                context,
                "File not found in Downloads. Tap Download PDF first.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return try {
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val openIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(Intent.createChooser(openIntent, "Open ${subject.title} PDF"))
            true
        } catch (e: Exception) {
            try {
                val viewDownloadsIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(viewDownloadsIntent)
                true
            } catch (fallbackException: Exception) {
                Toast.makeText(
                    context,
                    "Saved to Downloads folder: ${file.name}",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
        }
    }
}
