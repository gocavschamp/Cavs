package com.nucarf.exoplayerlibrary.ui;

/**
 * create time on on 2016/12/28.
 */
public interface ControllerListener {

    void doHorizontalScreen();

    void doVerticalScreen();

    void setIsDisableDraggableView(boolean b);

    void setCurrentPlayTime(String currentTime);

    void showADLayout();

    void hideADLayout();

    void start();

    void pause();

    void restart();

    long getDuration();

    long getCurrentPosition();

    String getTtitle();
    String getWatchNum();

    void seekTo(long var1);

    boolean isPlaying();

    int getBufferPercentage();
}