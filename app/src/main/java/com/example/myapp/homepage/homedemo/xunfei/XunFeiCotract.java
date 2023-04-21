package com.example.myapp.homepage.homedemo.xunfei;

import com.example.myapp.bean.ArticleListBean;
import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;

import java.util.List;

public interface XunFeiCotract {
    interface View extends BaseView {
        void setData(boolean isRefresh, List<ArticleListBean> data, boolean isEnd);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(boolean isRefresh);

    }
}
