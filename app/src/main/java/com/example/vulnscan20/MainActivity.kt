package com.example.vulnscan20

import android.content.Context
import android.content.pm.PackageInfo
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vulnscan20.ui.theme.Teal200
import com.example.vulnscan20.ui.theme.VulnScan20Theme
import java.io.Serializable

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
                    NavHost (context) {
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
        Spacer(modifier = Modifier.height(100.0.dp))
        TextButton(
            onClick = { navController.navigate("applist") },
            colors = ButtonDefaults.textButtonColors(backgroundColor = Teal200),
        )
        {
            Text(
                "Scan one of your \n installed Applications",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class Application(val appName: String,
                       val packageName: String
) : Serializable

@Composable
fun AppCard(app: PackageInfo, context: Context, navigateToProfile: (Application) -> Unit) {
    val pm = context.packageManager
    val testapp = Application(app.applicationInfo.loadLabel(pm).toString(), app.packageName)
    Row (modifier = Modifier
        .padding(all = 8.dp)
        .clickable {
                navigateToProfile(testapp)
        }
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
                style = MaterialTheme.typography.subtitle2)
        }
    }
}

@Composable
fun AppList(apps: MutableList<PackageInfo>, context: Context, navigateToProfile: (Application) -> Unit) {
    LazyColumn {
        items(apps) { app ->
            AppCard(app, context, navigateToProfile)
        }
    }
}

@Composable
fun ScanScreen(packageInfo: Application, onNavIconPressed: () -> Unit = {}){
    Text(text = "Success")
    Text(text = packageInfo.packageName)
}


@Preview(
    name = "Light Mode",
    device = Devices.PIXEL_2,
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "Dark Mode",
    device = Devices.PIXEL_2,
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DefaultPreview() {
    VulnScan20Theme {
//        NavHost()
    }
}