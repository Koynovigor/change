package com.l3on1kl.network

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

private const val API_KEY = "60b74e3943fe48cd86c23422243105"
var URL = ""

fun weatherAPI(city: String, weatherCurrent: MutableState<String>, androidContext: Context) {
    getData(city, weatherCurrent, androidContext)
}

private fun getData(city: String, weatherCurrent: MutableState<String>, context: Context) {
    URL = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
            "&q=$city" +
            "&days=1" +
            "&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(Request.Method.GET, URL,
        { response ->
            weatherCurrent.value = getWeather(response)
            Log.d("responseWeather", "Response: ${weatherCurrent.value}")
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