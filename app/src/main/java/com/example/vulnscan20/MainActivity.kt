package com.example.vulnscan20

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentActivity
import com.example.vulnscan20.ui.theme.VulnScan20Theme
import java.io.File
import java.util.concurrent.Executor
import kotlin.system.exitProcess

class MainActivity : FragmentActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

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
                    NavHost(context) {
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
                    ) { if (mgr.isDeviceSecure) {
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


@Composable
fun OSinfo(context: Context) {
    val version = Build.VERSION.RELEASE
    val mgr = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    val devmode = Settings.Secure.getInt(
        context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        Text(text = "Manufacturer: " + Build.MANUFACTURER)
        Text(text = "Model: " + Build.MODEL)
        Text(text = "Current OS Version: $version")
        Text(text = "API Level: " + Build.VERSION.SDK_INT)
        Text(text = "Developer Settings:")
        if (devmode == 0) {
            Text(text = "Developer Settings are not enabled.", color = Color.Green)
        } else {
            Text(
                text = "Developer Settings are enabled. Consider disabling them unless " +
                        "required in order to significantly increase your devices security.",
                color = Color.Red
            )
        }
        Text(text = "Tapjacking:")
        if (version.toInt() < 4 || version.toInt() == 6) {
            Text(
                text = "Your Android Version could be vulnerable to tapjacking. Consider updating your OS.",
                color = Color.Red
            )
        } else {
            Text(
                text = "Your Android Version should not be Vulnerable to tapjacking.",
                color = Color.Green
            )
        }
        Text(text = "Rooting:")
        if (isRooted(context)) {
            Text(
                text = "Your Android Device seems to be rooted. Rooted devices are more vulnerable to malware attacks.",
                color = Color.Red
            )
        } else if (isEmulator(context)) {
            Text(
                text = "Your Android Device seems to be emulated",
                color = Color.Red
            )
        } else {
            Text(
                text = "Your Android device seems to not be rooted or emulated.",
                color = Color.Green
            )
        }
        @RequiresApi(Build.VERSION_CODES.M)
        if (mgr.isDeviceSecure) {
            Text(text = "Lock:")
            Text(
                text = "Your Android device uses an appropriate lockscreen.",
                color = Color.Green
            )
        }
        else if (!mgr.isDeviceSecure) {
            Text(text = "Lock:")
            Text(
                text = "Your Android device is not appropriately locked. Consider setting up a pin code.",
                color = Color.Red
            )
        }
    }

}

fun isRooted(context: Context): Boolean {
    val isEmulator = isEmulator(context)
    val buildTags = Build.TAGS
    return if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
        true
    } else {
        val file = File("/system/app/Superuser.apk")
        if (file.exists()) {
            true
        } else {
            val file = File("/system/xbin/su")
            !isEmulator && file.exists()
        }
    }
}

fun isEmulator(context: Context): Boolean {
    return Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk".equals(Build.PRODUCT)
}

