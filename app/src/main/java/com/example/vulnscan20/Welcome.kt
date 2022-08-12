package com.example.vulnscan20

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.vulnscan20.ui.theme.Teal200
import com.example.vulnscan20.ui.theme.VulnScan20Theme

@Composable
fun Welcome(navController: NavController) {

    VulnScan20Theme {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(backgroundColor = Color.White) {
                var expanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier.clickable { expanded = !expanded },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.welcomeimg),
                        contentDescription = "Profile picture",
                        Modifier.padding(all = 5.dp)
                    )
                    AnimatedVisibility(expanded) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Welcome to \n VulnScan",
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                            TextButton(
                                onClick = { navController.navigate("homescreen") },
                                colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200)
                            )
                            {
                                Text("Continue", color = Color.Black)
                            }
                        }

                    }
                }
            }
        }
    }
}
