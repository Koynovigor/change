package com.l3on1kl.change

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.l3on1kl.change.ui.theme.ChangeTheme
import com.l3on1kl.network.weatherAPI
import com.l3on1kl.ping.checkPing
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChangeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                    ) {
                        var city by remember {
                            mutableStateOf("")
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            val weatherCurrent = remember {
                                mutableStateOf("")
                            }
                            val ping = remember {
                                mutableStateOf("")
                            }
                            TextField(
                                value = city,
                                onValueChange = { value ->
                                    city = value
                                    ping.value = ""
                                },
                                placeholder = {
                                    Text(
                                        text = "Город"
                                    )
                                }
                            )
                            Button(
                                onClick = {
                                    weatherAPI(city, weatherCurrent, this@MainActivity)
                                    checkPing(this@MainActivity, ping)
                                }
                            ) {
                                Text(text = "Узнать погоду")
                            }
                            if (ping.value.isNotEmpty()) {
                                Text(
                                    text = "Сейчас в $city: ${weatherCurrent.value} градусов\n" +
                                            "Пинг: ${ping.value}"
                                )
                            }
                        }
                        MyScreen(city = city)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        updateAppIconBasedOnDate(context = this)
    }
}

fun setAppIcon(context: Context, useAlternativeIcon: Boolean) {
    val packageManager = context.packageManager

    val initComponent = ComponentName(context, "com.l3on1kl.change.MainActivity")
    val alternativeComponent =
        ComponentName(context, "com.l3on1kl.change.alias.MainActivityAlternative")

    if (useAlternativeIcon) {
        packageManager.setComponentEnabledSetting(
            initComponent,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            alternativeComponent,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    } else {
        packageManager.setComponentEnabledSetting(
            initComponent,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            alternativeComponent,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

fun updateAppIconBasedOnDate(context: Context) {
    val calendar = Calendar.getInstance()
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    // Меняем иконку каждый день
    if (dayOfMonth % 2 == 0) {
        setAppIcon(context, useAlternativeIcon = true)
    } else {
        setAppIcon(context, useAlternativeIcon = false)
    }
}
