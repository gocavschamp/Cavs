package com.nucarf.exoplayerlibrary.verplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.MediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.nucarf.exoplayerlibrary.R;
import com.nucarf.exoplayerlibrary.util.EventLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * create time on on 16/4/18.
 */
public class VerPlayerLayout extends FrameLayout implements OnClickListener, ExoPlayer.EventListener, ControllerListener {


    private final String TAG = " VerPlayer";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

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
    private FrameLayout root;
    private boolean isFirstLoading = true;
    private boolean isError = false;
    private String videoInfoUrl = null;
    private VerPlayerListener verPlayerListener;
    private boolean isFinish = false;

    private void initializePlayer(boolean shouldAutoPlay) {
        if (player == null) {
            //1. 创建一个默认的 TrackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            //2.创建ExoPlayer
            player = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            //3.创建SimpleExoPlayerView
            //4.为SimpleExoPlayer设置播放器
            player.addListener(this);
            eventLogger = new EventLogger(trackSelector);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
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
//            Uri[] uris;
//            String[] extensions;
//            uris = new Uri[]{Uri.parse(videoInfoUrl)};
//            extensions = new String[]{null};
//            MediaSource[] mediaSources = new MediaSource[uris.length];
//            for (int i = 0; i < uris.length; i++) {
//                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
//            }
            //测量播放过程中的带宽。 如果不需要，可以为null。
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // 生成加载媒体数据的DataSource实例。
            DataSource.Factory dataSourceFactory
                    = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(),"flyvideo"),bandwidthMeter);
            // 生成用于解析媒体数据的Extractor实例。
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // MediaSource代表要播放的媒体。
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoInfoUrl),dataSourceFactory,extractorsFactory,
                    null,null);
