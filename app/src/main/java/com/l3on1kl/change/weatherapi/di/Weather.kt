package com.l3on1kl.change.weatherapi.di

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

const val API_KEY = "c2d8c3bbefc445b0b8770203242305"
class WeatherAPI(override val androidContext: Context) : Weather {
    // Определение реализации интерфейса
    override fun weatherAPI(city: String, weatherCurrent: MutableState<String>){
        getData(city, weatherCurrent,  androidContext)
        Log.d("weatherAPI", "WeatherCurrent: $weatherCurrent")
    }

    private fun getData(city: String, weatherCurrent: MutableState<String>,  context: Context){
        val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
                "&q=$city" +
                "&days=1" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val sRequest = StringRequest( Request.Method.GET, url,
            { response ->
                weatherCurrent.value = getWeather(response)
                Log.d("responseWeather", "Response: $weatherCurrent.value")
            },
            { error ->
                Log.d("responseWeather", "VolleyError: $error")
            }
        )
        queue.add(sRequest)
    }

    private fun getWeather(response: String): String {
        if (response.isEmpty()) return ""
        val mainObject = JSONObject(response)
        return mainObject.getJSONObject("current").getString("temp_c")
    }
}
