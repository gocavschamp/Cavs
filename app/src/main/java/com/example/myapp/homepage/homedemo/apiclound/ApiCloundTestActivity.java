package com.example.myapp.homepage.homedemo.apiclound;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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
        View decorView = getWindow().getDecorView();
        FrameLayout contentParent =
                (FrameLayout) decorView.findViewById(android.R.id.content);
        TextView x = new TextView(this);
        x.setText("这是一个TextView");
        x.setGravity(Gravity.CENTER);
        x.setBackgroundColor(Color.RED);
        x.setClickable(true);
        x.setLayoutParams(
                new ViewGroup.LayoutParams(
                        200,
                        200));
        contentParent.addView(x);

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
        evaluateJavascript("api.closeWin();");
//        mProvider.goBack();
        return;
//        if (mProvider.canGoBack()) {
//        } else {
//            finish();
//        }
//        super.onBackPressed();
    }
}
