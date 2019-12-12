package com.example.myapp.homepage.homedemo.apiclound;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.myapp.R;
import com.uzmap.pkg.openapi.ExternalActivity;
import com.uzmap.pkg.openapi.Html5EventListener;
import com.uzmap.pkg.openapi.WebViewProvider;

public class ApiCloundTestActivity extends ExternalActivity {

    private static final String TAG = "tag--";
    private WebViewProvider mProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Html5EventListener html5EventListener = new Html5EventListener("save") {
            @Override
            public void onReceive(WebViewProvider webViewProvider, Object o) {

            }
        };
        this.addHtml5EventListener(html5EventListener);
    }
    @Override
    protected void onPageStarted(WebViewProvider provider, String url, Bitmap favicon) {
        super.onPageStarted(provider, url, favicon);
        Log.d(TAG, "onPageStarted: " + url);
        mProvider = provider;
    }

    @Override
    protected void onPageFinished(WebViewProvider provider, String url) {
        super.onPageFinished(provider, url);
        Log.d(TAG, "onPageFinished: " + url);
        mProvider = provider;

    }

    @Override
    protected boolean shouldOverrideUrlLoading(WebViewProvider provider, String url) {
        Log.d(TAG, "shouldOverrideUrlLoading: " + url);
        return super.shouldOverrideUrlLoading(provider, url);
    }

    @Override
    protected void onReceivedTitle(WebViewProvider provider, String title) {
        super.onReceivedTitle(provider, title);
        mProvider = provider;
        Log.d(TAG, "onReceivedTitle: " + title);
        if ("SuperWebview DEMO".equals(title)) {
//            provider.loadUrl("http://www.baidu.com");
//            evaluateJavascript("window.location.href='http://www.baidu.com';");
        }
    }

    @Override
    public void onBackPressed() {
        if (mProvider.canGoBack()) {
            mProvider.goBack();
            return;
        } else {
            finish();
        }
        super.onBackPressed();
    }
}
