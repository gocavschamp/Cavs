package com.example.myapp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.activity.webViewer.LabelAdapter
import com.example.myapp.activity.webViewer.WebHistory
import com.example.myapp.bean.StringBean
import com.example.myapp.db.MySqliteHelper
import com.example.myapp.homepage.homedemo.videolist.VideoDetailsActivity
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.KeyboardUtil
import com.nucarf.base.utils.LogUtils
import com.nucarf.base.utils.SharePreUtils
import com.nucarf.base.utils.StringUtils
import com.nucarf.base.utils.ToastUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tencent.smtt.export.external.interfaces.*
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.activity_webx5.*
import java.util.*


class WebViewX5KtActivity : BaseActivityWithTitle() {

    private var islandport: Boolean = false
    private lateinit var labelAdapter: LabelAdapter
    private var mySqliteHelper: MySqliteHelper? = null
    internal var webView: WebView? = null
    private var url: String? = null
    private var webStationAdapter: WebStationAdapter? = null
    private var mUploadCallbackBelow: ValueCallback<Uri?>? = null
    private var mUploadCallbackAboveL: ValueCallback<Array<Uri?>?>? = null

    private var customViewCallback: IX5WebChromeClient.CustomViewCallback? = null
    private var customView: View? = null

    private var audioManager: AudioManager? = null
    private lateinit var listener: AudioManager.OnAudioFocusChangeListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webx5)
