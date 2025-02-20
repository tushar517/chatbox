package com.example.chatbox.utils

object Constants{
    val permissionList = listOf<PermissionObject>(
        /*PermissionObject(
            android.Manifest.permission.CAMERA,
            android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM
        ),
        PermissionObject(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM
        ),
        PermissionObject(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM
        ),
        PermissionObject(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.os.Build.VERSION_CODES.P
        ),
        PermissionObject(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.os.Build.VERSION_CODES.P
        )*/
    )
}

data class PermissionObject(
    val permission:String,
    val maxSdk:Int
)