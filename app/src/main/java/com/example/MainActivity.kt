package com.example

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.example.ui.AkisssMainScreen
import com.example.ui.AkisssViewModel
import com.example.ui.components.SplashScreen
import com.example.ui.theme.AkisssTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AkisssViewModel by viewModels()

    // Permission launcher for Android 13+ notifications & legacy storage
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val notifGranted = permissions[Manifest.permission.POST_NOTIFICATIONS] ?: true
        if (!notifGranted) {
            Toast.makeText(
                this,
                "Notifications disabled. Download alerts will show inside app only.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Register system broadcast receiver for native DownloadManager completions
        viewModel.downloadManager.registerReceiver()

        // Check required runtime permissions
        checkRequiredPermissions()

        setContent {
            AkisssTheme {
                var showSplash by remember { mutableStateOf(true) }

                Crossfade(
                    targetState = showSplash,
                    animationSpec = tween(durationMillis = 600),
                    label = "splash_crossfade"
                ) { isSplash ->
                    if (isSplash) {
                        SplashScreen(
                            onSplashFinished = { showSplash = false }
                        )
                    } else {
                        AkisssMainScreen(
                            viewModel = viewModel,
                            onOpenDownloadsFolder = { openDeviceDownloadsFolder() }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Synchronize download statuses with files in Downloads directory
        viewModel.refreshDownloadedFilesStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.downloadManager.unregisterReceiver()
    }

    private fun checkRequiredPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // Notification permission for Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Storage write permission for legacy Android (API <= 28)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    /**
     * Opens the device's native Downloads manager / files app
     */
    private fun openDeviceDownloadsFolder() {
        try {
            val downloadIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(downloadIntent)
        } catch (_: Exception) {
            try {
                val filesIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                startActivity(Intent.createChooser(filesIntent, "Open Downloads Folder"))
            } catch (fallbackException: Exception) {
                Toast.makeText(
                    this,
                    "Saved to internal storage under: /Downloads",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        /**
         * Reusable Kotlin function to download official GES curriculum PDF using Android DownloadManager
         */
        fun downloadCurriculumPdf(context: Context, subjectName: String, fileUrl: String): Long {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
            if (downloadManager == null) {
                Toast.makeText(context, "System DownloadManager is unavailable", Toast.LENGTH_SHORT).show()
                return -1L
            }

            return try {
                val cleanFileName = "GES_${subjectName.replace(" ", "_")}.pdf"
                val request = DownloadManager.Request(Uri.parse(fileUrl)).apply {
                    setTitle("Downloading $subjectName PDF")
                    setDescription("Official GES SHS Curriculum • curriculumresources.edu.gh")
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
                    )
                    setAllowedOverRoaming(true)
                    setMimeType("application/pdf")
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, cleanFileName)
                }

                Toast.makeText(context, "Download started: GES_${subjectName.replace(" ", "_")}.pdf", Toast.LENGTH_SHORT).show()
                downloadManager.enqueue(request)
            } catch (e: Exception) {
                Toast.makeText(context, "Queued download: $subjectName", Toast.LENGTH_SHORT).show()
                -1L
            }
        }
    }
}
