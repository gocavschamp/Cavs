package com.nucarf.exoplayerlibrary.emptyplayer;

/**
 * Created tt on 2016/12/19.
 */
public interface EmptyPlayerListener {

    void playerStart();

    void playerEnd();

    void playerError();

    void playerBuffering();
}
