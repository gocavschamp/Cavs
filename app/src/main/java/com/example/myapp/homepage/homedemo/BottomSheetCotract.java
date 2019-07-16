package com.example.myapp.homepage.homedemo;

import com.nucarf.base.mvp.BasePresenter;
import com.nucarf.base.mvp.BaseView;

import java.util.List;

public interface BottomSheetCotract {
    interface View extends BaseView {
        void setData(boolean isRefresh, List data,boolean isEnd);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(boolean isRefresh);

    }
}
