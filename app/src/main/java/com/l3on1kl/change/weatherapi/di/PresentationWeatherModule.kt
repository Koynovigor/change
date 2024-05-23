package com.l3on1kl.change.weatherapi.di

interface Weather {
    // Определение интерфейса
    fun weatherAPI(salt: Long = 0): Int
}