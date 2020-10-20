package com.example.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.bean.StringBean;
import com.nucarf.base.R;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.KeyboardUtil;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.SharePreUtils;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewX5KtActivity extends BaseActivityWithTitle {

    WebView webView;
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.btn_seach)
    Button btnSeach;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    private String url;
    private WebStationAdapter webStationAdapter;

    public static void lauch(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewX5KtActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webx5);
        ButterKnife.bind(this);
//        flContent = findViewById(R.id.fl_content);
        webView = new WebView(getApplicationContext());
        flContent.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        titlelayout.setTitleText(title + "");
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false));
        webStationAdapter = new WebStationAdapter(R.layout.item_web_station);
        recycleview.setAdapter(webStationAdapter);
        List data = new ArrayList<StringBean>();
        StringBean bean = new StringBean();
        bean.setName("百度");
        bean.setValue("https://www.baidu.com/");
        StringBean bean1 = new StringBean();
        bean1.setName("知乎");
        bean1.setValue("https://www.zhihu.com/");
        StringBean bean2 = new StringBean();
        bean2.setName("掘金");
        bean2.setValue("https://juejin.im/android");
        StringBean bean3 = new StringBean();
        bean3.setName("掘金");
        bean3.setValue("https://juejin.im/android");
        StringBean bean4 = new StringBean();
        bean4.setName("虎扑");
        bean4.setValue("https://m.hupu.com/");
        data.add(bean);
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        webStationAdapter.setNewData(data);
        initListener();
        initWeb();
    }

    private void initListener() {
        webStationAdapter.setOnItemClickListener((adapter, view, position) -> {
//            recycleview.setVisibility(View.GONE);
            loadH5(webStationAdapter.getData().get(position).getValue() + "");
        });
        etUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                recycleview.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void initWeb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速

        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webView.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAppCachePath(this.getDir("appcache", 0).getPath());
        webView.getSettings().setDatabasePath(this.getDir("databases", 0).getPath());
        webView.getSettings().setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportMultipleWindows(false);
        // 修改ua使得web端正确判断
//        String ua = webView.getSettings().getUserAgentString();
//        webView.getSettings().setUserAgentString(ua + ";isApp");
        // 设置允许JS弹窗
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        LogUtils.e("url--web--" + url);
        loadH5(url);
    }

    private void loadH5(final String url) {
        webView.addJavascriptInterface(new JsEventInterface(), "$api");
        webView.loadUrl(url);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                // url 你要访问的下载链接
                // userAgent 是HTTP请求头部用来标识客户端信息的字符串
                // contentDisposition 为保存文件提供一个默认的文件名
                // mimetype 该资源的媒体类型
                // contentLength 该资源的大小
                // 这几个参数都是可以通过抓包获取的
                // 用手机默认浏览器打开链接
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        // 与js交互
        webView.setWebViewClient(new WebViewClient() {
            //webview加载https链接错误或无响应
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler handler, SslError sslError) {
                if (handler != null) {
                    handler.proceed();//忽略证书的错误继续加载页面内容，不会变成空白页面
                }
                Toast.makeText(WebViewX5KtActivity.this, "" + sslError.getPrimaryError(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                LogUtils.e("" + view.getUrl());
                return false;
            }

            /**
             * 在开始加载网页时会回调
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!isDialogShowing()) {
                    showDialog();
                }
            }

            /**
             * 在结束加载网页时会回调
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissDialog();
                recycleview.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(
                new WebChromeClient() {

                    @Override
                    public void onProgressChanged(WebView webView, int i) {
                        super.onProgressChanged(webView, i);
                    }

                    @Override
                    public boolean onCreateWindow(WebView webView, boolean b, boolean b1, Message message) {
                        return super.onCreateWindow(webView, b, b1, message);
                    }
                }
        );
    }

    @OnClick(R.id.btn_seach)
    public void onViewClicked() {
        String url = etUrl.getText().toString();
        boolean contains = url.contains("http://")||url.contains("https://");
        url = contains ? url : "https://" + url;
        Uri parse = Uri.parse(url);
        LogUtils.e("url--input--" + parse.toString());
        loadH5(parse.toString());
        KeyboardUtil.hideInput(etUrl, mContext);
    }

    class JsEventInterface {

        public JsEventInterface() {

        }

        @JavascriptInterface
        public String h5_call_function_token() {
            return SharePreUtils.getjwt_token(WebViewX5KtActivity.this);
        }

        @JavascriptInterface
        public void close() {
            finish();
        }

        @JavascriptInterface
        public void getLocation(String fucName) {
            String js = "javascript:" + fucName + "(1," + "{lat:39.910331,lng:116.470041}" + ")";
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(js);
                }
            });
        }


        @JavascriptInterface
        public void h5_call_function_jumpapp(String action) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()， 再 destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
