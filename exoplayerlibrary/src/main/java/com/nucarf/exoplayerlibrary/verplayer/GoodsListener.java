package com.nucarf.exoplayerlibrary.verplayer;

/**
 * create time on on 2017/5/10.
 */
public interface GoodsListener {
    void onExpandGoodsView();

    void onCloseGoodsView();

    void onClickGoodsItem(String pGoodId);
}
