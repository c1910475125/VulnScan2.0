package com.example.vulnscan20

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.coroutines.coroutineContext

@Composable
fun NavHost(context: Context, navigateToProfile: (Application) -> Unit) {
    val navController = rememberNavController()
    val list = context.packageManager.getInstalledPackages(0)
            Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        androidx.navigation.compose.NavHost(
            navController = navController,
            startDestination = "welcome",
        ) {
            composable("welcome") { Welcome(navController = navController) }
            composable("homescreen") { HomeScreen(navController = navController) }
            composable("applist") { AppList(list, context, navigateToProfile) }
            composable("androidscan") { AndroidScan(context) }
        }
    }
}
