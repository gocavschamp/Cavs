package com.example.myapp.dragger;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.myapp.MyApplication;
import com.example.myapp.SampleApplicationLike;
import com.example.myapp.dragger.component.ActivityComponent;
import com.example.myapp.dragger.component.DaggerActivityComponent;
import com.example.myapp.dragger.module.ActivityModule;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.R;
import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.utils.DialogUtils;
import com.nucarf.base.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseMvpActivity<T extends BasePresenter> extends AutoLayoutActivity implements BaseView {

    protected Context mContext;
    private Unbinder unbinder;


    @Inject
    @Nullable
    public T mPresenter;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ImmersionBar.with(this).statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .flymeOSStatusBarFontColor(R.color.black)  //修改 flyme OS 状态栏字体颜色
                .keyboardEnable(true).init();
        registerEventBus();
//        PushAgent.getInstance(this).onAppStart();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(SampleApplicationLike.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
    }

    @Override
    public void showLoading() {
        if (alertDialog == null) {
            alertDialog = DialogUtils.dialogPro(mContext, "请稍后...", false);
        } else {
            alertDialog.show();
        }
    }

    @Override
    public void closeLoading() {
        if(alertDialog!=null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onFail() {
        ToastUtils.showShort("获取数据失败");
    }

    @Override
    public void onNetError() {
        ToastUtils.showShort("请检查网络是否连接");
    }

    protected abstract void initInject();

    protected abstract void initData();

    /**
     * 注册EventBus通信组件
     */
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 取消注册EventBus通信组件
     */
    protected void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 退出应用
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(String exitApp) {
//        if (exitApp.isExit()) {
//            finish();
//        }
    }


    protected String getName() {
        return getClass().getSimpleName();
    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        EventBus.getDefault().post("andServer-start");
//    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        unRegisterEventBus();
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
//        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}