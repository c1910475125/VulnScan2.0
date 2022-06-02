package com.example.vulnscan20

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import kotlin.coroutines.coroutineContext

object InstalledAppList {
    val appsInstalled = listOf(
        Application("PlaceholderApp", "examplePackage", R.drawable.welcomeimg )
    )

}