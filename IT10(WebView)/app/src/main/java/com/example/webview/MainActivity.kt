package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var webView:WebView
    lateinit var video_btn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        video_btn = findViewById(R.id.video_btn)

        video_btn.apply {
            setOnClickListener(View.OnClickListener {
                intent.setClass(context,VideoActivity::class.java)
                startActivity(intent)
            })
        }

        webView.apply {
            //it當天文章網址
            loadUrl("https://ithelp.ithome.com.tw/articles/10286339/draft")
            //告訴 WebView 啟用 JavaScript 執行。
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
    }


    override fun onBackPressed() {
        //控制返回鍵作用於webView
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}