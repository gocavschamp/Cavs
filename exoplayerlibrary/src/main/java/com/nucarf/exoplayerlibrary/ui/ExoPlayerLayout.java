package com.nucarf.exoplayerlibrary.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.nucarf.exoplayerlibrary.BuildConfig;
import com.nucarf.exoplayerlibrary.R;
import com.nucarf.exoplayerlibrary.util.EventLogger;
import com.nucarf.exoplayerlibrary.util.NetWorkUtil;
import com.nucarf.exoplayerlibrary.util.ScreenUtils;
import com.nucarf.exoplayerlibrary.verplayer.GoodsListener;
import com.tools.ad.ADLayout;
import com.tools.ad.ADListener;
import com.tools.ad.bean.Advertisement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created tt on 16/4/18.
 */
public class ExoPlayerLayout extends FrameLayout implements View.OnTouchListener, OnClickListener, ExoPlayer.EventListener, ADListener, ControllerListener, GoodsListener {

    private final String TAG = " ExoPlayer";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    public static int hideTime = 3000;

    private Handler mainHandler;
    private Timeline.Window window;
    private EventLogger eventLogger;
    private SimpleExoPlayerView simpleExoPlayerView;
    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private boolean playerNeedsSource;
    private boolean isTimelineStatic;
    private int playerWindow;
    private long playerPosition;
    private ControllerLayout mediaController;
    private ImageView player_loading_bg;
    private ProgressBar player_loading;
    private TextView player_error;
    private LinearLayout player_4g_loading;
    private TextView player_4g_loading_go;
    private ADLayout adView;
    private TvGoodsLayout mGoodsView;
    private FrameLayout root;
    private boolean isFirstLoading = true;
    private boolean isError = false;
    private boolean isMinimized = false;
    private String videoInfoUri = null;
    private ExoPlayerListener exoPlayerListener;
    private String mTitleStr;
    private int direction;//// 竖1横0
    //当前是否播放的广告
    private boolean mIsPlayAdVideo;

