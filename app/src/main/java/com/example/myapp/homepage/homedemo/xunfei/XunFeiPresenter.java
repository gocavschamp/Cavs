package com.example.myapp.homepage.homedemo.xunfei;

import com.nucarf.base.mvp.BasePAV;

import javax.inject.Inject;

public class XunFeiPresenter extends BasePAV<XunFeiCotract.View> implements XunFeiCotract.Presenter {
    @Inject
    public XunFeiPresenter() {
    }

    @Override
    public void loadData(boolean isRefresh) {

    }
}
