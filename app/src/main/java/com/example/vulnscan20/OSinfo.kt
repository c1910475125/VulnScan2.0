package com.example.vulnscan20

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import java.io.File

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun OSinfo(context: Context) {
    val version = Build.VERSION.RELEASE.toInt()
    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    val mgr = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    val devmode = Settings.Secure.getInt(
        context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Manufacturer: " + Build.MANUFACTURER)
        Text(text = "Model: " + Build.MODEL)
        Text(text = "Current OS Version: $version")
        Text(text = "API Level: " + Build.VERSION.SDK_INT + "\n")
        Text(text = "Developer Settings:")
        if (devmode == 0) {
            Text(text = "Developer Settings are not enabled.\n", color = Color.Green)
        } else {
            Text(
                text = "Developer Settings are enabled. Consider disabling them unless " +
                        "required in order to significantly increase your devices security.\n",
                color = Color.Red
            )
        }

        Text(text = "Rooting & Emulation:")
        if (isRooted(context)) {
            Text(
                text = "Your Android Device seems to be rooted. Rooted devices are more vulnerable to malware attacks.\n",
                color = Color.Red
            )
        } else if (isEmulator(context)) {
            Text(
                text = "Your Android Device seems to be emulated.\n",
                color = Color.Red
            )
        } else {
            Text(
                text = "Your Android device seems to not be rooted or emulated.\n",
                color = Color.Green
            )
        }

        Text(text = "Screen Timeout:")
        if (!checkTimeout(context)) {
            Text(
                text = "Your Android Device has an appropriate screen timeout set.\n",
                color = Color.Green
            )
        } else if (checkTimeout(context)) {
            Text(
                text = "Your Android Device has a long screen timeout set. Consider shortening that timer to prevent unauthorized physical access.\n",
                color = Color.Red
            )
        }
        Text(text = "Lock:")
        if (mgr.isDeviceSecure) {
            Text(
                text = "Your Android device uses an appropriate lockscreen.\n",
                color = Color.Green
            )
        } else if (!mgr.isDeviceSecure) {
            Text(
                text = "Your Android device is not appropriately locked. Consider setting up a pin code.\n",
                color = Color.Red
            )
        }

        if (bluetoothAdapter != null) {
            Text(text = "Bluetooth:")
            if (bluetoothAdapter.isEnabled) {
                Text(
                    text = "Your device has bluetooth enabled. Consider disabling it if is not currently required.\n",
                    color = Color.Red
                )
            } else if (!bluetoothAdapter.isEnabled) {
                Text(
                    text = "Your Bluetooth is disabled.\n",
                    color = Color.Green
                )
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {



                Text(text = "Enable bluetooth permissions for this application to overview paired devices and their MAC addresses. \n")

            } else {
                Row {
                    Text(
                        text = "Paired Device Name:",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Left
                    )
                    Text(text = "MAC address:", textAlign = TextAlign.Right)
                }
                val devices: MutableSet<BluetoothDevice> = bluetoothAdapter.bondedDevices
                for (BluetoothDevice in devices) {
                    Row {
                        Text(
                            text = BluetoothDevice.name,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Left
                        )
                        Text(text = BluetoothDevice.address, textAlign = TextAlign.Right)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

        }

        if (bluetoothAdapter == null) {
            Text(text = "Bluetooth:")
            Text(
                text = "Your device does not support bluetooth\n",
                color = Color.Red
            )
        }


        Text(text = "Tapjacking:")
        if (version < 4 || version == 6) {
            Text(
                text = "Your Android Version could be vulnerable to tapjacking. Consider updating your OS.\n",
                color = Color.Red
            )
        } else {
            Text(
                text = "Your Android Version should not be Vulnerable to tapjacking.\n",
                color = Color.Green
            )
        }
    }

}

//    check microphone and camera activity
//    check gps activity
//    check sleep timer check
//    check backup status

fun checkTimeout(context: Context): Boolean {
    if (Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_OFF_TIMEOUT
        ) > 300000
    ) {
        return true
    }
    return false
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