package com.example.vulnscan20

import android.content.Context
import android.content.pm.PackageInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AppCard(app: PackageInfo, context: Context, navigateToProfile: (Application) -> Unit) {
    val pm = context.packageManager
    val testapp = Application(
        app.applicationInfo.loadLabel(pm).toString(),
        app.packageName,
        app.versionName,
        app.applicationInfo.sourceDir,
        app.applicationInfo.dataDir
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