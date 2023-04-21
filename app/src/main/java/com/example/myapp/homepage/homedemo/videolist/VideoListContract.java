package com.example.myapp.homepage.homedemo.videolist;

import com.example.myapp.bean.ArticleListBean;
import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;

import java.util.List;

/**
 * Created by yuwenming on 2019/12/27.
 */
public interface VideoListContract {
    interface View extends BaseView {
        void setData(boolean isRefresh, List<VideoListData.ResponseBean.VideosBean> data, boolean isEnd);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(boolean isRefresh);
    }
}