    private void initializePlayer(boolean shouldAutoPlay) {
        if (player == null) {
            boolean preferExtensionDecoders = false;
            UUID drmSchemeUuid = null;
            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;

//            @SimpleExoPlayer.ExtensionRendererMode int extensionRendererMode =
//                    useExtensionRenderers() ? (preferExtensionDecoders ? SimpleExoPlayer.EXTENSION_RENDERER_MODE_PREFER
//                            : SimpleExoPlayer.EXTENSION_RENDERER_MODE_ON)
//                            : SimpleExoPlayer.EXTENSION_RENDERER_MODE_OFF;
//            TrackSelection.Factory videoTrackSelectionFactory =
//                    new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, new DefaultLoadControl());
            player.addListener(this);
            eventLogger = new EventLogger(trackSelector);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
//            player.setId3Output(eventLogger);
            mediaController.setupListener(this);
            mediaController.setEnabled(true);
            simpleExoPlayerView.setPlayer(player);
            if (isTimelineStatic) {
                if (playerPosition == C.TIME_UNSET) {
                    player.seekToDefaultPosition(playerWindow);
                } else {
                    player.seekTo(playerWindow, Math.max(0, playerPosition));
                }
            }
            player.setPlayWhenReady(shouldAutoPlay);
            playerNeedsSource = true;
        }
        if (playerNeedsSource) {
            Uri[] uris;
            String[] extensions;
//            uris = new Uri[]{Uri.parse(videoInfoUri)};
//            extensions = new String[]{null};
//            MediaSource[] mediaSources = new MediaSource[uris.length];
//            for (int i = 0; i < uris.length; i++) {
//                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
//            }
//            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
//                    : new ConcatenatingMediaSource(mediaSources);
            //测量播放过程中的带宽。 如果不需要，可以为null。
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // 生成加载媒体数据的DataSource实例。
            DataSource.Factory dataSourceFactory
                    = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(),"flyvideo"),bandwidthMeter);
            // 生成用于解析媒体数据的Extractor实例。
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // MediaSource代表要播放的媒体。
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoInfoUri),dataSourceFactory,extractorsFactory,
                    null,null);
            player.prepare(mediaSource, !isTimelineStatic, !isTimelineStatic);
            playerNeedsSource = false;
        }
    }


    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.e(TAG, "playWhenReady = " + playWhenReady + ", playbackState = " + playbackState);
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                mediaController.haveHideAbility(true);
                mediaController.setFinish(false);
                if (isFirstLoading || isError) {
                    player_loading_bg.setVisibility(VISIBLE);
                    mediaController.haveShowAbility(false);
                    mediaController.hide();
                } else {
                    player_loading_bg.setVisibility(GONE);
                    mediaController.haveShowAbility(true);
                }
                player_loading.setVisibility(VISIBLE);
                player_error.setVisibility(INVISIBLE);
                mediaController.gonePauseView();
                break;
            case ExoPlayer.STATE_READY:
                mediaController.haveHideAbility(true);
                mediaController.haveShowAbility(true);
                player_loading_bg.setVisibility(GONE);
                player_loading.setVisibility(INVISIBLE);
                player_error.setVisibility(INVISIBLE);
                mediaController.setFinish(false);
                mediaController.visiblePauseView();
                exoPlayerListener.playerVideoPrepared();
                if (isError) {
                    isError = false;
                    mediaController.show(hideTime);
                }
                if (isFirstLoading) {
                    if (!isMinimized) {
//                        mediaController.show(hideTime);
                        mediaController.hide();
                        adView.setVisibility(View.VISIBLE);
                        if (null != mGoodsList && mGoodsList.size() > 0) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mGoodsView.setVisibility(VISIBLE);
                                    mGoodsView.show();
                                }
                            }, 3000);
                        }
                    }
                    isFirstLoading = false;
                }
                break;
            case ExoPlayer.STATE_ENDED:
                mediaController.haveHideAbility(false);
                mediaController.setFinish(true);
                exoPlayerListener.playerVideoFinish();
                adView.hide();
                if (!isMinimized) {
//                    mediaController.show(hideTime);
                }
                if (isFirstLoading || isError) {
                    player_loading_bg.setVisibility(VISIBLE);
                    mediaController.haveShowAbility(false);
                    mediaController.hide();
                } else {
                    player_loading_bg.setVisibility(GONE);
                    mediaController.haveShowAbility(true);
                }
                player_loading.setVisibility(INVISIBLE);
                player_error.setVisibility(INVISIBLE);
                mediaController.visiblePauseView();
                break;
        }
    }

