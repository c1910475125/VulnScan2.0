package com.example.vulnscan20

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.vulnscan20.ui.theme.Teal200

@Composable
fun HomeScreen(navController: NavController, activity: MainActivity, context: Context) {

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.BLUETOOTH_CONNECT),2
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Welcome to AndroScan!")
        Spacer(modifier = Modifier.height(30.0.dp))
        TextButton(
            modifier = Modifier.padding(0.dp, 10.dp),
            onClick = { navController.navigate("applist") },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
        )
        {
            Text(
                "Overlook your \n System Applications",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        TextButton(
            onClick = {  navController.navigate("osinfo") },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
        )
        {
            Text(
                "Check Security Settings",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .padding(0.dp, 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = { System.exit(-1) },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
            )
            {
                Text(
                    "Exit the Application",
                    color = Color.Black,
                    textAlign = TextAlign.Center

                )
            }
        }
    }
}