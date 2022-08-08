package com.example.vulnscan20

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vulnscan20.ui.theme.Teal200
import com.example.vulnscan20.ui.theme.VulnScan20Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = applicationContext
        super.onCreate(savedInstanceState)
        setContent {
            VulnScan20Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(context) {
                        startActivity(ProfileActivity.newIntent(this, it))
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Welcome to VulnScan!")
        Spacer(modifier = Modifier.height(50.0.dp))
        TextButton(
            onClick = { navController.navigate("applist") },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
        )
        {
            Text(
                "Overlook your \n installed Applications",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        TextButton(
            onClick = { navController.navigate("androidscan") },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
        )
        {
            Text(
                "Scan your System",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(50.0.dp))
        TextButton(
            onClick = { System.exit(-1) },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200)
        )
        {
            Text(
                "Exit the Application",
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        }
    }
}

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
fun ScanScreen(app: Application, onNavIconPressed: () -> Unit = {}) {
    VulnScan20Theme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Application Name: " + app.appName)
            Text(text = "Package Name: " + app.packageName)
            app.version?.let { Text(text = "Version: $it") }
            Text(text = "Source Directory: " + app.sourcedir)
            Text(text = "Data Directory: " + app.datadir)
            Text(text = "Permissions: " + app.permissions)
            Spacer(modifier = Modifier.height(20.0.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = {  },
                    colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
                )
                {
                    Text(
                        "Scan the Selected \n Application",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

//*Scan some of the vulnerabilities scanned by QARK
// https://hackercombat.com/7-useful-android-vulnerability-scanners/
// https://github.com/linkedin/qark
// *//

@Composable
fun AndroidScan(context: Context) {
    val devmode = Settings.Secure.getInt(context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0)
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Current OS Version: " + android.os.Build.VERSION.RELEASE)
        if (devmode == 0) {
            Text(text = "Developer Settings are not enabled.", color = Color.Green)
        }
         else {Text(text = "Developer Settings are enabled. Consider disabling them unless " +
                "required in order to significantly increase your devices security", color = Color.Red)}
        Text(text = "Eavesdropping?")
        Text(text = "Tapjacking?")
    }

}