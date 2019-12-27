package com.example.myapp.homepage.homedemo.videolist;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.mvp.BaseMvpActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.widget.TitleLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListActivity extends BaseMvpActivity<VideoListPresenter> implements VideoListContract.View{

    @BindView(R.id.titlelayout)
    TitleLayout titlelayout;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private LinearLayoutManager layoutManager;
    private VideoListAdapter videoListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(titlelayout).init();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        initView();

        if (null != mPresenter) {
            mPresenter.loadData(true);
        }
    }

    private void initView() {
        titlelayout.setLeftClickListener(view ->{
            finish();
        });
        layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recycleview.setLayoutManager(layoutManager);
        videoListAdapter = new VideoListAdapter(R.layout.item_video_list);
        recycleview.setAdapter(videoListAdapter);
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onReLoad() {

    }

    @Override
    public void setData(boolean isRefresh, List<VideoListData.ResponseBean.VideosBean> data, boolean isEnd) {
        videoListAdapter.setNewData(data);
    }
}
