package com.example.myapp.homepage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.example.myapp.homepage.homedemo.EdittextTextActivity;
import com.example.myapp.homepage.homedemo.RxjavaDemoActivity;
import com.example.myapp.homepage.homedemo.amap.SearchWayResultActivity;
import com.example.myapp.homepage.homedemo.apiclound.ApiCloundTestActivity;
import com.example.myapp.homepage.homedemo.bannertest.BannerActivity;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetBihaverActivity;
import com.example.myapp.homepage.homedemo.multitem.MultItemActivity;
import com.example.myapp.homepage.homedemo.videolist.DouYinListActivity;
import com.example.myapp.homepage.homedemo.videolist.VideoListActivity;
import com.example.myapp.homepage.homedemo.xunfei.XunFeiYuYinActivity;
import com.example.myapp.homepage.homedemo.zxing.QrcodeZxingDemoActivity;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.UiGoto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseLazyFragment {


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
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_amap)
    TextView tvAmap;
    @BindView(R.id.tv_zxing)
    TextView tvZxing;
    private MycenterAdapter mycenterAdapter;

    private HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.home_page;
    }

    @Override
    protected void initData() {

        List<String> data = new ArrayList<>();
        data.add("bottom_sheet");
        data.add("xunfei_yuyin");
        data.add("rxjava");
        data.add("recycleview多布局");
        data.add("apicloud");
        data.add("高德地图");
        data.add("二维码zxing");
        data.add("列表播放");
        data.add("抖音列表播放");
        data.add("EdittextTextActivity");
        data.add("banner 轮播");
        mycenterAdapter.setNewData(data);


    }

    @Override
    protected void initView() {
        new Handler().sendMessage(Message.obtain());
        new Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });
        recycleview.setLayoutManager(new GridLayoutManager(mActivity, 3, RecyclerView.VERTICAL, false));
        mycenterAdapter = new MycenterAdapter(R.layout.mycenter_item);
        recycleview.setAdapter(mycenterAdapter);
        mycenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = mycenterAdapter.getData().get(position);
                switch (position) {
                    case 0:
                        UiGoto.startAty(mActivity, BottomSheetBihaverActivity.class);
                        break;
                    case 1:
                        UiGoto.startAty(mActivity, XunFeiYuYinActivity.class);
                        break;
                    case 2:
                        UiGoto.startAty(mActivity, RxjavaDemoActivity.class);
                        break;
                    case 3:
                        UiGoto.startAty(mActivity, MultItemActivity.class);
                        break;
                    case 4:
                        Intent intent = new Intent(mActivity, ApiCloundTestActivity.class);
                        mActivity.startActivity(intent);
                        break;
                    case 5:
                        UiGoto.startAty(mActivity, SearchWayResultActivity.class);
                        break;
                    case 6:
                        UiGoto.startAty(mActivity, QrcodeZxingDemoActivity.class);
                        break;
                    case 7:
                        UiGoto.startAty(mActivity, VideoListActivity.class);

                        break;
                    case 8:
                        UiGoto.startAty(mActivity, DouYinListActivity.class);

                        break;
                    case 9:
                        UiGoto.startAty(mActivity, EdittextTextActivity.class);

                        break;
                    case 10:
                        UiGoto.startAty(mActivity, BannerActivity.class);

                        break;
                }
            }
        });
    }


    @OnClick({R.id.tv_bottom_sheet, R.id.tv_zxing, R.id.tv_xunfei_yuyin, R.id.tv_rxjava, R.id.tv_mult_item, R.id.tv_apiclound, R.id.tv_amap})
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
                mActivity.startActivity(intent);
                break;
        }
    }

    private class MycenterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public MycenterAdapter(int layout) {
            super(layout);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_info, item);

        }
    }
}
