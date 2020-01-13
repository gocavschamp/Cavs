package com.example.myapp.homepage;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.widget.CustomImageView;
import com.example.myapp.widget.CustomLinealayout;
import com.example.myapp.widget.CustomProgressBar;
import com.nucarf.base.ui.BaseLazyFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.LAYER_TYPE_SOFTWARE;

public class HelloGroundFragment extends BaseLazyFragment {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ci_pic)
    CustomImageView ciPic;
    @BindView(R.id.cl_layout)
    CustomLinealayout clLayout;
    @BindView(R.id.progress_bar)
    CustomProgressBar progressBar;
    @BindView(R.id.et_angle)
    EditText etAngle;
    @BindView(R.id.tv_angle)
    TextView tvAngle;
    private boolean isStar;

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
        clLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStar) {
                    clLayout.startRippleAnimation();
                } else {
                    clLayout.stopRippleAnimation();
                }
                isStar = !isStar;
            }
        });
        progressBar.setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.tv_angle)
    public void onViewClicked() {
        if(!TextUtils.isEmpty(etAngle.getText())) {
            float min = Math.min(100f, Float.parseFloat(etAngle.getText().toString()));
            progressBar.resetValue(min);
        }
    }
}
