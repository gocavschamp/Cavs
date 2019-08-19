package com.example.myapp.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapp.SampleApplicationLike;
import com.example.myapp.dragger.component.DaggerFragmentComponent;
import com.example.myapp.dragger.component.FragmentComponent;
import com.example.myapp.dragger.module.FragmentModule;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.NetUtils;
import com.nucarf.base.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

/**
 * Created by hzy on 2019/1/17
 * <p>
 * MVP BaseMvpFragment
 *
 * @author Administrator
 *
 * */
public abstract class BaseMvpFragment<T extends BasePresenter> extends Fragment implements BaseView {
    @Inject
    @Nullable
    protected T mPresenter;

    protected Unbinder unbinder;
    protected View mRootView, mErrorView, mEmptyView;
    protected KProgressHUD mKProgressHUD;

    protected abstract int getLayoutId();

    protected abstract void initInject();

    protected abstract void initEventAndData();

    @Override
    public void onResume() {
        super.onResume();
        if (!NetUtils.isNetworkAvailable(BaseAppCache.getContext())) {
            onNetError();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        initInject();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        unbinder = ButterKnife.bind(this, view);
        initEventAndData();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(SampleApplicationLike.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(getActivity());
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void closeLoading() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {
        ToastUtils.showShort("获取数据失败");
    }

    @Override
    public void onNetError() {
        ToastUtils.showShort("请检查网络是否连接");
    }

    @Override
    public void onReLoad() {

    }
}
