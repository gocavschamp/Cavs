package com.example.myapp.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.activity.animals.AnimalsListActivity;
import com.example.myapp.activity.animals.FamilyActivity;
import com.example.myapp.activity.child.ChildAbcLearnActivity;
import com.example.myapp.activity.touping.TouPingLearnActivity;
import com.example.myapp.google.GoogleInfoActivity;
import com.moonlight.flyvideo.R;
import com.example.myapp.activity.WebViewX5KtActivity;
import com.example.myapp.activity.game.GameActivity;
import com.example.myapp.activity.gesture.GestureLockActivity;
import com.example.myapp.activity.speak.SpeakActivity;
import com.example.myapp.activity.svga.SvgaGiftActivity;
import com.example.myapp.activity.tantan.TanTanActivity;
import com.example.myapp.homepage.homedemo.DBTestActivity;
import com.example.myapp.homepage.homedemo.EdittextTextActivity;
import com.example.myapp.homepage.homedemo.FlutterTestActivity;
import com.example.myapp.homepage.homedemo.RxjavaDemoActivity;
import com.example.myapp.homepage.homedemo.amap.SearchWayResultActivity;
import com.example.myapp.homepage.homedemo.apiclound.ApiCloundTestActivity;
import com.example.myapp.homepage.homedemo.bannertest.BannerActivity;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetBihaverActivity;
import com.example.myapp.homepage.homedemo.dialogshow.DialogShowActivity;
import com.example.myapp.homepage.homedemo.dragscallview.DragAndScallActivity;
import com.example.myapp.homepage.homedemo.multitem.MultItemActivity;
import com.example.myapp.homepage.homedemo.videolist.DouYinListActivity;
import com.example.myapp.homepage.homedemo.videolist.VideoListActivity;
import com.example.myapp.homepage.homedemo.xunfei.XunFeiYuYinActivity;
import com.example.myapp.homepage.homedemo.zxing.QrcodeZxingDemoActivity;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.ui.TestBaseActivity;
import com.nucarf.base.utils.UiGoto;
import com.nucarf.base.widget.CircleImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.animal)
    CircleImageView animal;
    @BindView(R.id.mama)
    CircleImageView mama;
    private ListAdapter mycenterAdapter;

    public HomeFragment() {
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
        data.add("dialogshow");
        data.add("下拉回弹  拖拽布局");
        data.add("数据库增删改查，升级操作");
        data.add("webview");
        data.add("flutter");
        data.add("flutterFragment");
        data.add("tantan");
        data.add("svga ANIMATION");
        data.add("娱乐天地");
        data.add("语音播报");
        data.add("test");
        data.add("OCR 扫码 百度");
        data.add("手势解锁");
        data.add("小动物");
        data.add("A B C D");
        data.add("投 屏");
        data.add("locations");

        mycenterAdapter.setNewData(data);


    }

    @Override
    protected void initView() {
        recycleview.setLayoutManager(new GridLayoutManager(mActivity, 3, RecyclerView.VERTICAL, false));
        mycenterAdapter = new ListAdapter(R.layout.mycenter_item);
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
                    case 11:
                        UiGoto.startAty(mActivity, DialogShowActivity.class);

                        break;
                    case 12:
                        UiGoto.startAty(mActivity, DragAndScallActivity.class);

                        break;
                    case 13:
                        UiGoto.startAty(mActivity, DBTestActivity.class);

                        break;
                    case 14:
                        UiGoto.startAty(mActivity, WebViewX5KtActivity.class);

                        break;
                    case 15:
//                        Intent intentflutter = FlutterActivity.withCachedEngine("my_engine_id").build(mActivity);
//                        startActivity(intentflutter);
                        break;
                    case 16:
//                        UiGoto.startAty(mActivity, FlutterTestActivity.class);
                        break;
                    case 17:
                        UiGoto.startAty(mActivity, TanTanActivity.class);
                        break;
                    case 18:
                        UiGoto.startAty(mActivity, SvgaGiftActivity.class);
                        break;
                    case 19:
                        UiGoto.startAty(mActivity, GameActivity.class);
                    case 20:
                        UiGoto.startAty(mActivity, SpeakActivity.class);
                        break;
                    case 21:
                        UiGoto.startAty(mActivity, TestBaseActivity.class);
                        break;
                    case 22:
//                        UiGoto.startAty(mActivity, OcrReadActivity.class);
                        break;
                    case 23:
                        UiGoto.startAty(mActivity, GestureLockActivity.class);
                        break;
                    case 24:
                        UiGoto.startAty(mActivity, AnimalsListActivity.class);
                        break;
                    case 25:
//                        UiGoto.startAty(mActivity, ChildAbcLearnActivity.class);
                        ChildAbcLearnActivity.Companion.invoke(mActivity,0);
                        break;
                    case 26:
//                        UiGoto.startAty(mActivity, ChildAbcLearnActivity.class);
                        TouPingLearnActivity.Companion.invoke(mActivity,0);
                        break;
                    case 27:
//                        UiGoto.startAty(mActivity, ChildAbcLearnActivity.class);
                        UiGoto.startAty(mActivity, GoogleInfoActivity.class);

                        break;
                }
            }
        });
    }


    @OnClick({R.id.tv_bottom_sheet, R.id.tv_zxing,
            R.id.animal,
            R.id.mama,
            R.id.tv_xunfei_yuyin, R.id.tv_rxjava, R.id.tv_mult_item, R.id.tv_apiclound, R.id.tv_amap})
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
            case R.id.animal:
                UiGoto.startAty(mActivity, AnimalsListActivity.class);
                break;
            case R.id.mama:
                getPemision();
                UiGoto.startAty(mActivity, FamilyActivity.class);
                break;
        }
    }
    @SuppressLint("CheckResult")
    private void getPemision() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }

        });
    }

}