//            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
//                    : new ConcatenatingMediaSource(mediaSources);
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
                mediaController.setFinish(false);
                if (isFirstLoading || isError) {
                    player_loading_bg.setVisibility(VISIBLE);
                    mediaController.hide();
                } else {
                    player_loading_bg.setVisibility(GONE);
                }
                player_loading.setVisibility(VISIBLE);
                player_error.setVisibility(INVISIBLE);
                break;
            case ExoPlayer.STATE_READY:
                mediaController.setFinish(false);
                player_loading_bg.setVisibility(GONE);
                player_loading.setVisibility(INVISIBLE);
                player_error.setVisibility(INVISIBLE);
                if (isError) {
                    isError = false;
                    mediaController.show();
                }
                if (isFirstLoading) {
                    mediaController.show();
                    isFirstLoading = false;
                }
                break;
            case ExoPlayer.STATE_ENDED:
                mediaController.setFinish(true);
                verPlayerListener.playerVideoFinish(isFinish);
                if (isFirstLoading || isError) {
                    player_loading_bg.setVisibility(VISIBLE);
                    mediaController.hide();
                } else {
                    player_loading_bg.setVisibility(GONE);
                }
                player_loading.setVisibility(INVISIBLE);
                player_error.setVisibility(INVISIBLE);
                break;
        }
    }

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
        player_loading_bg.setVisibility(VISIBLE);
        player_loading.setVisibility(INVISIBLE);
        player_error.setVisibility(VISIBLE);
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
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "VerPlayerLayout"), bandwidthMeter);
    }

    private void release() {
        if (player != null && !isError) {
            if (getCurrentPosition() > 0) {
                if ((player.getPlaybackState() == ExoPlayer.STATE_ENDED || getCurrentPosition() == getDuration())) {
                    verPlayerListener.setViewHistory(-1);
                } else {
                    verPlayerListener.setViewHistory(getCurrentPosition());
                }
            } else if (videoInfoUrl != null) {
                verPlayerListener.setViewHistory(playerPosition);
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
        LayoutInflater.from(getContext()).inflate(R.layout.ver_player_video_root, this);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        player_loading_bg = (ImageView) findViewById(R.id.player_loading_bg);
        player_loading = (ProgressBar) findViewById(R.id.player_loading);
        player_error = (TextView) findViewById(R.id.player_error);
        mediaController = new ControllerLayout(getContext());
        root = (FrameLayout) findViewById(R.id.root);
        mediaController.setAnchorView(root);
        player_error.setOnClickListener(this);
        mainHandler = new Handler();
        window = new Timeline.Window();
        mediaDataSourceFactory = buildDataSourceFactory(true);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.player_error) {
            player_loading_bg.setVisibility(VISIBLE);
            player_loading.setVisibility(VISIBLE);
            player_error.setVisibility(INVISIBLE);
            mediaController.hide();
            initializePlayer(true);
        }
    }

    @Override
    public void onClickShare() {
        verPlayerListener.onClickVerReplayShare();
    }

    @Override
    public void onClickLike() {
        verPlayerListener.onClickVerReplayLike();
    }

    @Override
    public void start() {
        player.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        player.setPlayWhenReady(false);
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
        return player != null ? (player.getDuration() == -1L ? 0 : player.getCurrentPosition()) : 0;
    }

    @Override
    public void seekTo(long var1) {
        long seekPosition = player.getDuration() == -1L ? 0 : Math.min(Math.max(0, var1), getDuration());
        player.seekTo(seekPosition);
    }

    @Override
    public boolean isPlaying() {
        return player != null ? player.getPlayWhenReady() : false;
    }

    @Override
    public int getBufferPercentage() {
        return player.getBufferedPercentage();
    }

    @Override
    public void changeSize(boolean isLarge) {

    }

    public VerPlayerLayout(Context context) {
        super(context);
        init();
    }

    public VerPlayerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void play(String url, long longTime) {
        Log.e(TAG, "playerUrl = " + url + ", time = " + longTime);
        playerPosition = (longTime == -1) ? 0 : longTime;
        isTimelineStatic = true;
        isFirstLoading = true;
        isError = false;
        videoInfoUrl = url;
        initializePlayer(true);
    }

    public void setVerPlayerListener(VerPlayerListener listener) {
        verPlayerListener = listener;
    }

    public void finishPlayer() {
        releasePlayer();
        if (mediaController != null)
            mediaController.finishController();
        verPlayerListener = null;
        mainHandler = null;
        window = null;
        mediaDataSourceFactory = null;
        System.gc();
    }


    public void replayVideo() {
        mediaController.replayVideo();
    }

    public void updateLikeViewBefore(boolean flag) {
        mediaController.updateLikeView(flag);
        if (flag) {
            mediaController.addHeart();
        }
    }

    public void showFirstLoading() {
        player_loading_bg.setVisibility(VISIBLE);
        player_loading.setVisibility(VISIBLE);
        player_error.setVisibility(INVISIBLE);
    }

    public void closeFirstLoading() {
        player_loading_bg.setVisibility(VISIBLE);
        player_loading.setVisibility(INVISIBLE);
        player_error.setVisibility(INVISIBLE);
    }

    public void updateLikeView(boolean flag) {
        mediaController.updateLikeView(flag);
    }

    public void releasePlayer() {
        isFirstLoading = true;
        isError = false;
        release();
        videoInfoUrl = null;
        playerNeedsSource = false;
        isTimelineStatic = false;
        playerWindow = 0;
        playerPosition = 0;
        if (mediaController != null) {
            mediaController.releaseController();
        }
        player_loading_bg.setVisibility(INVISIBLE);
        player_loading.setVisibility(INVISIBLE);
        player_error.setVisibility(INVISIBLE);
    }

    public void onResume() {
        if (videoInfoUrl != null) {
            if ((Util.SDK_INT <= 23 || player == null)) {
                mediaController.setPause(false);
                initializePlayer(false);
            }
        }
    }

    public void onPause() {
        if (videoInfoUrl != null) {
            if (Util.SDK_INT <= 23) {
                mediaController.setPause(true);
                release();
            }
        }
    }

    public void onStop() {
        if (videoInfoUrl != null) {
            if (Util.SDK_INT > 23) {
                release();
            }
        }
    }
}