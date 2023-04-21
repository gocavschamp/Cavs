package com.nucarf.exoplayerlibrary.verplayer;

/**
 * create time on on 2016/12/27.
 */
public interface VerPlayerListener {

    void playerVideoFinish(boolean isFinish);

    void setViewHistory(long longTime);

    void onClickVerReplayShare();

    void onClickVerReplayLike();
}
