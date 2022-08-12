package com.example.vulnscan20

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.vulnscan20.ui.theme.VulnScan20Theme
import java.util.concurrent.Executor
import kotlin.system.exitProcess

class MainActivity : FragmentActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        val mgr = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val context = applicationContext
        super.onCreate(savedInstanceState)
        setContent {
            VulnScan20Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(context, this) {
                        startActivity(ProfileActivity.newIntent(this, it))
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= 29) {
            executor = ContextCompat.getMainExecutor(this)
            biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        if (mgr.isDeviceSecure) {
                            super.onAuthenticationError(errorCode, errString)
                            Toast.makeText(
                                applicationContext,
                                "Authentication error: $errString",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            moveTaskToBack(true)
                            exitProcess(-1)
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        moveTaskToBack(true)
                        exitProcess(-1)
                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login using your device credentials")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()

            biometricPrompt.authenticate(promptInfo)
        } else {
            Toast.makeText(this, "Authentication not supported!", Toast.LENGTH_LONG).show()
        }

    }
}




