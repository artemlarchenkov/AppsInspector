package com.example.testapplication.Model

import android.graphics.drawable.Drawable

data class InstalledApp(
    val appName: String,
    val packageName: String,
    val versionName: String?,
    val icon: Drawable,
    val apkPath: String
)
