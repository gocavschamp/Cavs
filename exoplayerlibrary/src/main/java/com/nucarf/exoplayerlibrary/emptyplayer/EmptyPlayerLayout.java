//package com.nucarf.exoplayerlibrary.emptyplayer;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import com.google.android.exoplayer2.C;
//import com.google.android.exoplayer2.DefaultLoadControl;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayer;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.drm.DrmSessionManager;
//import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
//import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
//import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
//import com.google.android.exoplayer2.drm.UnsupportedDrmException;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.source.dash.DashMediaSource;
//import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
//import com.google.android.exoplayer2.source.hls.HlsMediaSource;
//import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
//import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
//import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
//import com.google.android.exoplayer2.upstream.HttpDataSource;
//import com.google.android.exoplayer2.util.Util;
//import com.nucarf.base.utils.ScreenUtil;
//import com.nucarf.exoplayerlibrary.R;
//import com.nucarf.exoplayerlibrary.util.EventLogger;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//
///**
// * create time on on 2016/12/19.
// */
//public class EmptyPlayerLayout extends FrameLayout implements ExoPlayer.EventListener {
//
//    private final String TAG = " EmptyPlayer";
//    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
//
//    private EmptyPlayerListener emptyPlayerListener;
//    private Handler mainHandler;
//    private Timeline.Window window;
//    private EventLogger eventLogger;
//    private SimpleExoPlayerView simpleExoPlayerView;
//    private DataSource.Factory mediaDataSourceFactory;
//    private SimpleExoPlayer player;
//    private DefaultTrackSelector trackSelector;
//    private String videoInfo = null;
//    private boolean playerNeedsSource;
//    private boolean isTimelineStatic;
//    private boolean isFirstLoading = true;
//    private int playerWindow;
//    private long playerPosition;
//
//    private void initializePlayer() {
//        if (player == null) {
//            boolean preferExtensionDecoders = false;
//            UUID drmSchemeUuid = null;
//            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
//            if (drmSchemeUuid != null) {
//                String drmLicenseUrl = null;
//                String[] keyRequestPropertiesArray = null;
//                Map<String, String> keyRequestProperties;
//                if (keyRequestPropertiesArray == null || keyRequestPropertiesArray.length < 2) {
//                    keyRequestProperties = null;
//                } else {
//                    keyRequestProperties = new HashMap<>();
//                    for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
//                        keyRequestProperties.put(keyRequestPropertiesArray[i],
//                                keyRequestPropertiesArray[i + 1]);
//                    }
//                }
//                try {
//                    drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl,
//                            keyRequestProperties);
//                } catch (UnsupportedDrmException e) {
//                    return;
//                }
//            }
//
//            @SimpleExoPlayer.ExtensionRendererMode int extensionRendererMode =
//                    useExtensionRenderers() ? (preferExtensionDecoders ? SimpleExoPlayer.EXTENSION_RENDERER_MODE_PREFER
//                            : SimpleExoPlayer.EXTENSION_RENDERER_MODE_ON)
//                            : SimpleExoPlayer.EXTENSION_RENDERER_MODE_OFF;
//            TrackSelection.Factory videoTrackSelectionFactory =
//                    new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
//            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, new DefaultLoadControl(),
//                    drmSessionManager, extensionRendererMode);
//            player.addListener(this);
//            eventLogger = new EventLogger(trackSelector);
//            player.addListener(eventLogger);
//            player.setAudioDebugListener(eventLogger);
//            player.setVideoDebugListener(eventLogger);
//            player.setId3Output(eventLogger);
////            mediaController.setupListener(this);
////            mediaController.setEnabled(true);
//            simpleExoPlayerView.setPlayer(player);
//            if (isTimelineStatic) {
//                if (playerPosition == C.TIME_UNSET) {
//                    player.seekToDefaultPosition(playerWindow);
//                } else {
//                    player.seekTo(playerWindow, Math.max(0, playerPosition));
//                }
//            }
//            player.setPlayWhenReady(true);
//            playerNeedsSource = true;
//        }
//        if (playerNeedsSource) {
//            Uri[] uris;
//            String[] extensions;
//            uris = new Uri[]{Uri.parse(videoInfo)};
//            extensions = new String[]{null};
//            MediaSource[] mediaSources = new MediaSource[uris.length];
//            for (int i = 0; i < uris.length; i++) {
//                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
//            }
//            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
//                    : new ConcatenatingMediaSource(mediaSources);
//            player.prepare(mediaSource, !isTimelineStatic, !isTimelineStatic);
//            playerNeedsSource = false;
//        }
//    }
//
//    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
//        if(mediaDataSourceFactory == null) {
//            mediaDataSourceFactory = buildDataSourceFactory(true);
//        }
//        int type = Util.inferContentType(!TextUtils.isEmpty(overrideExtension) ? "." + overrideExtension
//                : uri.getLastPathSegment());
//        switch (type) {
//            case C.TYPE_SS:
//                return new SsMediaSource(uri, buildDataSourceFactory(false),
//                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
//            case C.TYPE_DASH:
//                return new DashMediaSource(uri, buildDataSourceFactory(false),
//                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
//            case C.TYPE_HLS:
//                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
//            case C.TYPE_OTHER:
//                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
//                        mainHandler, eventLogger);
//            default: {
//                throw new IllegalStateException("Unsupported type: " + type);
//            }
//        }
//    }
//
//    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(UUID uuid, String licenseUrl, Map<String, String> keyRequestProperties) throws UnsupportedDrmException {
//        if (Util.SDK_INT < 18) {
//            return null;
//        }
//        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl,
//                buildHttpDataSourceFactory(false), keyRequestProperties);
//        return new StreamingDrmSessionManager<>(uuid,
//                FrameworkMediaDrm.newInstance(uuid), drmCallback, null, mainHandler, eventLogger);
//    }
//
//    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
//        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
//    }
//
//    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
//        return buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
//    }
//
//    @Override
//    public void onLoadingChanged(boolean isLoading) {
//    }
//
//    @Override
//    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//        Log.e(TAG, "playWhenReady = " + playWhenReady + ", playbackState = " + playbackState);
//        switch (playbackState) {
//            case ExoPlayer.STATE_READY:
//                if (isFirstLoading) {
//                    if (emptyPlayerListener != null)
//                        emptyPlayerListener.playerStart();
//                    isFirstLoading = false;
//                }else {
//                    if (emptyPlayerListener != null)
//                        emptyPlayerListener.playerStart();
//                }
//                break;
//            case ExoPlayer.STATE_BUFFERING:
//                if (emptyPlayerListener != null)
//                    emptyPlayerListener.playerBuffering();
//                break;
//            case ExoPlayer.STATE_ENDED:
//                if (emptyPlayerListener != null)
//                    emptyPlayerListener.playerEnd();
//                break;
//        }
//    }
//
//    @Override
//    public void onPositionDiscontinuity() {
//    }
//
//    @Override
//    public void onTimelineChanged(Timeline timeline, Object manifest) {
//        if(window == null) {
//            window = new Timeline.Window();
//        }
//        if(mediaDataSourceFactory == null) {
//            mediaDataSourceFactory = buildDataSourceFactory(true);
//        }
//
//        isTimelineStatic = !timeline.isEmpty()
//                && !timeline.getWindow(timeline.getWindowCount() - 1, window).isDynamic;
//    }
//
//    @Override
//    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//    }
//
//    @Override
//    public void onPlayerError(ExoPlaybackException e) {
//        if (emptyPlayerListener != null)
//            emptyPlayerListener.playerError();
//        release();
//        playerNeedsSource = true;
//    }
//
//    private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
//        return new DefaultDataSourceFactory(getContext(), bandwidthMeter,
//                buildHttpDataSourceFactory(bandwidthMeter));
//    }
//
//    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
//        return new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "EmptyPlayerLayout"), bandwidthMeter);
//    }
//
//    private boolean useExtensionRenderers() {
//        return BuildConfig.FLAVOR.equals("withExtensions");
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public EmptyPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }
//
//    private void init() {
//        LayoutInflater.from(getContext()).inflate(R.layout.empty_player_video, this);
//        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
//
//        mainHandler = new Handler();
//        window = new Timeline.Window();
//        mediaDataSourceFactory = buildDataSourceFactory(true);
//    }
//
//    private void release() {
//        if (player != null) {
//            playerWindow = player.getCurrentWindowIndex();
//            playerPosition = C.TIME_UNSET;
//            Timeline timeline = player.getCurrentTimeline();
//            if (!timeline.isEmpty() && timeline.getWindow(playerWindow, window).isSeekable) {
//                playerPosition = player.getCurrentPosition();
//            }
//            player.release();
//            player = null;
//            trackSelector = null;
//            eventLogger = null;
//        }
//    }
//
//    public EmptyPlayerLayout(Context context) {
//        super(context);
//        init();
//    }
//
//    public EmptyPlayerLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public EmptyPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    public void play(String url) {
//        playerPosition = 0;
//        isFirstLoading = true;
//        videoInfo = url;
//        initializePlayer();
//    }
//
//    public long getCurrentPosition() {
//        return player != null ? (player.getDuration() == -1L ? 0 : player.getCurrentPosition()) : 0;
//    }
//
//    public long getDuration() {
//        return player != null ? player.getDuration() == -1L ? 0 : player.getDuration() : 0;
//    }
//
//    public void restart() {
//        player.setPlayWhenReady(true);
//        player.seekTo(0);
//    }
//
//    public void seekTo(long var1) {
//        player.seekTo((player.getDuration() == -1L ? 0 : Math.min(Math.max(0, var1), getDuration())));
//    }
//
//    public boolean isPlaying() {
//        return player != null ? player.getPlayWhenReady() : false;
//    }
//
//    public int getBufferPercentage() {
//        return player.getBufferedPercentage();
//    }
//
//    public void pause() {
//        if (player != null) {
//            player.setPlayWhenReady(false);
//        }
//    }
//
//    public void start() {
//        if (player != null) {
//            player.setPlayWhenReady(true);
//        }
//    }
//
//    public void doVerticalScreen() {
////        exoPlayerListener.doVerticalScreen();
//        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
//    }
//
//    public void doHorizontalScreen() {
////        exoPlayerListener.doHorizontalScreen();
//        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
//
//    }
//
//    public void setEmptyPlayerListener(EmptyPlayerListener listener) {
//        emptyPlayerListener = listener;
//    }
//
//    public void releasePlayer() {
//        release();
//        playerPosition = 0;
//        videoInfo = null;
//        isFirstLoading = true;
////        emptyPlayerListener = null;
////        mainHandler = null;
////        window = null;
////        mediaDataSourceFactory = null;
////        System.gc();
//    }
//    public void finishPlayer() {
//        release();
//        playerPosition = 0;
//        videoInfo = null;
//        isFirstLoading = true;
//        emptyPlayerListener = null;
//        mainHandler = null;
//        window = null;
//        mediaDataSourceFactory = null;
//        System.gc();
//    }
//
//    public void onResume() {
//        if (videoInfo != null) {
//            if ((Util.SDK_INT <= 23 || player == null)) {
//                initializePlayer();
//            }
//        }
//    }
//
//    public void onPause() {
//        pause();
//        if (videoInfo != null) {
//            if (Util.SDK_INT <= 23) {
//                release();
//            }
//        }
//    }
//
//    public void onStop() {
//        if (videoInfo != null) {
//            if (Util.SDK_INT > 23) {
//                release();
//            }
//        }
//    }
//
//    public void changePlayerSize(boolean isLarge){
//        if(isLarge){
//            ViewGroup.LayoutParams layoutParams = simpleExoPlayerView.getLayoutParams();
//            int height =  ScreenUtil.getScreenHeight(getContext());
//            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
//            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
//            simpleExoPlayerView.setLayoutParams(layoutParams);
//        }else{
//            ViewGroup.LayoutParams layoutParams = simpleExoPlayerView.getLayoutParams();
//            int height = ScreenUtil.getScreenWidth(getContext()) * 9 / 16;
//            layoutParams.height = height;
//            layoutParams.width = ScreenUtil.getScreenWidth(getContext()) * height / ScreenUtil.getScreenHeight(getContext());
//            simpleExoPlayerView.setLayoutParams(layoutParams);
//        }
//    }
//}