//        ButterKnife.bind(this)
        //        flContent = findViewById(R.id.fl_content);
        webView = WebView(applicationContext)
        fl_content!!.addView(
            webView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        titlelayout.setTitleText(title + "")
        titlelayout.setRightText("收藏书签☆")
        recycleview!!.layoutManager = GridLayoutManager(mContext, 5, RecyclerView.VERTICAL, false)
        webStationAdapter = WebStationAdapter(R.layout.item_web_station)
        recycleview!!.adapter = webStationAdapter
        labelAdapter = LabelAdapter(R.layout.item_db_history)
        rvHistoryLabel.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvHistoryLabel.adapter = labelAdapter
        initStation()
        initListener()
        initWeb()
        mySqliteHelper = MySqliteHelper.getHelperInstance(mContext)
        rvHistoryLabel.visibility = View.GONE
        audioWebViewControl()
    }

    private fun initStation() {
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
        val bean5 = StringBean()
        bean5.name = "好看视频"
        bean5.value = "https://haokan.baidu.com/"
        val bean6 = StringBean()
        bean6.name = "搜神谱"
        bean6.value = "https://www.jspoo.com/"
        data.add(bean)
        data.add(bean1)
        data.add(bean2)
        data.add(bean3)
        data.add(bean4)
        data.add(bean5)
        data.add(bean6)
        webStationAdapter!!.setNewData(data)
    }

    private fun audioWebViewControl() {
        audioManager = mContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        listener = AudioManager.OnAudioFocusChangeListener {
//            when (it) {
//                AudioManager.AUDIOFOCUS_LOSS -> ToastUtils.showShortToast(context, "loss")
//                AudioManager.AUDIOFOCUS_GAIN -> ToastUtils.showShortToast(context, "gain")
//            }
        }
    }

    private fun initListener() {
        titlelayout.setOnLongClickListener {
            StringUtils.copy(mContext, webView!!.url)
            true
        }
        titlelayout.setLeftClickListener {
            finish()
        }
        titlelayout.setRightClickListener {
            collect();
        }
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
            rvHistoryLabel.visibility = View.GONE
            recycleview.setVisibility(View.GONE);
            loadH5(webStationAdapter!!.data[position].value + "")
        }
        labelAdapter.setOnItemClickListener { adapter, view, position ->
            rvHistoryLabel.visibility = View.GONE
            loadH5(labelAdapter.getItem(position)?.url)
        }
        labelAdapter.setOnItemLongClickListener { adapter, view, position ->
            labelAdapter.getItem(position)
            StringUtils.copy(mContext, labelAdapter.getItem(position)?.url)
            true
        }
        labelAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.ivDelete -> {
                    labelAdapter.getItem(position)?.id?.let { deleteItem(tvLabel.isSelected, it) }
                }
            }

        }
        et_url!!.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            recycleview!!.visibility = if (hasFocus) View.VISIBLE else View.GONE
        }
        tvBack.setOnClickListener {
            if (webView!!.canGoBack()) {
                webView!!.goBack()
            }
        }
        tvNext.setOnClickListener {
            if (webView!!.canGoForward()) {
                webView!!.goForward()
            }
        }
        tvHome.setOnClickListener {
            rvHistoryLabel.visibility = View.GONE
            recycleview.visibility = View.VISIBLE
            fl_content.visibility = View.GONE

        }
        tvHistory.setOnClickListener {
            tvHistory.isSelected = !tvHistory.isSelected
            tvLabel.isSelected = false
            if (rvHistoryLabel.visibility == View.VISIBLE)
                rvHistoryLabel.visibility = View.GONE
            else
                getHistory()

        }
        tvLabel.setOnClickListener {
            tvHistory.isSelected = false
            tvLabel.isSelected = !tvLabel.isSelected
            if (rvHistoryLabel.visibility == View.VISIBLE)
                rvHistoryLabel.visibility = View.GONE
            else
                getLabel()
        }
        tvRefresh.setOnClickListener {
            tvHistory.isSelected = false
            tvRefresh.isSelected = !tvRefresh.isSelected
            webView?.reload()
        }
    }

    private fun getLabel() {
        val data = getData(MySqliteHelper.TABLE_NAME_LABEL)
        labelAdapter.setNewData(data)
        rvHistoryLabel.visibility = View.VISIBLE
    }

    private fun addLabel(title: String, url: String, iscollect: Int?) {
        val database = mySqliteHelper!!.writableDatabase
        val whereArgs = arrayOf(title, url)
        database.delete(MySqliteHelper.TABLE_NAME_WEB, "title=? and url = ?", whereArgs)
        val values = ContentValues()
        values.put("title", title)
        values.put("url", url)
        values.put("note", "" + System.currentTimeMillis())
        values.put("iscollect", iscollect)
        database.insert(MySqliteHelper.TABLE_NAME_LABEL, null, values)
        ToastUtils.show_middle(mContext, "成功", 0)

    }

    private fun getHistory() {
        val data = getData(MySqliteHelper.TABLE_NAME_WEB)
        labelAdapter.setNewData(data)
        rvHistoryLabel.visibility = View.VISIBLE
    }

    private fun deleteItem(isLabel: Boolean, id: Int) {
        // delete from Orders where Id = 7
        // 这里都条件筛选很灵活，不仅仅可以是 XX = ?，还可以是XX > ?，XX < ?，甚至是XX > ? and YY = ?，不过这样，第三个参数里面，就需要2个值了。
        val database = mySqliteHelper!!.writableDatabase
        val arrayOf = arrayOf(id.toString())
        database.delete(
            if (isLabel) MySqliteHelper.TABLE_NAME_LABEL else MySqliteHelper.TABLE_NAME_WEB,
            "id=?",
            arrayOf
        )
        val data =
            getData(if (isLabel) MySqliteHelper.TABLE_NAME_LABEL else MySqliteHelper.TABLE_NAME_WEB)
        labelAdapter.setNewData(data)
    }

    private fun addHistory(title: String, url: String, iscollect: Int?) {
        val database = mySqliteHelper!!.writableDatabase
        val whereArgs = arrayOf(title, url)
        database.delete(MySqliteHelper.TABLE_NAME_WEB, "title=? and url = ?", whereArgs)
        val values = ContentValues()
        values.put("title", title)
        values.put("url", url)
        values.put("note", "" + System.currentTimeMillis())
        database.insert(MySqliteHelper.TABLE_NAME_WEB, null, values)
    }

    private fun collect() {
        webView?.title?.let { webView?.url?.let { it1 -> addLabel(it, it1, 1) } }
    }

    override fun initData() {

    }

    private fun getData(tableName: String): MutableList<WebHistory> {
        val database: SQLiteDatabase = mySqliteHelper!!.getReadableDatabase();
//        database.beginTransaction();
        val cursor: Cursor = database.query(tableName, null, null, null, null, null, "id desc");
        var data = mutableListOf<WebHistory>();
        if (cursor.moveToFirst()) {
            do {
                when (tableName) {
                    MySqliteHelper.TABLE_NAME_WEB -> {

                        // name age height weight sex grade book like_star note
                        var id: String = cursor.getString(cursor.getColumnIndex("id"));
                        var title: String = cursor.getString(cursor.getColumnIndex("title"));
                        var url: String = cursor.getString(cursor.getColumnIndex("url"));
                        var nickname: String? = cursor.getString(cursor.getColumnIndex("nickname"));
                        var note: String? = cursor.getString(cursor.getColumnIndex("note"));
                        var iscollect: Int? = cursor.getInt(cursor.getColumnIndex("iscollect"));
                        var item = WebHistory(
                            id = id.toInt(),
                            title = title,
                            url = url,
                            nickname = nickname,
                            note = note,
                            iscollect = iscollect
                        )
                        data.add(item)

                    }
                    MySqliteHelper.TABLE_NAME_LABEL -> {
                        // name age height weight sex grade book like_star note
                        var id: String = cursor.getString(cursor.getColumnIndex("id"));
                        var title: String = cursor.getString(cursor.getColumnIndex("title"));
                        var url: String = cursor.getString(cursor.getColumnIndex("url"));
                        var nickname: String? = cursor.getString(cursor.getColumnIndex("nickname"));
                        var note: String? = cursor.getString(cursor.getColumnIndex("note"));
                        var iscollect: Int? = cursor.getInt(cursor.getColumnIndex("iscollect"));
                        var item = WebHistory(
                            id = id.toInt(),
                            title = title,
                            url = url,
                            nickname = nickname,
                            note = note,
                            iscollect = iscollect
                        )
                        data.add(item)

                    }
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return data
//        database.setTransactionSuccessful();
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
            settings.setGeolocationDatabasePath(
                this@WebViewX5KtActivity.getDir("geolocation", 0)
                    .path
            )
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
        fl_content.visibility = View.VISIBLE
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
            override fun onReceivedSslError(
                webView: WebView,
                handler: SslErrorHandler?,
                sslError: SslError
            ) {
                handler?.proceed()
                Toast.makeText(
                    this@WebViewX5KtActivity,
                    "" + sslError.primaryError,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                LogUtils.e("tag", "shouldOverrideUrlLoading---" + view.url)
                /**
                 * 拦截tel:拨打电话。
                 */
                if (view.url.startsWith("tel:")) {
                    try {
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                    } catch (e: Exception) {
                    }
                    return true
                }
                if (view.url.contains("3gp") || view.url.contains("mp4") || view.url.contains("flv")
                    || view.url.contains("rmvb") || view.url.contains("mpeg") || view.url.contains("wmv")
                    || view.url.contains("avi") || view.url.contains("mov") || view.url.contains("mpv")
                ) {
                    //todo  local video player init
                    ToastUtils.showShort(view.url.toString())
                    VideoDetailsActivity.launcher(mContext, view.url)
                    return true;
                }
                return false
            }

            /**
             * 在开始加载网页时会回调
             */
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                LogUtils.e("tag", "onPageStarted-----" + url)

                super.onPageStarted(view, url, favicon)
                showDialog()
            }

            override fun onLoadResource(p0: WebView?, p1: String?) {
                super.onLoadResource(p0, p1)
                LogUtils.i("tag", "onLoadResource-----" + url)
            }

            /**
             * 在结束加载网页时会回调
             */
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                LogUtils.e("tag", "----onPageFinished-----" + url)
                recycleview.visibility = View.GONE
                dismissDialog()
            }
        }
        webView!!.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(
                view: View?,
                callback: IX5WebChromeClient.CustomViewCallback?
            ) {
//                super.onShowCustomView(view, callback)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (customView != null) {
                    customViewCallback?.onCustomViewHidden()
                    return
                }
                customView = view
                customViewCallback = callback
                fl_video.visibility = View.VISIBLE
                fl_video.addView(customView)
                ll_bar.visibility = View.GONE
                ll_search.visibility = View.GONE
                rlOption.visibility = View.GONE
            }

            override fun onHideCustomView() {
//                super.onHideCustomView()
                if (customViewCallback == null) return
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                if (customViewCallback != null) {
                    fl_video.removeView(customView)
                    fl_video.visibility = View.GONE
                    customView?.visibility = View.GONE
                    ll_bar.visibility = View.VISIBLE
                    ll_search.visibility = View.VISIBLE
                    rlOption.visibility = View.VISIBLE
                    customView = null
                    customViewCallback!!.onCustomViewHidden()

                }
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
             */
            override fun openFileChooser(
                uploadMsg: ValueCallback<Uri?>,
                acceptType: String?,
                capture: String?
            ) {
                mUploadCallbackBelow = uploadMsg
                takePhoto()
            }

            /**
             * API >= 21(Android 5.0.1)回调此方法
             */
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>?>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = filePathCallback
                takePhoto()
                return true
            }

            override fun onReceivedTitle(web: WebView?, title: String?) {
                super.onReceivedTitle(web, title)
                titlelayout.setTitleText(title)
                dismissDialog()
                LogUtils.e("tag", "onReceivedTitle" + title)
                title?.let { web?.url?.let { it1 -> addHistory(it, it1, 0) } }
            }

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onJsAlert(
                webView: WebView?,
                s: String,
                s1: String,
                jsResult: JsResult
            ): Boolean {
                LogUtils.e("web--js  alert==$s---$s1---$jsResult")
                return super.onJsAlert(webView, s, s1, jsResult)
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("webview", " 现在是横屏1");
            islandport = false;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("webview", " 现在是竖屏1");
            islandport = true;
        }
    }

    override fun onResume() {
        super.onResume()
        webView!!.onResume()
    }

    override fun onPause() {
        var i = 0
        do {
            val result = audioManager?.requestAudioFocus(
                listener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                break
            }
            i++
        } while (i < 10)
        super.onPause()
        webView!!.onPause()
    }

    override fun onBackPressed() {
        if (customView != null) {
            webView!!.webChromeClient.onHideCustomView()
        }
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


    /**
     * 选择图片
     */
    @SuppressLint("CheckResult")
    private fun takePhoto() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
            .subscribe { aBoolean: Boolean ->
                if (aBoolean) {
                    val i = Intent(Intent.ACTION_GET_CONTENT)
                    i.addCategory(Intent.CATEGORY_OPENABLE)
                    i.type = "image/*"
                    startActivityForResult(Intent.createChooser(i, "Image Chooser"), 100)
                } else {
                    //用户不同意使用权限
                    ToastUtils.show_middle(mContext, "应用缺少必要的权限！请打开所需要的权限。", 1)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            //针对5.0以上, 以下区分处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data)
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data)
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private fun chooseAbove(resultCode: Int, data: Intent?) {
        if (RESULT_OK == resultCode) {
            if (data != null) {
                // 这里是针对从文件中选图片的处理, 区别是一个返回的URI, 一个是URI[]
                val results: Array<Uri?>
                val uriData = data.data
                if (uriData != null) {
                    results = arrayOf(uriData)
                    mUploadCallbackAboveL!!.onReceiveValue(results)
                } else {
                    mUploadCallbackAboveL!!.onReceiveValue(null)
                }
            } else {
                mUploadCallbackAboveL!!.onReceiveValue(null)
            }
        } else {
            mUploadCallbackAboveL!!.onReceiveValue(null)
        }
        mUploadCallbackAboveL = null
    }


    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private fun chooseBelow(resultCode: Int, data: Intent?) {
        if (RESULT_OK == resultCode) {
//            updatePhotos();
            if (data != null) {
                // 这里是针对文件路径处理
                val uri = data.data
                if (uri != null) {
                    mUploadCallbackBelow!!.onReceiveValue(uri)
                } else {
                    mUploadCallbackBelow!!.onReceiveValue(null)
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                mUploadCallbackBelow!!.onReceiveValue(null)
            }
        } else {
            mUploadCallbackBelow!!.onReceiveValue(null)
        }
        mUploadCallbackBelow = null
    }

}
