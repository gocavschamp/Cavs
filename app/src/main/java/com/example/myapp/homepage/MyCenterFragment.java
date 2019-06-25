package com.example.myapp.homepage;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.homepage.homedemo.BottomSheetBihaverActivity;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.UiGoto;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCenterFragment extends BaseLazyFragment {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_bottom_sheet)
    TextView tvBottomSheet;

    public MyCenterFragment() {
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
        tvBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiGoto.startAty(mActivity, BottomSheetBihaverActivity.class);

            }
        });
    }

//    @OnClick({R.id.tvTitle, R.id.tv_bottom_sheet})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tvTitle:
//                break;
//            case R.id.tv_bottom_sheet:
////                UiGoto.startAty(mActivity, BottomSheetBihaverActivity.class);
//                mActivity.startActivity(new Intent(mActivity, BottomSheetBihaverActivity.class));
//
//                break;
//        }
//    }
}
