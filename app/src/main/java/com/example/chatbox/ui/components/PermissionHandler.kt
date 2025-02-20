package com.example.chatbox.ui.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.chatbox.utils.Constants.permissionList


@Composable
fun PermissionHandler() {
    val localContext = LocalContext.current
    var showAlertDialog by remember { mutableStateOf(false) }
    var countDeny by remember { mutableIntStateOf(0) }
    val list = permissionList.filter {
        it.maxSdk >= android.os.Build.VERSION.SDK_INT
    }.map { it.permission }.toTypedArray()
    // Request permissions using a rememberLauncherForActivityResult
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissionResults ->
                (!permissionResults.values.all { it }).let { isDeny->
                    countDeny++
                    showAlertDialog = isDeny
                }
            })

    fun checkPermissionsAndRequestIfNeeded() {
        if (permissionList.isNotEmpty() && countDeny<2) {
            requestPermissionLauncher.launch(list)
        }
    }
    LaunchedEffect(showAlertDialog) {
        checkPermissionsAndRequestIfNeeded()
    }

    if (showAlertDialog && countDeny >= 2) {
        AlertDialog(onDismissRequest = { },
            title = { Text("Permission is required") },
            text = {
                Text("Please allow all permission")
            },
            confirmButton = {
                Button(onClick = {

                    requestPermissionLauncher.launch(list)

                    // Open app settings
                    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", localContext.packageName, null)
                    intent.data = uri
                    localContext.startActivity(intent)
                }) {
                    Text(color = Color.White, text = "Go to settings")
                }
            })
    }
}

