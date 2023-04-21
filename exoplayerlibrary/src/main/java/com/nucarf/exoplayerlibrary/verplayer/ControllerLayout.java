package com.nucarf.exoplayerlibrary.verplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


import com.nucarf.base.widget.PeriscopeLayout;
import com.nucarf.exoplayerlibrary.R;
import com.nucarf.exoplayerlibrary.util.ScreenUtils;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;



/**
 * create time on on 16/4/18.
 */
public class ControllerLayout extends FrameLayout implements View.OnClickListener, OnSeekBarChangeListener {

    private ControllerListener controllerListener;
    private ViewGroup mAnchor;
    private View mRoot;
    private SeekBar mProgress;
    private TextView mEndTime;
    private TextView mCurrentTime;
    private ImageView mPauseButton;
    private Handler mHandler;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private PeriscopeLayout periscopeLayout;
    private ImageView share;
    private ImageView like;
    private boolean isFinish = false;
    private boolean mShowing = false;
    private boolean isPause = false;

    public ControllerLayout(Context context) {
        super(context);
        mHandler = new ControllerLayout.MessageHandler(this);
        mRoot = null;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null) {
            initControllerView(mRoot);
        }
    }

    protected View makeControllerView() {
        mRoot = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ver_player_media_controller, null);
        initControllerView(mRoot);
        return mRoot;
    }

    @SuppressLint({"WrongViewCast"})
    private void initControllerView(View v) {
        mPauseButton = (ImageView) v.findViewById(R.id.pause);
        mPauseButton.setOnClickListener(this);
        share = (ImageView) v.findViewById(R.id.share);
        share.setOnClickListener(this);
        like = (ImageView) v.findViewById(R.id.like);
        like.setOnClickListener(this);
        mProgress = (SeekBar) v.findViewById(R.id.mediacontroller_progress);
        mProgress.setOnSeekBarChangeListener(this);
        mProgress.setMax(1000);
        mEndTime = (TextView) v.findViewById(R.id.time);
        mCurrentTime = (TextView) v.findViewById(R.id.time_current);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        periscopeLayout = (PeriscopeLayout) v.findViewById(R.id.periscopeLayout);
        ScreenUtils.setRelativeLayoutParams(periscopeLayout, (ScreenUtils.getScreenWidth(getContext()) - ScreenUtils.dip2px(getContext(), 30)) / 5, ScreenUtils.getScreenHeight(getContext()) / 2);
    }

    private String stringForTime(long timeMs) {
        int totalSeconds = (int) timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60 % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        return hours > 0 ? mFormatter.format("%d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString() : mFormatter.format("%d:%02d:%02d", new Object[]{0, Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
    }

    private long setProgress() {
        if (controllerListener != null) {
            long position = controllerListener.getCurrentPosition();
            long duration = controllerListener.getDuration();
            if (mProgress != null) {
                if (duration > 0) {
                    if (!mProgress.isEnabled()) {
                        mProgress.setEnabled(true);
                    }
                    long percent = 1000L * position / duration;
                    mProgress.setProgress((int) percent);
                } else {
                    mProgress.setProgress(0);
                    mProgress.setEnabled(false);
                }
                int percent1 = controllerListener.getBufferPercentage();
                mProgress.setSecondaryProgress(percent1 * 10);
            }
            if (mEndTime != null) {
                mEndTime.setText(stringForTime(duration));
            }
            if (mCurrentTime != null) {
                mCurrentTime.setText(stringForTime(position));
            }
            return position;
        } else {
            return 0;
        }
    }

    private void updatePausePlay() {
        if (mRoot != null && mPauseButton != null && controllerListener != null) {
            if (isFinish) {
                mPauseButton.setImageResource(R.mipmap.ver_player_btn_stop);
            } else if (controllerListener.isPlaying()) {
                mPauseButton.setImageResource(R.mipmap.exo_player_btn_bofang);
            } else {
                mPauseButton.setImageResource(R.mipmap.ver_player_btn_stop);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.pause) {
            if (isFinish) {
                doRestart();
            } else {
                doPauseResume();
            }
            show();
        } else if (i == R.id.share) {
            if (controllerListener != null) {
                controllerListener.onClickShare();
            }
        } else if (i == R.id.like) {
            if (controllerListener != null) {
                controllerListener.onClickLike();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            long duration = controllerListener.getDuration();
            long newposition = duration * (long) progress / 1000L;
            controllerListener.seekTo((int) newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newposition));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        updatePausePlay();
        show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private class MessageHandler extends Handler {
        private final WeakReference<ControllerLayout> mView;

        MessageHandler(ControllerLayout view) {
            mView = new WeakReference(view);
        }

        public void handleMessage(Message msg) {
            ControllerLayout view = mView.get();
            if (view != null && view.controllerListener != null) {
                switch (msg.what) {
                    case 1:
                        periscopeLayout.addHeart();
                        break;
                    case 2:
                        if (view.controllerListener.isPlaying() && !view.isFinish)
                            sendMessageDelayed(obtainMessage(2), (1000 - view.setProgress() % 1000));
                        break;
                }
            }
        }
    }

    private void doPauseResume() {
        if (controllerListener != null) {
            if (controllerListener.isPlaying()) {
                controllerListener.pause();
            } else {
                controllerListener.start();
            }
        }
        updatePausePlay();
    }

    private void doRestart() {
        if (controllerListener != null) {
            controllerListener.restart();
            updatePausePlay();
        }
    }

    public void setupListener(ControllerListener listener) {
        controllerListener = listener;
        updatePausePlay();
    }

    public void setAnchorView(ViewGroup view) {
        mAnchor = view;
        LayoutParams frameParams = new LayoutParams(-1, -1);
        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
    }

    public void setFinish(boolean flag) {
        isFinish = flag;
        mHandler.removeMessages(2);
        if (!isFinish) {
            mHandler.sendEmptyMessage(2);
        } else {
            mProgress.setProgress(1000);
        }
        updatePausePlay();
    }

    public void replayVideo() {
        doRestart();
        show();
    }

    public void show() {
        if (!mShowing && mAnchor != null) {
            LayoutParams msg = new LayoutParams(-1, -2, 80);
            mAnchor.addView(this, msg);
            mShowing = true;
        }
        updatePausePlay();
    }

    public void hide() {
        if (mAnchor != null && !isPause) {
            mAnchor.removeView(this);
            mShowing = false;
        }
    }

    public void addHeart() {
        mHandler.sendEmptyMessage(1);
    }

    public void updateLikeView(boolean flag) {
        if (flag) {
            like.setImageResource(R.mipmap.ver_btn_dianzan2);
        } else {
            like.setImageResource(R.mipmap.ver_btn_dianzan1);
        }
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public void releaseController() {
        if (mHandler != null) {
            mHandler.removeMessages(1);
            mHandler.removeMessages(2);
        }
        if (mAnchor != null) {
            mAnchor.removeView(this);
        }
        isFinish = false;
        mShowing = false;
    }

    public void finishController() {
        releaseController();
        controllerListener = null;
        mHandler = null;
        mFormatBuilder = null;
        mFormatter = null;
        mAnchor = null;
        System.gc();
    }
}