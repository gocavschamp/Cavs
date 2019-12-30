package com.nucarf.exoplayerlibrary.ui;

/**
 * Created tt on 2016/12/27.
 */
public interface ExoPlayerListener {

    void doHorizontalScreen();

    void doVerticalScreen();

    void setIsDisableDraggableView(boolean b);

    void playerVideoPrepared();

    void playerVideoFinish();

    void playerVideoDuration(long pDuration);

    void onClickADLayout(String url);

    //点击了广告的视频
    void onClickADVideo(String pADVideoUrl);

    void onClickGoodsItem(String pGoodsId);

    void setViewHistory(long longTime);
}
