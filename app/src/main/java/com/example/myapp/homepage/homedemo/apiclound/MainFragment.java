package com.example.myapp.homepage.homedemo.apiclound;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.nucarf.base.utils.LogUtils;
import com.uzmap.pkg.openapi.APIListener;
import com.uzmap.pkg.openapi.SuperFragment;
import com.uzmap.pkg.openapi.WebViewProvider;

/**
 * Created by yuwenming on 2019/12/23.
 */
public class MainFragment extends SuperFragment {
    @Override
    public String getStartUrl() {
        return getArguments().getString("url");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        setApiListener(new APIListener() {
            @Override
            protected void onProgressChanged(WebViewProvider provider, int newProgress) {
                super.onProgressChanged(provider, newProgress);
                LogUtils.e("onProgressChanged---->"+newProgress);

            }

            @Override
            protected void onReceivedTitle(WebViewProvider provider, String title) {
                super.onReceivedTitle(provider, title);
                LogUtils.e("onReceivedTitle---->"+title);

            }

            @Override
            protected void onPageFinished(WebViewProvider provider, String url) {
                super.onPageFinished(provider, url);
                LogUtils.e("onPageFinished---->"+url);

//                evaluateJavascript();
            }
        });
    }
}
