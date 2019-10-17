package com.example.myapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.mian.adapter.ViewPagerAdapterMain;
import com.example.myapp.utils.ExampleUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.utils.SharePreUtils;
import com.nucarf.base.widget.ViewPagerSlide;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnTabSelectListener {

    private static final String TAG = MainActivity.class.getSimpleName().toString();
    @BindView(R.id.vp_main)
    ViewPagerSlide vpMain;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    //    @BindView(R.id.stl_main)
//    SlidingTabLayout tabLayout;
//    @BindView(R.id.vp_main)
//    ViewPagerSlide vpMain;
    private ViewPagerAdapterMain viewPagerAdapterMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(false, 0.2f).init();
        registerMessageReceiver();  // used for receive msg
        //修改 测试分支
        if (!SharePreUtils.getIsSetAlias()) {
            setAlias();
        }
    }

    @Override
    protected void initData() {
        initViewPager();

        getPemision();
    }

    private void getPemision() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    private void initViewPager() {
        viewPagerAdapterMain = new ViewPagerAdapterMain(getSupportFragmentManager());
        vpMain.setAdapter(viewPagerAdapterMain);
        vpMain.setSlidAble(false);
        vpMain.setOffscreenPageLimit(viewPagerAdapterMain.COUNT);
        vpMain.addOnPageChangeListener(this);
        stlMain.setViewPager(vpMain, new String[]{"首页", "论坛", "消息"});
//        stlMain.setViewPager(vpMain, new String[]{"首页", "首页", "wodededeeee"});
        stlMain.setOnTabSelectListener(this);
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias() {
        if (!ExampleUtil.isValidTagAndAlias("yuwenming")) {
            Toast.makeText(this, "yicunzai", Toast.LENGTH_SHORT).show();
            return;
        }

        // 调   用 Handler 来异步设置别名
        // 调   用 Handler 来异步设置别名
        // 调   用 Handler 来异步设置别名
        // 调   用 Handler 来异步设置别名dev001
        //  dev001修改
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "yuwenming 1111111"));
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "yuwenming 1111111    dev"));
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "yuwenming 1111111    dev"));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SharePreUtils.setIsSetAlias(true);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    public static boolean isForeground = false;

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        for (int i = 0; i < stlMain.getTabCount(); i++) {
            if (i == position) {
                TextView titleView = stlMain.getTitleView(i);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            } else {
                TextView titleView = stlMain.getTitleView(i);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        }

    }

    @Override
    public void onTabReselect(int position) {

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//                    if (!ExampleUtil.isEmpty(extras)) {
//                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//                    }
//                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }
}
