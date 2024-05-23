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
import com.l3on1kl.change.weatherapi.di.Weather
import com.l3on1kl.change.weatherapi.di.weatherModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.Calendar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin{
            androidContext(this@MainActivity)
            modules(
                weatherModule  // Загрузка модуля
            )
        }

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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        )  {
                            var city by remember {
                                mutableStateOf("")
                            }
                            TextField(
                                value = city,
                                onValueChange = {value ->
                                    city = value
                                },
                                placeholder = {
                                    Text(
                                        text = "Город"
                                    )
                                }
                            )
                            val weatherCurrent = remember {
                                mutableStateOf("")
                            }

                            val weather: Weather = get() // Использование модуля

                            Button(
                                onClick = {
                                    weather.weatherAPI(city, weatherCurrent)
                                }
                            ) {
                                Text(text = "Узнать погоду")
                            }
                            if (city.isNotEmpty()){
                                Text(
                                    text = "Сейчас в $city: ${weatherCurrent.value} градусов"
                                )
                            }
                        }
                        Button(
                            onClick = {
                                finish()
                            },
                        ) {
                            Text(text = "Выход")
                        }
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
    val alternativeComponent = ComponentName(context, "com.l3on1kl.change.alias.MainActivityAlternative")

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
