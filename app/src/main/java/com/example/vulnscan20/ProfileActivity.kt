package com.example.vulnscan20

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.vulnscan20.ui.theme.VulnScan20Theme

class ProfileActivity : AppCompatActivity() {

    private val application: Application by lazy {
        intent?.getSerializableExtra(PACKAGE_ID) as Application
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VulnScan20Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppScreen(application)
                }
            }
        }
    }

    companion object {
        private const val PACKAGE_ID = "package_id"
        fun newIntent(context: Context, application: Application) =
            Intent(context, ProfileActivity::class.java).apply {
                putExtra(PACKAGE_ID, application)
            }
    }
}
