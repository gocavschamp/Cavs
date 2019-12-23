package com.example.myapp.homepage.homedemo.apiclound;

import com.uzmap.pkg.openapi.SuperFragment;

/**
 * Created by yuwenming on 2019/12/23.
 */
public class MainFragment extends SuperFragment {
    @Override
    public String getStartUrl() {
        return getArguments().getString("url");
    }
}
