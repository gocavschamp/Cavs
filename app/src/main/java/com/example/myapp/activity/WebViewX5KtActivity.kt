package com.example.myapp.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.JavascriptInterface
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

import com.example.myapp.bean.StringBean
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.KeyboardUtil
import com.nucarf.base.utils.LogUtils
import com.nucarf.base.utils.SharePreUtils
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_webx5.*

import java.util.ArrayList


class WebViewX5KtActivity : BaseActivityWithTitle() {

    internal var webView: WebView? = null
    private var url: String? = null
    private var webStationAdapter: WebStationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webx5)
//        ButterKnife.bind(this)
        //        flContent = findViewById(R.id.fl_content);
        webView = WebView(applicationContext)
        fl_content!!.addView(webView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))
        url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        titlelayout.setTitleText(title + "")
        recycleview!!.layoutManager = GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false)
        webStationAdapter = WebStationAdapter(R.layout.item_web_station)
        recycleview!!.adapter = webStationAdapter
        val data = ArrayList<StringBean>()
        val bean = StringBean()
        bean.name = "百度"
        bean.value = "https://www.baidu.com/"
        val bean1 = StringBean()
        bean1.name = "知乎"
        bean1.value = "https://www.zhihu.com/"
        val bean2 = StringBean()
        bean2.name = "掘金"
        bean2.value = "https://juejin.im/android"
        val bean3 = StringBean()
        bean3.name = "掘金"
        bean3.value = "https://juejin.im/android"
        val bean4 = StringBean()
        bean4.name = "虎扑"
        bean4.value = "https://m.hupu.com/"
        data.add(bean)
        data.add(bean1)
        data.add(bean2)
        data.add(bean3)
        data.add(bean4)
        webStationAdapter!!.setNewData(data)
        initListener()
        initWeb()
    }

    private fun initListener() {
        btn_seach?.setOnClickListener {
            var url = et_url!!.text.toString()
            val contains = url.contains("http://") || url.contains("https://")
            url = if (contains) url else "https://$url"
            val parse = Uri.parse(url)
            LogUtils.e("url--input--$parse")
            loadH5(parse.toString())
            KeyboardUtil.hideInput(et_url, mContext)
        }
        webStationAdapter!!.setOnItemClickListener { adapter, view, position ->
            //            recycleview.setVisibility(View.GONE);
            loadH5(webStationAdapter!!.data[position].value + "")
        }
        et_url!!.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus -> recycleview!!.visibility = if (hasFocus) View.VISIBLE else View.GONE }
    }

    override fun initData() {

    }

    private fun initWeb() {
        webView?.run {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            setLayerType(View.LAYER_TYPE_HARDWARE, null)//开启硬件加速
            settings.setSupportZoom(false)
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.blockNetworkImage = false//解决图片不显示
            settings.domStorageEnabled = true
            settings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
            settings.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
            val appCachePath = applicationContext.cacheDir.absolutePath

            settings.setAppCachePath(appCachePath)
            settings.setAppCachePath(this@WebViewX5KtActivity.getDir("appcache", 0).path)
            settings.databasePath = this@WebViewX5KtActivity.getDir("databases", 0).path
            settings.setGeolocationDatabasePath(this@WebViewX5KtActivity.getDir("geolocation", 0)
                    .path)
            settings.setGeolocationEnabled(true)
            settings.databaseEnabled = true
            settings.setAppCacheEnabled(true)
            settings.allowFileAccess = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.setSupportMultipleWindows(false)
            settings.javaScriptCanOpenWindowsAutomatically = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                settings.setAllowUniversalAccessFromFileURLs(true)
            }
        }
        LogUtils.e("url--web--" + url)
        loadH5(url)
    }

    private fun loadH5(url: String?) {
        webView?.run {
            addJavascriptInterface(JsEventInterface(), "\$api")
            loadUrl(url)
            setDownloadListener { s, s1, s2, s3, l ->
                // url 你要访问的下载链接
                // userAgent 是HTTP请求头部用来标识客户端信息的字符串
                // contentDisposition 为保存文件提供一个默认的文件名
                // mimetype 该资源的媒体类型
                // contentLength 该资源的大小
                // 这几个参数都是可以通过抓包获取的
                // 用手机默认浏览器打开链接
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
        // 与js交互
        webView!!.webViewClient = object : WebViewClient() {
            //webview加载https链接错误或无响应
            override fun onReceivedSslError(webView: WebView, handler: SslErrorHandler?, sslError: SslError) {
                handler?.proceed()
                Toast.makeText(this@WebViewX5KtActivity, "" + sslError.primaryError, Toast.LENGTH_SHORT).show()
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                LogUtils.e("" + view.url)
                return false
            }

            /**
             * 在开始加载网页时会回调
             */
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                if (!isDialogShowing) {
                    showDialog()
                }
            }

            /**
             * 在结束加载网页时会回调
             */
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                dismissDialog()
                recycleview!!.visibility = View.GONE
            }
        }
        webView!!.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(webView: WebView?, i: Int) {
                super.onProgressChanged(webView, i)
            }

            override fun onCreateWindow(webView: WebView?, b: Boolean, b1: Boolean, message: Message?): Boolean {
                return super.onCreateWindow(webView, b, b1, message)
            }
        }
    }

    internal inner class JsEventInterface {

        @JavascriptInterface
        fun h5_call_function_token(): String {
            return SharePreUtils.getjwt_token(this@WebViewX5KtActivity)
        }

        @JavascriptInterface
        fun close() {
            finish()
        }

        @JavascriptInterface
        fun getLocation(fucName: String) {
            val js = "javascript:$fucName(1,{lat:39.910331,lng:116.470041})"
            webView!!.post { webView!!.loadUrl(js) }
        }


        @JavascriptInterface
        fun h5_call_function_jumpapp(action: String) {
        }

    }

    override fun onResume() {
        super.onResume()
        webView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView!!.onPause()
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()， 再 destory()
            val parent = webView!!.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(webView)
            }

            webView?.run {
                stopLoading()
                clearHistory()
                clearView()
                removeAllViews()
                destroy()
            }
            webView = null
        }
        super.onDestroy()
    }

    companion object {

        fun lauch(context: Context, title: String, url: String) {
            val intent = Intent(context, WebViewX5KtActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

}
