package com.l3on1kl.ping

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.l3on1kl.network.URL

fun checkPing(context: Context, ping: MutableState<String>) {
    val queue = Volley.newRequestQueue(context)
    val startTime = System.nanoTime()
    val sRequest = StringRequest(
        Request.Method.GET, URL,
        {
            val endTime = System.nanoTime()
            ping.value = ((endTime - startTime) / 1_000_000).toString()
            Log.d("ping", ping.value)
        }
    ) { error ->
        Log.d("responseWeather", "VolleyError: $error")
    }
    queue.add(sRequest)
}