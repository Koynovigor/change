package com.l3on1kl.change.weatherapi.di

import android.content.Context
import androidx.compose.runtime.MutableState

interface Weather {
    val androidContext: Context
    // Определение интерфейса
    fun weatherAPI(city: String, weatherCurrent: MutableState<String>)
}