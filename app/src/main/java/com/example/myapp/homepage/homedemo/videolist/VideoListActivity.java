package com.example.myapp.homepage.homedemo.videolist;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.example.myapp.mvp.BaseMvpActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.widget.TitleLayout;
import com.nucarf.exoplayerlibrary.ui.ExoPlayerLayout;
import com.nucarf.exoplayerlibrary.ui.ExoPlayerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.M)
public class VideoListActivity extends BaseMvpActivity<VideoListPresenter> implements VideoListContract.View, ExoPlayerListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.titlelayout)
    TitleLayout titlelayout;
    @BindView(R.id.recycleview)
    RecyclerView videoRecycleview;
    @BindView(R.id.fl_content)
    RelativeLayout flContent;
    private LinearLayoutManager layoutManager;
    private VideoListAdapter videoListAdapter;
    private long mPlayTime = 0;
    private ExoPlayerLayout mExoPlayer;
    private View mVideoView;
    private int mCurrentPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(titlelayout).statusBarColor(R.color.white).init();
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
        videoRecycleview.setLayoutManager(layoutManager);
        videoListAdapter = new VideoListAdapter(R.layout.item_video_list);
        videoRecycleview.setAdapter(videoListAdapter);
        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startPlay(position, mVideoView);
//                if (mExoPlayer != null) {
//                    flContent.setVisibility(View.VISIBLE);
//                    String url = videoListAdapter.getData().get(position).getPlay_url();
//                    mExoPlayer.play(url, null, null, false, mPlayTime, true);
//                }
            }
        });
        videoListAdapter.setOnLoadMoreListener(this, videoRecycleview);
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
        flContent.setVisibility(View.GONE);
        videoRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int visibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                    startPlay(visibleItemPosition, mVideoView);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void startPlay(int visibleItemPosition, View mVideoView) {
        if (mCurrentPosition != visibleItemPosition) {
            stopPlay();
        }
        if (visibleItemPosition != -1 && !mExoPlayer.isPlaying()) {
            BaseViewHolder viewHolder = (BaseViewHolder) videoRecycleview.findViewHolderForLayoutPosition(visibleItemPosition);
            ViewParent parent = mVideoView.getParent();
            if (parent instanceof FrameLayout) {
                ((ViewGroup) parent).removeView(mVideoView);
            }
            if (viewHolder != null) {
                FrameLayout mVideoContent = viewHolder.getView(R.id.fl_content_item);
                mVideoContent.addView(mVideoView, 0);
                if (mExoPlayer != null) {
                    String url = videoListAdapter.getData().get(visibleItemPosition).getPlay_url();
                    mExoPlayer.play(url, null, null, false, mPlayTime, true);
                    mCurrentPosition = visibleItemPosition;
                }
            }
        }
    }

    private void stopPlay() {
        releasPlayer();
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mVideoView);
        }
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
        videoListAdapter.loadMoreComplete();
        videoListAdapter.addData(data);
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
            flContent.setVisibility(View.GONE);
            flContent.removeAllViews();
            BaseViewHolder viewHolder = (BaseViewHolder) videoRecycleview.findViewHolderForLayoutPosition(mCurrentPosition);
            ViewParent parent = mVideoView.getParent();
            if (parent instanceof FrameLayout) {
                ((ViewGroup) parent).removeView(mVideoView);
            }
            if (viewHolder != null) {
                FrameLayout mVideoContent = viewHolder.getView(R.id.fl_content_item);
                mVideoContent.addView(mVideoView, 0);
                mExoPlayer.start();
            }
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
