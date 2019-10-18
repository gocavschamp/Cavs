package com.nucarf.base.retrofit;

import android.text.TextUtils;


import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> implements Observer<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = false;

    protected CommonSubscriber(BaseView view){
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState){
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState){
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.onNetError(1,mErrorMsg);
        } else if (e instanceof ApiException) {
            mView.onNetError(1,e.toString());
        } else if (e instanceof HttpException) {
            HttpException e1 = (HttpException) e;
            mView.onNetError(1,"数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.onNetError(1,"未知错误ヽ(≧Д≦)ノ");
            LogUtils.d(e.toString());
        }
        if (isShowErrorState) {
//            mView.onNetError();
        }
    }
}
