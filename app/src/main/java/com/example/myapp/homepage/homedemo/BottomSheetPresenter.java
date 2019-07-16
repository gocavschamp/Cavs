package com.example.myapp.homepage.homedemo;


import com.example.myapp.api.AppService;
import com.nucarf.base.mvp.BasePAV;
import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.retrofit.RetrofitUtils;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.SharePreUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class BottomSheetPresenter  extends BasePAV<BottomSheetCotract.View> implements BottomSheetCotract.Presenter {
    private ArrayList data;
    private boolean isEnd;

    //    @Inject
    public BottomSheetPresenter(BottomSheetCotract.View view){
        mView = view;
    }
    @Override
    public void loadData(boolean isRefresh) {
        if(data==null) {
            data = new ArrayList();
        }
        if(isRefresh) {
            mView.showLoading();
            data.clear();
        }else {

        }

//        RetrofitUtils.INSTANCE.getClient(AppService.class).appRegister().enqueue();
        SharePreUtils.getUserName(BaseAppCache.getContext());
        for (int i = 0; i < 9; i++) {
            data.add("data-------" + i);
        }
        if(data.size()>17) {
            isEnd = true;

        }else {
            isEnd = false;

        }
        mView.setData(isRefresh,data,isEnd);
        mView.closeLoading();

    }


}
