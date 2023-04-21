package com.example.myapp.homepage.homedemo.bannertest;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.homepage.homedemo.bannertest.bean.DataBean;
import com.youth.banner.transformer.BasePageTransformer;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @Description TODO
 * @Author page
 * @Date 2020/11/9 17:19
 */
public class HorizontalStackTransformerWithRotation extends BasePageTransformer {
    private static final float CENTER_PAGE_SCALE = 0.8f;
    private int offscreenPageLimit = 3;
    private int overlayCount = DataBean.getTestData().size();
    private float scaleOffset = 40;
    private float transOffset = 40;


    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position <= 0.0f) {//当前页
            page.setTranslationX(0f);
            page.setAlpha(1 - 0.5f * Math.abs(position));
            page.setClickable(true);
        } else {
            otherTrans(page, position);
            page.setClickable(false);
        }
    }
    private void otherTrans(View page, float position) {
        //缩放比例
        float scale = (page.getWidth() - scaleOffset * position) / (float) (page.getWidth());
        page.setScaleX(scale);
        page.setScaleY(scale);

        page.setAlpha(1f);
        if (position > overlayCount - 1 && position < overlayCount) { //当前页向右滑动时,最右面第四个及以后页面应消失
            float curPositionOffset = transOffset * (float) Math.floor(position); //向下取整
            float lastPositionOffset = transOffset * (float) Math.floor(position - 1); //上一个卡片的偏移量
            float singleOffset = 1 - Math.abs(position % (int) position);
            float transX = (-page.getWidth() * position) + (lastPositionOffset + singleOffset * (curPositionOffset - lastPositionOffset));
            page.setTranslationX(transX);
        } else if (position <= overlayCount - 1) {
            float transX = (-page.getWidth() * position) + (transOffset * position);
            page.setTranslationX(transX);
        } else {
            page.setAlpha(0f);
//            page.setTranslationX(0); //不必要的隐藏在下面
        }}
}