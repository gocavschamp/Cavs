package com.example.myapp.homepage.homedemo.videolist;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.myapp.api.AppService;
import com.example.myapp.bean.ArticleBean;
import com.nucarf.base.mvp.BasePAV;
import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.retrofit.CommonSubscriber;
import com.nucarf.base.retrofit.RetrofitUtils;
import com.nucarf.base.retrofit.RxSchedulers;
import com.nucarf.base.utils.AndroidUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by yuwenming on 2019/12/27.
 */
public class VideoListPresenter extends BasePAV<VideoListContract.View> implements VideoListContract.Presenter {
    @Inject
    public VideoListPresenter() {
    }
    @Override
    public void loadData(boolean isRefresh) {
        String url = "https://haokan.baidu.com/videoui/api/videorec?tab=yingshi&act=pcFeed&pd=pc&num=5&shuaxin_id=1577413362081";
        RetrofitUtils.INSTANCE.getRxjavaClient(AppService.class)
                .getVideoList(url)
                .compose(RxSchedulers.io_main())
                .compose(RxSchedulers.handleResult())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new CommonSubscriber<VideoListData>(mView,true) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoListData articleBean) {
                        mView.closeLoading();

                        mView.setData(isRefresh,articleBean.getResponse().getVideos(),false);

                    }
                });

    }

}
