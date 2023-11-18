package com.armaan.coffeemasters.pages

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AppWebView() {
    val url = "https://firtman.github.io/coffeemasters/webapp"
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            webViewClient = WebViewClient() //customize the add view
            this.loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}

@Composable
fun InfoPage() {
    AppWebView()
}