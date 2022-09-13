package com.example.webview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.webkit.WebChromeClient
import android.webkit.WebChromeClient.CustomViewCallback
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.startActivity
import android.view.WindowManager
import android.widget.LinearLayout


class VideoActivity : AppCompatActivity() {
    lateinit var video_webView: WebView
    lateinit var fullScreen_layout:FrameLayout
    private val mimeType = "text/html"
    private val encoding = "UTF-8" //"base64";
    private val activity:VideoActivity = this
    val videoEmbededAddress = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/JsHqEpWQl-8\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"
    private val USERAGENT =
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        measure()
        fullScreen_layout = findViewById(R.id.fullScreen_layout)
        video_webView = findViewById(R.id.video_webView)
//        val initialScale: Int = this.getScale(screenWidth.toDouble())
        video_webView.apply {
            //允許javaScript行為
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = false
            settings.loadsImagesAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.loadsImagesAutomatically = true
            setInitialScale(getScale(570.0))
            settings.userAgentString = USERAGENT //Important to auto play video

            webViewClient = MyWebViewClient(context)
            webChromeClient = MyWebChromeClient(fullScreen_layout,video_webView,activity)
            //無版權音樂
            loadUrl(videoEmbededAddress)
            loadDataWithBaseURL("", videoEmbededAddress, mimeType, encoding, "")
//            settings.useWideViewPort = false
        }
    }

    private fun measure() {
        val wm = this.getSystemService(WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.getDefaultDisplay().getMetrics(dm)
        val width = dm.widthPixels // 螢幕寬度(畫素)
        val height = dm.heightPixels // 螢幕高度(畫素)
        val density = dm.density //螢幕密度(0.75 / 1.0 / 1.5)
        val densityDpi = dm.densityDpi //螢幕密度dpi(120 / 160 / 240)
        //螢幕寬度演算法:螢幕寬度(畫素)/螢幕密度
        //螢幕寬度演算法:螢幕寬度(畫素)/螢幕密度
        val screenWidth = (width / density).toInt() //螢幕寬度(dp)
        val screenHeight = (height / density).toInt() //螢幕高度(dp)
        Log.e("TAG", "width: $width ,height: $height")
        Log.e("TAG", "screenWidth: $screenWidth ,screenHeight: $screenHeight")
    }

    private class MyWebViewClient(val context:Context) : WebViewClient() {
        //跳轉進入此function
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = Uri.parse(request.url.toString())
            Log.e("TAG", "shouldOverrideUrlLoading: $uri")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(context,intent,null)
            //true WebViewClient處裡跳轉，false WebView 處裡跳轉
            return true
        }
    }

    private class MyWebChromeClient(val fullScreen_layout:ViewGroup,val video_webView:WebView,val activity:VideoActivity) : WebChromeClient() {
        private var mCustomView: View? = null
        private val viewActivity = VideoActivity()

        var customViewCallback: CustomViewCallback? = null
        // 全屏
        override fun onShowCustomView(view: View, callback: CustomViewCallback) {
            super.onShowCustomView(view, callback)
            customViewCallback = callback
            mCustomView = view
            fullScreen_layout.addView(view)
            fullScreen_layout.visibility = View.VISIBLE
            video_webView.visibility = View.INVISIBLE
            viewActivity.hideSystemUI(activity)
        }
        // 退出全屏
        override fun onHideCustomView() {
            super.onHideCustomView()
            if (mCustomView == null) return
            customViewCallback?.onCustomViewHidden()
            fullScreen_layout.removeView(mCustomView)
            fullScreen_layout.visibility = View.INVISIBLE
            video_webView.visibility = View.VISIBLE
            mCustomView = null
            viewActivity.quitFullScreen(activity)

            Log.e("onHideCustomView", "退出全屏")
        }
    }
    override fun onBackPressed() {
        //控制返回鍵作用於webView
        if(video_webView.canGoBack()){
            video_webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
    fun getScale(contentWidth: Double): Int {
        val displaymetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displaymetrics)
//        windowManager.currentWindowMetrics.bounds
        val dimension = displaymetrics.widthPixels / contentWidth * 100.0
        return dimension.toInt()
    }

    //退出全螢幕
    fun quitFullScreen(activity:VideoActivity) {
        Log.e( "quitFullScreen: ", ""+requestedOrientation)
        // 聲明當前螢幕狀態的引數並獲取
        val attrs:WindowManager.LayoutParams = activity.window.attributes
        attrs.flags = (WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.window.attributes = attrs
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        //ˊ如果狀態為感應
        if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
            //改為豎屏
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * 設定全屏
     */
    fun hideSystemUI(activity:VideoActivity) {
        Log.e("hideSystemUI: ", "" + requestedOrientation)
        //ˊ如果狀態為豎屏
        if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            //改為感應
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }
}