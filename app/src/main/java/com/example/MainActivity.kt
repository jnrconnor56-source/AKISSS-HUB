package com.example

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.ui.AkisssMainScreen
import com.example.ui.AkisssViewModel
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
                AkisssMainScreen(
                    viewModel = viewModel,
                    onOpenDownloadsFolder = { openDeviceDownloadsFolder() }
                )
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
}
