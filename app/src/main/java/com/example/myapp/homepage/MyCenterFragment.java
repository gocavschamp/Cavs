package com.example.myapp.homepage;

import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetBihaverActivity;
import com.example.myapp.homepage.homedemo.xunfei.XunFeiYuYinActivity;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.UiGoto;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCenterFragment extends BaseLazyFragment implements View.OnClickListener {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_bottom_sheet)
    TextView tvBottomSheet;
    @BindView(R.id.tv_xunfei_yuyin)
    TextView tvXunfeiYuyin;

    private MyCenterFragment() {
    }

    public static MyCenterFragment newInstance() {
        return new MyCenterFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.home_page;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        tvBottomSheet = mRootView.findViewById(R.id.tv_bottom_sheet);
        tvXunfeiYuyin = mRootView.findViewById(R.id.tv_xunfei_yuyin);
        tvBottomSheet.setOnClickListener(this);
        tvXunfeiYuyin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_xunfei_yuyin :
                UiGoto.startAty(mActivity, XunFeiYuYinActivity.class);

                break;
            case R.id.tv_bottom_sheet :
                UiGoto.startAty(mActivity, BottomSheetBihaverActivity.class);

                break;
        }

    }
}
