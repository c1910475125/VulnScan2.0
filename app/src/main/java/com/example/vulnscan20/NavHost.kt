package com.example.vulnscan20

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHost(context: Context) {
    val navController = rememberNavController()
    var list = context.packageManager.getInstalledPackages(0)
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
            composable("applist") { AppList(list, context, navController = navController
            ) }
        }
    }
}
