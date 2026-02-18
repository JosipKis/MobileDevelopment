package com.example.filmoviapp1.presentation

import android.Manifest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission (
    onPermissionGranted: () -> Unit = {}
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Camera Permission") },
            text = {Text("We Need Permission to Access Camera")},
            confirmButton = {
                Button(onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    onPermissionGranted()
                }) {
                    Text("Allow")
                }
            }
        )
    } else {
        onPermissionGranted()
    }
}