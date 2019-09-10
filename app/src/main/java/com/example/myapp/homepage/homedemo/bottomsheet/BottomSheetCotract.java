package com.example.myapp.homepage.homedemo.bottomsheet;

import com.example.myapp.bean.ArticleListBean;
import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;

import java.util.List;

public interface BottomSheetCotract {
    interface View extends BaseView {
        void setData(boolean isRefresh, List<ArticleListBean> data, boolean isEnd);
        int getDataSize();
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(boolean isRefresh);

    }
}
