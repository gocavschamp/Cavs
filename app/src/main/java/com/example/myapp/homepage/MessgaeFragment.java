package com.example.myapp.homepage;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapp.R;
import com.example.myapp.activity.EditInputActivity;
import com.example.myapp.activity.WebViewX5KtActivity;
import com.example.myapp.activity.game.GameActivity;
import com.example.myapp.activity.gesture.GestureLockActivity;
import com.example.myapp.activity.speak.SpeakActivity;
import com.example.myapp.activity.svga.SvgaGiftActivity;
import com.example.myapp.activity.tantan.TanTanActivity;
import com.example.myapp.homepage.homedemo.DBTestActivity;
import com.example.myapp.homepage.homedemo.EdittextTextActivity;
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
import com.example.myapp.widget.CustomView;
import com.example.myapp.widget.HupuGiftView;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.ui.TestBaseActivity;
import com.nucarf.base.utils.UiGoto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessgaeFragment extends BaseLazyFragment implements View.OnFocusChangeListener, View.OnClickListener, OnKeyboardListener, HupuGiftView.OnGiftClickLister {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.custom_view)
    CustomView customView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_card)
    EditText etCard;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.hupu)
    HupuGiftView hupu;
    @BindView(R.id.hupu2)
    HupuGiftView hupu2;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private ListAdapter mycenterAdapter;

    public MessgaeFragment() {
    }

    public static MessgaeFragment newInstance() {
        return new MessgaeFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.messgae_page;
    }

    @Override
    protected void initData() {
        ImmersionBar immersionBar = ImmersionBar.with(this).setOnKeyboardListener(this);
        List<String> data = new ArrayList<>();
        data.add("Edittext input limit");
        data.add("todo1");
        data.add("todo2");
        data.add("todo3");
        data.add("todo4");
        mycenterAdapter.setNewData(data);
    }

    @Override
    protected void initView() {
        etName.setOnFocusChangeListener(this);
        etPhone.setOnFocusChangeListener(this);
        etCard.setOnFocusChangeListener(this);
        etName.setOnClickListener(this);
        etPhone.setOnClickListener(this);
        etCard.setOnClickListener(this);
        hupu.setOnGiftClick(this);
        hupu2.setOnGiftClick(this);
        recycleview.setLayoutManager(new GridLayoutManager(mActivity, 2, RecyclerView.VERTICAL, false));
        mycenterAdapter = new ListAdapter(R.layout.mycenter_item);
        recycleview.setAdapter(mycenterAdapter);
        mycenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = mycenterAdapter.getData().get(position);
                switch (position) {
                    case 0:
                        UiGoto.startAty(mActivity, EditInputActivity.class);
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
                }
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.et_name:
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, etName.getTop());
                        }
                    });
                    break;
                case R.id.et_phone:
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, etPhone.getTop());
                        }
                    });

                    break;
                case R.id.et_card:
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, etCard.getTop());
                        }
                    });

                    break;
            }
        }
    }

    @OnClick(R.id.tvTitle)
    public void onViewClicked() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_name:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etName.getTop());
                    }
                });
                break;
            case R.id.et_phone:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etPhone.getTop());
                    }
                });

                break;
            case R.id.et_card:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etCard.getTop());
                    }
                });

                break;

        }
    }

    @Override
    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
        if (!isPopup) {
            scrollView.smoothScrollTo(0, 0);
        }
    }

    @Override
    public void ClickView(View view) {
        switch (view.getId()) {
            case R.id.hupu:
                if (hupu2.isShowing()) {
                    hupu2.switchGift();
                }
                hupu.switchGift();

                break;
            case R.id.hupu2:
                if (hupu.isShowing()) {
                    hupu.switchGift();
                }
                hupu2.switchGift();
                break;
        }

    }

    @Override
    public void ClickGiftView(View view) {

    }
}
