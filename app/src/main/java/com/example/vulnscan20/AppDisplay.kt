package com.example.vulnscan20

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vulnscan20.ui.theme.VulnScan20Theme

@Composable
fun AppList(
    apps: MutableList<PackageInfo>,
    context: Context,
    navigateToProfile: (Application) -> Unit
) {
    LazyColumn {
        items(apps) { app ->
            AppCard(app, context, navigateToProfile)
        }
    }
}

@Composable
fun AppCard(app: PackageInfo, context: Context, navigateToProfile: (com.example.vulnscan20.Application) -> Unit) {
    val pm = context.packageManager
    
    val testapp = Application(
        app.applicationInfo.loadLabel(pm).toString(),
        app.packageName,
        app.versionName,
        app.applicationInfo.sourceDir,
        app.applicationInfo.dataDir,
    )
    Row (modifier = Modifier
        .padding(all = 8.dp)
        .clickable {
            navigateToProfile(testapp)
        }
        .fillMaxSize()
    ){
        AsyncImage(
            model = app.applicationInfo.loadIcon(pm),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = app.applicationInfo.loadLabel(pm).toString(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
        }
    }
}

@Composable
fun AppScreen(app: Application) {
    VulnScan20Theme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        ) {
            Text(text = "Application Name: " + app.appName)
            Text(text = "Package Name: " + app.packageName)
            app.version?.let { Text(text = "Version: $it") }
            Text(text = "Source Directory: " + app.sourcedir)
            Text(text = "Data Directory: " + app.datadir)
            Spacer(modifier = Modifier.height(20.0.dp))
        }
    }
}