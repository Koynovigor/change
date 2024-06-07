package com.l3on1kl.change

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewComponent(url: String, onWebViewClosed: () -> Unit) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setCookie(url, "myCookie=myCookieValue; path=/")
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
    LaunchedEffect(Unit) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
        cookieManager.flush()
    }
    onWebViewClosed()
}

@Composable
fun MyScreen(city: String) {
    var showWebView by remember { mutableStateOf(false) }
    val url = "https://www.weatherapi.com/weather/q/$city"
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            showWebView = !showWebView
        }) {
            Text(if (showWebView) "Close WebView" else "Open WebView")
        }
        if (showWebView) {
            WebViewComponent(url = url) {
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookies(null)
                cookieManager.flush()
            }
        }
    }
}
