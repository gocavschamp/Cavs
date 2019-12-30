package com.example.myapp.homepage.homedemo.videolist;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapp.R;
import com.example.myapp.mvp.BaseMvpActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.widget.TitleLayout;
import com.nucarf.exoplayerlibrary.ui.ExoPlayerLayout;
import com.nucarf.exoplayerlibrary.ui.ExoPlayerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListActivity extends BaseMvpActivity<VideoListPresenter> implements VideoListContract.View, ExoPlayerListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.titlelayout)
    TitleLayout titlelayout;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.fl_content)
    RelativeLayout flContent;
    private LinearLayoutManager layoutManager;
    private VideoListAdapter videoListAdapter;
    private long mPlayTime = 0;
    private ExoPlayerLayout mExoPlayer;

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
        titlelayout.setLeftClickListener(view -> {
            finish();
        });
        layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recycleview.setLayoutManager(layoutManager);
        videoListAdapter = new VideoListAdapter(R.layout.item_video_list);
        recycleview.setAdapter(videoListAdapter);
        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mExoPlayer != null) {
                    flContent.setVisibility(View.VISIBLE);
                    mExoPlayer.play(videoListAdapter.getData().get(position).getPlay_url(), null, null, false, mPlayTime, true);
                }
            }
        });
        videoListAdapter.setOnLoadMoreListener(this,recycleview);
        View mVideoView = View.inflate(mContext, R.layout.video_layout, null);
        flContent.addView(mVideoView);
        if (mExoPlayer != null) {
            mExoPlayer.onPause();
            mExoPlayer.releasePlayer();
            mExoPlayer = null;
        }
        mExoPlayer = (ExoPlayerLayout) mVideoView.findViewById(R.id.exoPlayer);
        View mConverTrans = mVideoView.findViewById(R.id.conver_trans);
        mConverTrans.setOnClickListener(this);
        mConverTrans.setVisibility(View.GONE);

        mExoPlayer.setExoPlayerListener(this);
        mExoPlayer.setOnClickListener(this);
        mExoPlayer.releasePlayer();
        mExoPlayer.showFirstLoading(true);
        flContent.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (flContent.getVisibility() == View.VISIBLE) {
            releasPlayer();
            flContent.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        releasPlayer();
        mExoPlayer = null;
        super.onDestroy();
    }

    private void releasPlayer() {
        if (null != mExoPlayer) {
            mExoPlayer.onStop();
            mExoPlayer.releasePlayer();
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onReLoad() {

    }

    @Override
    public void setData(boolean isRefresh, List<VideoListData.ResponseBean.VideosBean> data, boolean isEnd) {
        videoListAdapter.loadMoreComplete();
        videoListAdapter.addData(data);
    }

    @Override
    public void doHorizontalScreen() {

    }

    @Override
    public void doVerticalScreen() {

    }

    @Override
    public void setIsDisableDraggableView(boolean b) {

    }

    @Override
    public void playerVideoPrepared() {

    }

    @Override
    public void playerVideoFinish() {

    }

    @Override
    public void playerVideoDuration(long pDuration) {

    }

    @Override
    public void onClickADLayout(String url) {

    }

    @Override
    public void onClickADVideo(String pADVideoUrl) {

    }

    @Override
    public void onClickGoodsItem(String pGoodsId) {

    }

    @Override
    public void setViewHistory(long longTime) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadMoreRequested() {
        if (null != mPresenter) {
            mPresenter.loadData(false);
        }
    }
}
