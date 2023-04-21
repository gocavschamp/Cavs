package com.example.myapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;

import com.example.myapp.R;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebViewClient;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.SharePreUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;

public class WebViewX5Activity extends BaseActivityWithTitle {

    WebView webView;
    //    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private String url;
    private ValueCallback<Uri> mUploadCallbackBelow;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private String TAG = WebViewX5Activity.class.getSimpleName();

    public static void lauch(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewX5Activity.class);
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
        flContent = findViewById(R.id.fl_content);
        webView = new WebView(getApplicationContext());
        flContent.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        titlelayout.setTitleText(title);
        initWeb();
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
        webView.setDownloadListener(new com.tencent.smtt.sdk.DownloadListener() {
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
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e(TAG, "shouldOverrideUrlLoading: " + request.getUrl().toString());

                /**
                 * 拦截tel:拨打电话。
                 */
                if (url.startsWith("tel:")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                    } catch (Exception e) {
                    }
                    return true;
                }
                /**
                 * 淘油宝支付拦截
                 */
                if (request.getUrl().toString().contains("platformapi/startapp")) {
                    try {
                        Intent intent;
                        intent = Intent.parseUri(request.getUrl().toString(),
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        startActivity(intent);
                    } catch (Exception e) {
                        new AlertDialog.Builder(mContext)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", (dialog, which) -> {
                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                    mContext.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                } else if (request.getUrl().toString().startsWith("weixin://wap/pay?") || request.getUrl().toString().startsWith("alipays://platformapi/startApp?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(request.getUrl());
                    startActivity(intent);
                    return true;
                }
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
            }
        });
        webView.setWebChromeClient(
                new WebChromeClient() {
                    /**
                     * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
                     */
                    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                        mUploadCallbackBelow = uploadMsg;
                        takePhoto();
                    }

                    /**
                     * API >= 21(Android 5.0.1)回调此方法
                     */
                    @Override
                    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                        mUploadCallbackAboveL = filePathCallback;
                        takePhoto();
                        return true;
                    }
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

    class JsEventInterface {

        public JsEventInterface() {

        }

        @JavascriptInterface
        public String h5_call_function_token() {
            return SharePreUtils.getjwt_token(WebViewX5Activity.this);
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

//    public void openWX(WxParam wxParam) {
//        if (wxParam == null) {
//            return;
//        }
//        IWXAPI api = WXAPIFactory.createWXAPI(this, RetrofitConfig.WX_PAY_APPID);
//        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//        req.userName = wxParam.getApp_id(); // 填小程序原始id
//        req.path = wxParam.getPath();
//        req.miniprogramType = wxParam.getType();
//        api.sendReq(req);
//    }
//
//    public void openAlipay(WxParam wxParam) {
//        //https://opensupport.alipay.com/support/knowledge/31867/201602383690?ant_source=zsearch
//        try {
//            String pathAll = wxParam.getPath();//这里是传的参数
//            LogUtils.e(pathAll);
//            String[] pathArr = pathAll.split("\\?");
//            String query = null;
//            String path = null;
//            if (pathArr != null && pathArr.length > 1) {
//                path = pathArr[0];
//                query = pathArr[1];
//
//            }
//            String link = URLEncoder.encode(query, "UTF-8");//这里是encode传的参数
//            String url = "alipays://platformapi/startapp?appId=" + wxParam.getApp_id() + "&page=" + path + "&query=" + link;
//            Log.e("url = ", url);
//            Uri uri = Uri.parse(url); // url为你要链接的地址
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 选择图片
     */
    @SuppressLint("CheckResult")
    private void takePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.addCategory(Intent.CATEGORY_OPENABLE);
                        i.setType("image/*");
                        startActivityForResult(Intent.createChooser(i, "Image Chooser"), 100);
                    } else {
                        //用户不同意使用权限
                        ToastUtils.show_middle(mContext, "应用缺少必要的权限！请打开所需要的权限。", 1);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //针对5.0以上, 以下区分处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            if (data != null) {
                // 这里是针对从文件中选图片的处理, 区别是一个返回的URI, 一个是URI[]
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }


    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
//            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                mUploadCallbackBelow.onReceiveValue(null);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
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
