package com.nucarf.exoplayerlibrary.verplayer;

/**
 * create time on on 2016/12/28.
 */
public interface ControllerListener {

    void onClickShare();

    void onClickLike();

    void start();

    void pause();

    void restart();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long var1);

    boolean isPlaying();

    int getBufferPercentage();

    void changeSize(boolean isLarge);
}
