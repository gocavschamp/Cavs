package com.nucarf.exoplayerlibrary.verplayer;

/**
 * Created by wlj on 2017/5/10.
 */
public interface GoodsListener {
    void onExpandGoodsView();

    void onCloseGoodsView();

    void onClickGoodsItem(String pGoodId);
}
