package com.example.myapp.widget.gift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.moonlight.flyvideo.R;
import com.example.myapp.bean.GiftBean;

import java.util.Comparator;
import java.util.TreeMap;

public class GiftRootLayout extends LinearLayout implements Animation.AnimationListener, GiftAnimListener {

    public final String TAG = GiftRootLayout.class.getSimpleName();

    private GiftItemLayout firstItemLayout, lastItemLayout;
    private Animation firstGiftItemInAnim, firstGiftItemOutAnim;
    private Animation lastGiftItemInAnim, lastGiftItemOutAnim;
    private final TreeMap<String, GiftBean> giftBeanTreeMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    });

    public GiftRootLayout(Context context) {
        super(context);
        init(context);
    }

    public GiftRootLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftRootLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("NewApi")
    public GiftRootLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        firstGiftItemInAnim = AnimationUtils.loadAnimation(context, R.anim.gift_in);
        firstGiftItemInAnim.setFillAfter(true);
        firstGiftItemOutAnim = AnimationUtils.loadAnimation(context, R.anim.gift_out);
        firstGiftItemOutAnim.setInterpolator(new AnticipateInterpolator());
        firstGiftItemOutAnim.setFillAfter(true);

        lastGiftItemInAnim = AnimationUtils.loadAnimation(context, R.anim.gift_in);
        lastGiftItemInAnim.setFillAfter(true);
        lastGiftItemOutAnim = AnimationUtils.loadAnimation(context, R.anim.gift_out);
        lastGiftItemOutAnim.setInterpolator(new AnticipateInterpolator());
        lastGiftItemOutAnim.setFillAfter(true);

        firstGiftItemOutAnim.setAnimationListener(this);
        lastGiftItemOutAnim.setAnimationListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!changed || getChildCount() == 0) return;
        firstItemLayout = findViewById(R.id.firstItemLayout);
        firstItemLayout.setAnimListener(this);
        lastItemLayout = findViewById(R.id.lastItemLayout);
        lastItemLayout.setAnimListener(this);
    }

    public void loadGift(GiftBean giftBean){
        if(giftBeanTreeMap==null)return;
        String tag = giftBean.getUserName() + giftBean.getPresent_name();
        if (firstItemLayout.getState() == GiftItemLayout.GIFTITEM_SHOW && firstItemLayout.getMyTag().equals(tag)) {
            firstItemLayout.addCount(giftBean.getGroup());
            return;
        }
        if (lastItemLayout.getState() == GiftItemLayout.GIFTITEM_SHOW && lastItemLayout.getMyTag().equals(tag)) {
            lastItemLayout.addCount(giftBean.getGroup());
            return;
        }
        addGift(giftBean);
    }

    public void addGift(GiftBean giftBean){
        if(giftBeanTreeMap==null)return;
        if (giftBeanTreeMap.size() == 0) {
            giftBeanTreeMap.put(giftBean.getPresent_id(), giftBean);
            showGift();
            return;
        }
        for (String key : giftBeanTreeMap.keySet()) {
            GiftBean result = giftBeanTreeMap.get(key);
            String tagNew = giftBean.getUserName() + giftBean.getPresent_name();
            String tagOld = result.getUserName() + result.getPresent_name();
            if (tagNew.equals(tagOld)) {
                giftBean.setGroup(result.getGroup() + 1);
                giftBeanTreeMap.remove(result.getPresent_id());
                giftBeanTreeMap.put(giftBean.getPresent_id(), giftBean);
                return;
            }
        }
        giftBeanTreeMap.put(giftBean.getPresent_id(), giftBean);
    }

    public void showGift(){
        if(isEmpty())return;
        if(firstItemLayout.getState()==GiftItemLayout.GIFTITEM_DEFAULT){
            firstItemLayout.setData(getGift());
            firstItemLayout.setVisibility(View.VISIBLE);
            firstItemLayout.startAnimation(firstGiftItemInAnim);
            firstItemLayout.startAnimation();
        }else if(lastItemLayout.getState()==GiftItemLayout.GIFTITEM_DEFAULT){
            lastItemLayout.setData(getGift());
            lastItemLayout.setVisibility(View.VISIBLE);
            lastItemLayout.startAnimation(lastGiftItemInAnim);
            lastItemLayout.startAnimation();
        }
    }

    public GiftBean getGift(){
        GiftBean giftBean = null;
        if (giftBeanTreeMap.size() != 0) {
            // 获取队列首个礼物实体
            giftBean = giftBeanTreeMap.firstEntry().getValue();
            // 移除队列首个礼物实体
            giftBeanTreeMap.remove(giftBeanTreeMap.firstKey());
        }
        return giftBean;
    }

    /**
     * 礼物是否为空
     */
    public boolean isEmpty() {
        return giftBeanTreeMap == null || giftBeanTreeMap.size() == 0;
    }

    @Override
    public void onAnimationStart(Animation animation) { }
    @Override
    public void onAnimationEnd(Animation animation) {
        showGift();
    }
    @Override
    public void onAnimationRepeat(Animation animation) { }

    @Override
    public void giftAnimEnd(int position) {
        switch (position) {
            case 1:
                firstItemLayout.startAnimation(firstGiftItemOutAnim);
                break;
            case 0:
                lastItemLayout.startAnimation(lastGiftItemOutAnim);
                break;
        }
    }
}