//    @Override
//    public void onPositionDiscontinuity() {
//    }
//
//    @Override
//    public void onTimelineChanged(Timeline timeline, Object manifest) {
//        isTimelineStatic = !timeline.isEmpty()
//                && !timeline.getWindow(timeline.getWindowCount() - 1, window).isDynamic;
//    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        release();
        mediaController.haveHideAbility(true);
        mediaController.haveShowAbility(false);
        player_loading_bg.setVisibility(VISIBLE);
        player_loading.setVisibility(INVISIBLE);
        player_error.setVisibility(VISIBLE);
        mediaController.gonePauseView();
        mediaController.hide();
        if (!isError) {
            isError = true;
        }
        playerNeedsSource = true;
    }

    private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(getContext(), bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "ExoPlayerLayout"), bandwidthMeter);
    }

    private boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
    }

    private void release() {
        if (player != null && !isError) {
            if (getCurrentPosition() > 0) {
                if ((player.getPlaybackState() == ExoPlayer.STATE_ENDED || getCurrentPosition() == getDuration())) {
                    exoPlayerListener.setViewHistory(-1);
                } else {
                    exoPlayerListener.setViewHistory(getCurrentPosition());
                }
            } else if (videoInfoUri != null) {
                exoPlayerListener.setViewHistory(playerPosition);
            }
            playerWindow = player.getCurrentWindowIndex();
            playerPosition = C.TIME_UNSET;
            Timeline timeline = player.getCurrentTimeline();
            if (!timeline.isEmpty() && timeline.getWindow(playerWindow, window).isSeekable) {
                playerPosition = player.getCurrentPosition();
            }
            player.release();
            player = null;
            trackSelector = null;
            eventLogger = null;
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.exo_player_video_root, this);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.setOnTouchListener(this);
        player_loading_bg = (ImageView) findViewById(R.id.player_loading_bg);
        player_loading = (ProgressBar) findViewById(R.id.player_loading);
        player_error = (TextView) findViewById(R.id.player_error);
        player_4g_loading = (LinearLayout) findViewById(R.id.player_4g_loading);
        player_4g_loading_go = (TextView) findViewById(R.id.player_4g_loading_go);
        adView = (ADLayout) findViewById(R.id.adView);
        mGoodsView = (TvGoodsLayout) findViewById(R.id.goods_View);
        mediaController = new ControllerLayout(getContext());
        root = (FrameLayout) findViewById(R.id.root);
        mediaController.setAnchorView(root);
        mediaController.setOnTouchListener(this);
        root.setOnTouchListener(this);
        player_error.setOnClickListener(this);
        player_4g_loading_go.setOnClickListener(this);
        adView.setADListener(this);
        mGoodsView.setGoodsListener(this);
        mainHandler = new Handler();
        window = new Timeline.Window();
        mediaDataSourceFactory = buildDataSourceFactory(true);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.player_error) {
            mediaController.haveHideAbility(true);
            mediaController.haveShowAbility(false);
            player_loading_bg.setVisibility(VISIBLE);
            player_loading.setVisibility(VISIBLE);
            player_error.setVisibility(INVISIBLE);
            mediaController.gonePauseView();
            mediaController.hide();
            initializePlayer(true);
        }
        if (i == R.id.player_4g_loading_go) {
            player_loading_bg.setVisibility(VISIBLE);
            player_loading.setVisibility(VISIBLE);
            player_error.setVisibility(INVISIBLE);
            player_4g_loading.setVisibility(INVISIBLE);
            initializePlayer(true);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case 0:
                if (!mIsPlayAdVideo) {
                    if (!isError && !isFirstLoading && !isMinimized) {
                        try {
                            if (mediaController.isShowing()) {
                                mediaController.hide();
                                if (null != mGoodsList && mGoodsList.size() > 0) {
                                    mGoodsView.setVisibility(VISIBLE);
                                }
                            } else {
                                mediaController.show(hideTime);
                                mGoodsView.setVisibility(GONE);
                            }
                        } catch (NullPointerException var2) {
                            var2.printStackTrace();
                        }
                    }
                } else {
                    exoPlayerListener.onClickADVideo(videoInfoUri);
                }
                break;
        }

        return true;
    }

    @Override
    public void onClickAD(String url) {
//      mediaController.onClickADVerticalScreen();
        exoPlayerListener.onClickADLayout(url);
    }

    @Override
    public void onCloseAD() {
    }

    @Override
    public void showADLayout() {
        if (!isMinimized) {
            adView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideADLayout() {
        adView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void start() {
        if(player!=null){
            player.setPlayWhenReady(true);
        }

    }

    @Override
    public void pause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void restart() {
        player.setPlayWhenReady(true);
        player.seekTo(0);
    }


    @Override
    public long getDuration() {

        return player != null ? player.getDuration() == -1L ? 0 : player.getDuration() : 0;
    }

    @Override
    public long getCurrentPosition() {
        if (null != exoPlayerListener && null != player) {
            exoPlayerListener.playerVideoDuration(player.getCurrentPosition());
        }
        return player != null ? (player.getDuration() == -1L ? 0 : player.getCurrentPosition()) : 0;
    }

    private String mTitle;
    private String mWatchNum;


    public void setmWatchNum(String mWatchNum) {
        this.mWatchNum = mWatchNum;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    @Override
    public String getTtitle() {
        return mTitle;
    }

    @Override
    public String getWatchNum() {
        return mWatchNum;
    }

    @Override
    public void seekTo(long var1) {
        player.seekTo((player.getDuration() == -1L ? 0 : Math.min(Math.max(0, var1), getDuration())));
    }

    @Override
    public boolean isPlaying() {
        return player != null ? player.getPlayWhenReady() : false;
    }

    @Override
    public int getBufferPercentage() {
        if(null!=player) {
            return player.getBufferedPercentage();
        }else{
            return 0;
        }
    }

    @Override
    public void setCurrentPlayTime(String currentTime) {
        adView.setCurrentPlayTime(currentTime);
    }

    @Override
    public void doHorizontalScreen() {
        exoPlayerListener.doHorizontalScreen();
        if (direction == 1) { //视频为竖屏
            mediaController.updateDirection(true);

        } else {//视频为横屏
            simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            if (null != mGoodsList && mGoodsList.size() > 0) {
                int mWidth = ScreenUtils.dip2px(getContext(), 110) + (ScreenUtils.dip2px(getContext(), 85) * mGoodsList.size());
                if (mWidth > ScreenUtils.getScreenWidth(getContext())) {
                    mWidth = ScreenUtils.dip2px(getContext(), 70) + ScreenUtils.getScreenWidth(getContext());
                }
                ScreenUtils.setFrameLayoutParams(mGoodsView, mWidth, ScreenUtils.dip2px(getContext(), 119));
            }
            mGoodsView.setRotaionScreen(false);

        }

    }

    @Override
    public void doVerticalScreen() {
        exoPlayerListener.doVerticalScreen();
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        if (null != mGoodsList && mGoodsList.size() > 0) {
            int mWidth = ScreenUtils.dip2px(getContext(), 110) + (ScreenUtils.dip2px(getContext(), 85) * mGoodsList.size());
            if (mWidth > ScreenUtils.getScreenWidth(getContext())) {
                mWidth = ScreenUtils.dip2px(getContext(), 70) + ScreenUtils.getScreenWidth(getContext());
            }
            ScreenUtils.setFrameLayoutParams(mGoodsView, mWidth, ScreenUtils.dip2px(getContext(), 119));
        }
        mGoodsView.setRotaionScreen(true);
        mediaController.updateDirection(false);
    }

    @Override
    public void setIsDisableDraggableView(boolean b) {
        exoPlayerListener.setIsDisableDraggableView(b);
    }

    public ExoPlayerLayout(Context context) {
        super(context);
        init();
    }

    public ExoPlayerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExoPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExoPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setExoPlayerListener(ExoPlayerListener listener) {
        exoPlayerListener = listener;
    }

    private ArrayList<TaobaoGoods> mGoodsList;

    public void play(String url, ArrayList<Advertisement> advertisements, ArrayList<TaobaoGoods> pGoodsList, boolean isLive, long time, boolean isNeedWifi) {
        Log.e(TAG, "playerUrl = " + url + ", time = " + time);
        playerPosition = (time == -1) ? 0 : time;
        isTimelineStatic = true;
        isFirstLoading = true;
        isError = false;
        isMinimized = false;
        videoInfoUri = url;
        adView.setAdvertisements(advertisements);
        mGoodsList = pGoodsList;
        if (null != pGoodsList && pGoodsList.size() > 0) {
            int mWidth = ScreenUtils.dip2px(getContext(), 110) + (ScreenUtils.dip2px(getContext(), 85) * pGoodsList.size());
            if (mWidth > ScreenUtils.getScreenWidth(getContext())) {
                mWidth = ScreenUtils.dip2px(getContext(), 70) + ScreenUtils.getScreenWidth(getContext());
            }
            ScreenUtils.setFrameLayoutParams(mGoodsView, mWidth, ScreenUtils.dip2px(getContext(), 119));
            mGoodsView.setmGoodsList(pGoodsList);
        } else {
            mGoodsView.setVisibility(GONE);
        }

        if (isLive)
            adView.setIsLive();
        if (isNeedWifi && NetWorkUtil.getNetworkType(getContext()) != NetWorkUtil.NET_WIFI) {
            player_loading_bg.setVisibility(VISIBLE);
            player_4g_loading.setVisibility(VISIBLE);
            player_loading.setVisibility(INVISIBLE);
            player_error.setVisibility(INVISIBLE);
            Toast.makeText(getContext(), "您当前处在非wifi网络下", Toast.LENGTH_LONG).show();
        } else {
            initializePlayer(true);
        }
    }

    public void setAdData(ArrayList<Advertisement> advertisements, boolean isLive) {
        adView.setAdvertisements(advertisements);
        if (isLive)
            adView.setIsLive();
    }

    //窗口最小化
    public void onMinimized() {
        isMinimized = true;
        if (player != null) {
            mediaController.haveShowAbility(false);
            mediaController.haveHideAbility(true);
            mediaController.hide();
            mGoodsView.setVisibility(GONE);
            hideADLayout();
        }
    }

    //窗口最大化
    public void onMaximized() {
        if (player != null) {
            if (isError) {
                mediaController.haveShowAbility(false);
                mediaController.haveHideAbility(true);
            } else {
                if (!isFirstLoading) {
                    if (player.getPlaybackState() == Player.STATE_BUFFERING) {
                        mediaController.haveShowAbility(true);
                        mediaController.haveHideAbility(false);
                    } else {
                        mediaController.haveShowAbility(true);
                        mediaController.haveHideAbility(true);
                    }
//                    mediaController.show(hideTime);
                } else {
                    mediaController.haveShowAbility(false);
                    mediaController.haveHideAbility(true);
                }
            }
        }
        isMinimized = false;
    }

    public void showFirstLoading(boolean isFirstLadingVideo) {
        if (isFirstLadingVideo) {
            player_loading_bg.setVisibility(VISIBLE);
            player_loading.setVisibility(VISIBLE);
            player_error.setVisibility(INVISIBLE);
            player_4g_loading.setVisibility(INVISIBLE);
        }
    }

    public void closeFirstLoading(boolean isFirstLadingVideo) {
        if (isFirstLadingVideo) {
            player_loading_bg.setVisibility(VISIBLE);
            player_loading.setVisibility(INVISIBLE);
            player_error.setVisibility(INVISIBLE);
            player_4g_loading.setVisibility(INVISIBLE);
        }
    }

    public void releasePlayer() {
        isFirstLoading = true;
        isError = false;
        isMinimized = false;
        release();
        videoInfoUri = null;
        playerNeedsSource = false;
        isTimelineStatic = false;
        playerWindow = 0;
        playerPosition = 0;
        hideADLayout();
        adView.releaseData();
        if (mediaController != null) {
            mediaController.releaseController();
        }
        player_loading_bg.setVisibility(INVISIBLE);
        player_loading.setVisibility(INVISIBLE);
        player_error.setVisibility(INVISIBLE);
        player_4g_loading.setVisibility(INVISIBLE);
    }

    public void finishPlayer() {
        releasePlayer();
        adView.finishADLayout();
        if (mediaController != null)
            mediaController.finishController();
        exoPlayerListener = null;
        mainHandler = null;
        window = null;
        mediaDataSourceFactory = null;
        System.gc();
    }

    public void updateDirection(boolean isHorizontal) {
        mediaController.updateDirection(isHorizontal);
    }

    public void setFullscreenButton(boolean isShow) {
        mediaController.setFullscreenButton(isShow);
    }

    public void setHideTime(int time) {
        hideTime = time;
    }

    public void replayVideo() {
        mediaController.replayVideo();
    }

    public void onResume() {
        if (videoInfoUri != null) {
            if ((Util.SDK_INT <= 23 || player == null)) {
                initializePlayer(false);
//                mediaController.haveHideAbility(true);
//                mediaController.show(hideTime);
            }
        }
    }

    public void onPause() {
        pause();
        if (videoInfoUri != null) {
            if (Util.SDK_INT <= 23) {
                mediaController.haveHideAbility(false);
                release();
            }
        }
    }

    public void onStop() {
        if (videoInfoUri != null) {
            if (Util.SDK_INT > 23) {
                mediaController.haveHideAbility(false);
                release();
            }
        }
    }

    @Override
    public void onExpandGoodsView() {


    }

    @Override
    public void onCloseGoodsView() {

    }

    @Override
    public void onClickGoodsItem(String pGoodId) {
        exoPlayerListener.onClickGoodsItem(pGoodId);
    }

    // 竖1横0
    public void setDiretion(int direction) {
        this.direction = direction;
    }

    public void setmIsPlayAdVideo(boolean mIsPlayAdVideo) {
        this.mIsPlayAdVideo = mIsPlayAdVideo;
    }



    public String getVideoInfoUri() {
        return videoInfoUri;
    }
}