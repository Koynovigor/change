package com.l3on1kl.change.weatherapi

import com.l3on1kl.change.weatherapi.di.Weather
import kotlin.random.Random

class WeatherAPI: Weather {
    // Определение реализации интерфейса
    override fun weatherAPI(salt: Long): Int {
        val random = Random(salt)
        return random.nextInt()
    }
}