package com.example.myapp.homepage;

import com.example.myapp.R;
import com.nucarf.base.ui.BaseLazyFragment;

public class HelloGroundFragment extends BaseLazyFragment {


    public HelloGroundFragment() {
    }

    public static HelloGroundFragment newInstance() {
        return new HelloGroundFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.hello_ground_page;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
}
