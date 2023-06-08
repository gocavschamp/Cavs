package com.example.myapp.homepage.homedemo.videolist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.loadingbox.LoadingBox;
import com.moonlight.flyvideo.R;
import com.example.myapp.mvp.BaseMvpActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.utils.AssetUtil;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.widget.TitleLayout;
import com.nucarf.exoplayerlibrary.ui.ExoPlayerLayout;
import com.nucarf.exoplayerlibrary.ui.ExoPlayerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailsActivity extends BaseMvpActivity<VideoListPresenter> implements VideoListContract.View, ExoPlayerListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.titlelayout)
    TitleLayout titlelayout;
    @BindView(R.id.fl_content)
    RelativeLayout flContent;
    private long mPlayTime = 0;
    private ExoPlayerLayout mExoPlayer;
    private View mVideoView;
    private int mCurrentPosition = -1;
    private LoadingBox loadingBox;
    private String videoUrl = "";

    public static void launcher(Context context, String videoUrl) {
        Intent intent = new Intent(context, VideoDetailsActivity.class);
        intent.putExtra("videoUrl", videoUrl);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(titlelayout).statusBarColor(R.color.white).init();
    }

    VideoListData videoData = null;

    @Override
    protected void initInject() {
//        getActivityComponent().inject(this);
        mPresenter = new VideoListPresenter();
        videoUrl = getIntent().getStringExtra("videoUrl");

        try {
            String json_str = AssetUtil.getText(BaseAppCache.getContext(), "video.text");
            videoData = new Gson().fromJson(json_str, new TypeToken<VideoListData>() {
            }.getType());
        } catch (Exception e) {
            LogUtils.e("$name--", e.getMessage());
        }
    }

    @Override
    protected void initData() {

        initView();
    }

    private void initView() {
        titlelayout.setLeftClickListener(view -> finish());
        loadingBox = new LoadingBox(this, flContent);
        loadingBox.setExceptionBackgroundColor(Color.GRAY);
        loadingBox.setClickListener(v -> mPresenter.loadData(true));
        mVideoView = View.inflate(mContext, R.layout.video_layout, null);
//        flContent.addView(mVideoView);
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
        flContent.setVisibility(View.VISIBLE);
        startPlay(0, mVideoView);
    }

    @Override
    public void onNetError(int errorCode, String errorMsg) {

        loadingBox.showInternetErrorLayout();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void startPlay(int visibleItemPosition, View mVideoView) {
        stopPlay();
        if (mExoPlayer != null) {
            mExoPlayer.play(videoUrl, null, null, false, mPlayTime, true);
            mCurrentPosition = visibleItemPosition;
        }
    }

    private void stopPlay() {
        releasPlayer();
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            doVerticalScreen();
            return;
        }
        stopPlay();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
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
    }

    @Override
    public void doHorizontalScreen() {
        LogUtils.e("video--doHorizontalScreen");
        //横屏全屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (mExoPlayer != null) {
            mExoPlayer.pause();
            ViewParent parent = mVideoView.getParent();
            if (parent instanceof FrameLayout) {
                ((FrameLayout) parent).removeView(mVideoView);
            }
            flContent.setVisibility(View.VISIBLE);
            flContent.addView(mVideoView);
            mExoPlayer.start();
        }
    }

    @Override
    public void doVerticalScreen() {
        LogUtils.e("video--doVerticalScreen");
        //竖屏全屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mExoPlayer != null) {
            mExoPlayer.pause();
            ViewParent parent = mVideoView.getParent();
            if (parent instanceof FrameLayout) {
                ((FrameLayout) parent).removeView(mVideoView);
            }
            flContent.setVisibility(View.VISIBLE);
            flContent.addView(mVideoView);
            mExoPlayer.start();
        }
    }

    @Override
    public void setIsDisableDraggableView(boolean b) {
        LogUtils.e("video--setIsDisableDraggableView");

    }

    @Override
    public void playerVideoPrepared() {
        LogUtils.e("video--playerVideoPrepared");

    }

    @Override
    public void playerVideoFinish() {
        LogUtils.e("video--playerVideoFinish");
        stopPlay();
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
        LogUtils.e("video--setViewHistory");

    }

    @Override
    public void onClick(View v) {
        LogUtils.e("video--onClick" + v.getId());

    }

    @Override
    public void onLoadMoreRequested() {
        if (null != mPresenter) {
            mPresenter.loadData(false);
        }
    }

}
