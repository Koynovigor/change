package com.l3on1kl.change.weatherapi.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val weatherModule = module {
    single<Weather> { WeatherAPI(androidContext()) } // Определение модуля
}