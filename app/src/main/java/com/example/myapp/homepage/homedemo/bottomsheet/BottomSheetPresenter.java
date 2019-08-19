package com.example.myapp.homepage.homedemo.bottomsheet;


import com.example.myapp.api.AppService;
import com.example.myapp.bean.ArticleListBean;
import com.nucarf.base.mvp.BasePAV;
import com.nucarf.base.retrofit.RetrofitUtils;
import com.nucarf.base.retrofit.logiclayer.BaseResult;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.SharePreUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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

    @Override
    public void loadData(boolean isRefresh) {
        if (data == null) {
            data = new ArrayList();
        }
        if (isRefresh) {
            mView.showLoading();
            data.clear();
        } else {
            RetrofitUtils.INSTANCE.getRxjavaClient(AppService.class)
                    .getArticleList(mPage)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResult<ArrayList<ArticleListBean>>>() {
                        @Override
                        public void accept(BaseResult<ArrayList<ArticleListBean>> articleListBeanBaseResult) throws Exception {
                            mView.setData(isRefresh, articleListBeanBaseResult.getResult(), false);
                        }
                    });

        }

//        RetrofitUtils.INSTANCE.getClient(AppService.class).appRegister().enqueue();
        SharePreUtils.getUserName(BaseAppCache.getContext());
        for (int i = 0; i < 9; i++) {
//            data.add("data-------" + i);
        }
        if (data.size() > 17) {
            isEnd = true;

        } else {
            isEnd = false;

        }
//        mView.setData(isRefresh, data, isEnd);
        mView.closeLoading();

    }


}
