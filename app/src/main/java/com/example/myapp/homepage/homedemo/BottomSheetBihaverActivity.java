package com.example.myapp.homepage.homedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.example.myapp.dragger.BaseMvpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetBihaverActivity extends BaseMvpActivity<BottomSheetPresenter>implements BottomSheetCotract.View, BaseQuickAdapter.RequestLoadMoreListener {

    //    @BindView(R.id.map)
//    MapView map;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt)
    Button btn;
    private List<String> data;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetAdapter bottomSheetAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_bihaver);
//        map.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
//        map.onDestroy();
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.recycleview));
        bottomSheetBehavior.setBottomSheetCallback(new MyBottomSheetCallback());
        recycleview.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

        bottomSheetAdapter = new BottomSheetAdapter(R.layout.default_normal_layout);
        recycleview.setAdapter(bottomSheetAdapter);
        bottomSheetAdapter.setEnableLoadMore(true);
        bottomSheetAdapter.setOnLoadMoreListener(this,recycleview);
        getData();
    }

    private void getData() {
        if(mPresenter!=null) {
            mPresenter.loadData(true);
        }
    }

    @OnClick(R.id.bt)
    public void onViewClicked() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    @Override
    public void onSucess() {

    }

    @Override
    public void onReLoad() {

    }

    @Override
    protected void initInject() {
//        getActivityComponent().inject(this);
        mPresenter = new BottomSheetPresenter(this);
    }

    @Override
    public void setData(boolean isRefresh, List data,boolean isEnd) {
        if(isRefresh) {
            bottomSheetAdapter.setNewData(data);
        }else {
            bottomSheetAdapter.addData(data);
            bottomSheetAdapter.loadMoreComplete();
        }
        if(isEnd) {
            bottomSheetAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadData(false);
    }


    class MyBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View view, int i) {

        }

        @Override
        public void onSlide(@NonNull View view, float v) {

        }
    }
}
