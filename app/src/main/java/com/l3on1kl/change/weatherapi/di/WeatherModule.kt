package com.l3on1kl.change.weatherapi.di

import com.l3on1kl.change.weatherapi.WeatherAPI
import org.koin.dsl.module

val weatherModule = module {
    single<Weather> { WeatherAPI() } // Определение модуля
}