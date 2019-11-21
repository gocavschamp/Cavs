package com.example.myapp.homepage.homedemo.bottomsheet;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.myapp.api.AppService;
import com.example.myapp.bean.ArticleBean;
import com.example.myapp.bean.ArticleListBean;
import com.nucarf.base.mvp.BasePAV;
import com.nucarf.base.retrofit.CommonSubscriber;
import com.nucarf.base.retrofit.RetrofitUtils;
import com.nucarf.base.retrofit.RxSchedulers;
import com.nucarf.base.retrofit.logiclayer.BaseResult;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.SharePreUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BottomSheetPresenter extends BasePAV<BottomSheetCotract.View> implements BottomSheetCotract.Presenter {
    private ArrayList<ArticleListBean> data;
    private boolean isEnd;
    private int mPage = 0;

    @Inject
    public BottomSheetPresenter() {
//        mView = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadData(boolean isRefresh) {
        if (data == null) {
            data = new ArrayList();
        }
        mView.showLoading();
        if (isRefresh) {
            data.clear();
            mPage = 0;
        }
        RetrofitUtils.INSTANCE.getRxjavaClient(AppService.class)
                .getArticleList(mPage)
                .compose(RxSchedulers.io_main())
                .compose(RxSchedulers.handleResult())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribeWith(new CommonSubscriber<ArticleBean>(mView,true) {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.closeLoading();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        mPage++;
                        int total = articleBean.getTotal();
                        isEnd = mView.getDataSize() >= total;
                        mView.setData(isRefresh, articleBean.getDatas(), isEnd);
                        mView.closeLoading();
                    }
                })
//                .subscribe(new Consumer<ArticleBean>() {
//                    @Override
//                    public void accept(ArticleBean articleBean) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.d(RxSchedulers.TAG, "apply: " +throwable);
//                        mView.closeLoading();
//                    }
//                })
        ;

    }

}
