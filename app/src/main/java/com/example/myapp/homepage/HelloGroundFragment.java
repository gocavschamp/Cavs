package com.example.myapp.homepage;

import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.widget.CustomImageView;
import com.example.myapp.widget.CustomLinealayout;
import com.nucarf.base.ui.BaseLazyFragment;

import butterknife.BindView;

public class HelloGroundFragment extends BaseLazyFragment {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ci_pic)
    CustomImageView ciPic;
    @BindView(R.id.cl_layout)
    CustomLinealayout clLayout;

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
