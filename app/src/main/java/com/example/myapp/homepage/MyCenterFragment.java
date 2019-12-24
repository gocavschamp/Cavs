package com.example.myapp.homepage;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.homepage.homedemo.RxjavaDemoActivity;
import com.example.myapp.homepage.homedemo.amap.SearchWayResultActivity;
import com.example.myapp.homepage.homedemo.apiclound.ApiCloundTestActivity;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetBihaverActivity;
import com.example.myapp.homepage.homedemo.multitem.MultItemActivity;
import com.example.myapp.homepage.homedemo.xunfei.XunFeiYuYinActivity;
import com.example.myapp.homepage.homedemo.zxing.QrcodeZxingDemoActivity;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.UiGoto;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCenterFragment extends BaseLazyFragment {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_bottom_sheet)
    TextView tvBottomSheet;
    @BindView(R.id.tv_xunfei_yuyin)
    TextView tvXunfeiYuyin;
    @BindView(R.id.tv_rxjava)
    TextView tvRxjava;
    @BindView(R.id.tv_mult_item)
    TextView tvMultItem;
    @BindView(R.id.tv_apiclound)
    TextView tvApiclound;

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
    }



    @OnClick({R.id.tv_bottom_sheet, R.id.tv_zxing,R.id.tv_xunfei_yuyin, R.id.tv_rxjava, R.id.tv_mult_item, R.id.tv_apiclound, R.id.tv_amap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_sheet:
                UiGoto.startAty(mActivity, BottomSheetBihaverActivity.class);
                break;
            case R.id.tv_amap:
                UiGoto.startAty(mActivity, SearchWayResultActivity.class);
                break;
            case R.id.tv_xunfei_yuyin:
                UiGoto.startAty(mActivity, XunFeiYuYinActivity.class);
                break;
            case R.id.tv_rxjava:
                UiGoto.startAty(mActivity, RxjavaDemoActivity.class);
                break;
            case R.id.tv_mult_item:
                UiGoto.startAty(mActivity, MultItemActivity.class);
                break;
            case R.id.tv_zxing:
                UiGoto.startAty(mActivity, QrcodeZxingDemoActivity.class);
                break;
            case R.id.tv_apiclound:
                Intent intent = new Intent(mActivity, ApiCloundTestActivity.class);
                int nextInt = new Random().nextInt();
                if (nextInt % 2 == 0) {
//                    intent.putExtra("startUrl", "https://docs.apicloud.com/Dev-Guide/SuperWebview-guide-for-android");
                }
                mActivity.startActivity(intent);
                break;
        }
    }
}
