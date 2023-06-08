package com.example.myapp.widget.gift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moonlight.flyvideo.R;
import com.example.myapp.bean.GiftBean;
import com.nucarf.base.utils.GlideUtils;


public class GiftItemLayout extends LinearLayout implements Animation.AnimationListener {

    public final String TAG = GiftItemLayout.class.getSimpleName();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    handler.removeCallbacksAndMessages(null);
                    state = GIFTITEM_DEFAULT;
                    if (animListener == null) break;
                    animListener.giftAnimEnd(index);
                    break;
            }
        }
    };

    public static final int SHOW_TIME = 1700;
    public static final int GIFTITEM_DEFAULT = 0;
    public static final int GIFTITEM_SHOW = 1;

    /**
     * 当前显示状态
     */
    public int state = GIFTITEM_DEFAULT;
    /**
     * 当前显示位置
     */
    public int index;
    /**
     * 当前tag
     */
    public String tag;

    private GiftBean giftBean;

    private TextView tvUserName;
    private TextView tvMessage;
    private ImageView ivgift;
    private LinearLayout ll_Content;
    private TextView giftNum;

    /**
     * 透明度动画(200ms), 连击动画(200ms)
     */
    private Animation translateAnim, numAnim, giftPicAnim;
    private GiftAnimListener animListener;
    private Context context;

    public GiftItemLayout(Context context) {
        super(context);
        init(context, null);
    }

    public GiftItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GiftItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("NewApi")
    public GiftItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.item_small_gift, this);
        this.context = context;
//        crvheadimage = findViewById(R.id.crvheadimage);
        tvUserName = findViewById(R.id.tv_UserName);
        ll_Content = findViewById(R.id.ll_Content);
        tvMessage = findViewById(R.id.tv_Message);
        ivgift = findViewById(R.id.ivgift);
        giftNum = findViewById(R.id.giftNum);
        initTranslateAnim();
        initNumAnim();
        initGiftPicAnim();

        if (null == attrs) return;
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.GiftItemLayout, 0, 0);
        if (null == typed) return;
        index = typed.getInteger(R.styleable.GiftItemLayout_gift_index, 0);
        typed.recycle();
    }

    /**
     * 设置礼物item显示的数据
     *
     * @param giftBean
     */
    public void setData(GiftBean giftBean) {
        this.giftBean = giftBean;
        tag = giftBean.getUserName() + giftBean.getPresent_name();
        tvUserName.setText(TextUtils.isEmpty(giftBean.getUserName()) ? "" : giftBean.getUserName());
        tvMessage.setText(TextUtils.isEmpty(giftBean.getTip_message()) ? "" : giftBean.getTip_message());
        giftNum.setText("x" + giftBean.getGroup());
        GlideUtils.load(context, giftBean.getPic_url(), ivgift);
    }

    /**
     * 执行了礼物数量连接效果
     *
     * @param group
     */
    public void addCount(int group) {
        handler.removeMessages(0);
        giftBean.group = giftBean.group + group;
        giftNum.setText("x" + giftBean.group);
        giftNum.startAnimation(numAnim);// 执行礼物数量动画
    }

    public void startAnimation() {
        ll_Content.startAnimation(translateAnim);
        state = GIFTITEM_SHOW;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == translateAnim) {// 头像渐变动画执行完毕
            ll_Content.clearAnimation();
            giftNum.startAnimation(numAnim);// 执行礼物数量动画
            ivgift.startAnimation(giftPicAnim);// 执行礼物动画
        } else {
            handler.sendEmptyMessageDelayed(0, SHOW_TIME);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    /**
     * 初始化位移动画
     */
    private void initTranslateAnim() {
        translateAnim = new TranslateAnimation(-300, 0, 0, 0);
        translateAnim.setDuration(200);
        translateAnim.setFillAfter(true);
        translateAnim.setAnimationListener(this);
    }

    /**
     * 初始化礼物数字动画
     */
    private void initNumAnim() {
        numAnim = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        numAnim.setDuration(200);
        numAnim.setAnimationListener(this);
    }

    /**
     * 初始化礼物图片动画
     */
    private void initGiftPicAnim() {
        giftPicAnim = new ScaleAnimation(2.5f, 1.0f, 2.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        giftPicAnim.setDuration(200);
        giftPicAnim.setAnimationListener(this);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMyTag() {
        return tag;
    }

    public void setMyTag(String tag) {
        this.tag = tag;
    }

    public GiftAnimListener getAnimListener() {
        return animListener;
    }

    public void setAnimListener(GiftAnimListener animListener) {
        this.animListener = animListener;
    }
}