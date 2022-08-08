package com.example.vulnscan20

import android.content.pm.PermissionInfo
import java.io.Serializable
import java.security.Permissions

data class Application(
    val appName: String,
    val packageName: String,
    val version: String?,
    val sourcedir: String,
    val datadir: String,
    val permissions: String?
) : Serializable