package com.nucarf.exoplayerlibrary.emptyplayer;

/**
 * create time on on 2016/12/19.
 */
public interface EmptyPlayerListener {

    void playerStart();

    void playerEnd();

    void playerError();

    void playerBuffering();
}
