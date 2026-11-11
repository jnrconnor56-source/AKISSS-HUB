package com.example.download

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.data.SubjectItem
import java.io.File

class CurriculumDownloadManager(private val context: Context) {

    private val downloadManager: DownloadManager? =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager

    // Map download ID to SubjectItem for broadcast tracking
    private val activeDownloads = mutableMapOf<Long, SubjectItem>()

    // Listeners for UI state update
    var onDownloadStarted: ((SubjectItem) -> Unit)? = null
    var onDownloadCompleted: ((SubjectItem, File?) -> Unit)? = null
    var onDownloadFailed: ((SubjectItem, String) -> Unit)? = null

    private val downloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(recvContext: Context?, intent: Intent?) {
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (downloadId != -1L) {
                    val subject = activeDownloads.remove(downloadId)
                    if (subject != null) {
                        checkDownloadStatus(downloadId, subject)
                    }
                }
            }
        }
    }

    fun registerReceiver() {
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(downloadReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(downloadReceiver, filter)
        }
    }

    fun unregisterReceiver() {
        try {
            context.unregisterReceiver(downloadReceiver)
        } catch (_: Exception) {
            // Receiver might not be registered
        }
    }

    /**
     * Dispatches native DownloadManager request directly to public Downloads directory
     */
    fun enqueueDownload(subject: SubjectItem): Long {
        if (downloadManager == null) {
            val errorMsg = "System Download Manager is unavailable on this device."
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
            onDownloadFailed?.invoke(subject, errorMsg)
            return -1L
        }

        try {
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

            val downloadId = downloadManager.enqueue(request)
            activeDownloads[downloadId] = subject

            val startMessage = "Download started: ${subject.title} (Year ${subject.year}) PDF..."
            Toast.makeText(context, startMessage, Toast.LENGTH_SHORT).show()
            onDownloadStarted?.invoke(subject)

            return downloadId
        } catch (e: Exception) {
            val failureMsg = "Could not queue download: ${e.localizedMessage ?: "Unknown error"}"
            Toast.makeText(context, failureMsg, Toast.LENGTH_LONG).show()
            onDownloadFailed?.invoke(subject, failureMsg)
            return -1L
        }
    }

    private fun checkDownloadStatus(downloadId: Long, subject: SubjectItem) {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager?.query(query)
        if (cursor != null && cursor.moveToFirst()) {
            val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            if (statusIndex != -1) {
                val status = cursor.getInt(statusIndex)
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    val file = getDownloadedFile(subject)
                    val completeMessage = "Download complete: ${subject.title} saved to Downloads"
                    Toast.makeText(context, completeMessage, Toast.LENGTH_LONG).show()
                    onDownloadCompleted?.invoke(subject, file)
                } else {
                    val reasonIndex = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
                    val reason = if (reasonIndex != -1) cursor.getInt(reasonIndex) else -1
                    val errorMsg = "Download was not successful (code: $reason)"
                    onDownloadFailed?.invoke(subject, errorMsg)
                }
            }
            cursor.close()
        } else {
            // Check direct file existence
            val file = getDownloadedFile(subject)
            if (file != null && file.exists()) {
                onDownloadCompleted?.invoke(subject, file)
            }
        }
    }

    /**
     * Checks if the curriculum PDF exists in public Downloads
     */
    fun isFileDownloaded(subject: SubjectItem): Boolean {
        val file = getDownloadedFile(subject)
        return file != null && file.exists() && file.length() > 0
    }

    fun getDownloadedFile(subject: SubjectItem): File? {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            File(downloadsDir, subject.fileName)
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
                "File not found in Downloads. Please download it first.",
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
            // Fallback: Launch system Downloads app
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
