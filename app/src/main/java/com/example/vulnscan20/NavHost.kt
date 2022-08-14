package com.example.vulnscan20

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun NavHost(context: Context, activity: MainActivity, navigateToProfile: (Application) -> Unit) {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        androidx.navigation.compose.NavHost(
            navController = navController,
            startDestination = "welcome",
        ) {
            composable("welcome") { Welcome(navController = navController) }
            composable("homescreen") { HomeScreen(navController = navController, activity, context) }
            composable("applist") { AppList(context, navigateToProfile) }
            composable("osinfo") { OSinfo(context) }
        }
    }
}

