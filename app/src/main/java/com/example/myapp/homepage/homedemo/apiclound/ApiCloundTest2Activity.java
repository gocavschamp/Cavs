package com.example.myapp.homepage.homedemo.apiclound;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.moonlight.flyvideo.R;
import com.uzmap.pkg.openapi.ExternalActivity;
import com.uzmap.pkg.openapi.Html5EventListener;
import com.uzmap.pkg.openapi.WebViewProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApiCloundTest2Activity extends ExternalActivity {

    private static final String TAG = "tag--";
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    private WebViewProvider mProvider;
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private MainFragment twoFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apicloud2_layout);
        ButterKnife.bind(this);


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
        x.setVisibility(View.GONE);
        x.setGravity(Gravity.CENTER);
        x.setBackgroundColor(Color.RED);
        x.setClickable(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                200,
                200);
        x.setLayoutParams(layoutParams);
//        contentParent.addView(x);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        Intent mFirstPgaeIntent = new Intent(ApiCloundTest2Activity.this, MultItemActivity.class);
//                View v = getLocalActivityManager().startActivity("one", mFirstPgaeIntent).getDecorView();
//                contentParent.addView(v);
            }
        });
        fragmentManager = getFragmentManager();
        mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://www.jianshu.com");
        mainFragment.setArguments(bundle);
        twoFragment = new MainFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://www.baidu.com");
        twoFragment.setArguments(bundle1);
        fragmentManager.beginTransaction().add(R.id.fl_content, mainFragment).commitAllowingStateLoss();

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
        finish();
    }

    @OnClick({R.id.tv_open, R.id.tv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open:
                drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_head:
                if (mainFragment.isVisible()) {
                    fragmentManager.beginTransaction().replace(R.id.fl_content, twoFragment).commitAllowingStateLoss();
                    twoFragment.getSuperWebview();
                    drawerlayout.closeDrawers();
                } else {
                    fragmentManager.beginTransaction().replace(R.id.fl_content, mainFragment).commitAllowingStateLoss();
                    drawerlayout.closeDrawers();
                }
                break;
        }
    }
}
