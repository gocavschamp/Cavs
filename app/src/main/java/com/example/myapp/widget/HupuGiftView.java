package com.example.myapp.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapp.R;
import com.nucarf.base.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class HupuGiftView extends RelativeLayout {

    private static final String TAG = "hupu---";
    private final int mDefaltTextColor = 0xffffff;
    private final int text_color;
    private final int team1_bg_color;
    private final int team2_bg_color;
    private final int gift_bg;
    private final int name_text_size;
    private final int gift_text_size;
    private final int team_size;
    private final int gift_size;
    private final int gift_margin;
    @BindView(R.id.tv_gift3)
    TextView tvGift3;
    @BindView(R.id.tv_gift2)
    TextView tvGift2;
    @BindView(R.id.tv_gift1)
    TextView tvGift1;
    @BindView(R.id.tv_flag)
    TextView tvFlag;
    private String team1_flag;
    private String team2_flag;
    private int defWidth;
    private int defHeight;
    private Paint team1BgPaint;
    private Paint textPaint;
    private Paint team1FlagPaint;
    private Paint hupuViewBgPaint;
    private Paint gifttextPaint;
    private boolean isShow = false;
    private View rl_content;
    private boolean isShowing = false;

    public HupuGiftView(Context context) {
        this(context, null, 0);
    }

    public HupuGiftView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HupuGiftView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HupuGift);
//         <attr name="team1_flag" format="string" />
//        <attr name="team2_flag" format="string" />
//        <attr name="text_color" format="color" />
//        <attr name="team1_bg_color" format="color" />
//        <attr name="team2_bg_color" format="color" />
//        <attr name="name_text_size" format="dimension" />
//        <attr name="gift_text_size" format="dimension" />
//        <attr name="team_size" format="dimension" />
//        <attr name="gift_size" format="dimension" />
//        <attr name="gift_margin" format="dimension" />
//        <attr name="gift_bg" format="color" />
        team1_flag = typedArray.getString(R.styleable.HupuGift_team1_flag);
        team2_flag = typedArray.getString(R.styleable.HupuGift_team2_flag);
        text_color = typedArray.getColor(R.styleable.HupuGift_text_color, Color.WHITE);
        team1_bg_color = typedArray.getColor(R.styleable.HupuGift_team1_bg_color, Color.GREEN);
        team2_bg_color = typedArray.getColor(R.styleable.HupuGift_team2_bg_color, Color.BLUE);
        gift_bg = typedArray.getColor(R.styleable.HupuGift_gift_bg, Color.BLUE);
        name_text_size = typedArray.getDimensionPixelSize(R.styleable.HupuGift_name_text_size, 16);
        gift_text_size = typedArray.getDimensionPixelSize(R.styleable.HupuGift_gift_text_size, 15);
        team_size = typedArray.getDimensionPixelSize(R.styleable.HupuGift_team_size, 80);
        gift_size = typedArray.getDimensionPixelSize(R.styleable.HupuGift_gift_size, 60);
        gift_margin = typedArray.getDimensionPixelSize(R.styleable.HupuGift_gift_margin, 10);
        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
//        team1BgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        team1BgPaint.setColor(team1_bg_color);
//        Paint team2BgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        textPaint.setColor(text_color);
//        textPaint.setTextSize(name_text_size);
//        gifttextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        gifttextPaint.setColor(text_color);
//        gifttextPaint.setTextSize(gift_text_size);
//        team1FlagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        hupuViewBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        View inflate = View.inflate(context, R.layout.hupu_gift_view, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(Gravity.RIGHT);
        inflate.setLayoutParams(layoutParams);
//        ButterKnife.bind(inflate);
        addView(inflate);
        tvFlag = inflate.findViewById(R.id.tv_flag);
        rl_content = inflate.findViewById(R.id.rl_content);
        tvGift1 = inflate.findViewById(R.id.tv_gift1);
        tvGift2 = inflate.findViewById(R.id.tv_gift2);
        tvGift3 = inflate.findViewById(R.id.tv_gift3);
        tvFlag.setOnClickListener((v -> {
            if (!isShow) {
                showGift();
            } else {
                closeGift();
            }
        }));


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void showGift() {
        if (isShowing) {
            return;
        }
        int mWidth = tvFlag.getWidth() + tvGift1.getWidth() * 3 + 10 * 4;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(tvFlag.getWidth(), mWidth+30);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) value, ViewGroup.LayoutParams.WRAP_CONTENT);
                rl_content.setLayoutParams(layoutParams);
            }
        });

        ObjectAnimator rotation = ObjectAnimator.ofFloat(tvGift1, "rotation", 0, -360);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(tvGift1, "translationX", 0, -(tvFlag.getWidth() + 10));
        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(tvGift2, "rotation", 0, -360);
        ObjectAnimator translationX1 = ObjectAnimator.ofFloat(tvGift2, "translationX", 0, -(tvFlag.getWidth() + tvGift1.getWidth() + 20));
        ObjectAnimator rotation2 = ObjectAnimator.ofFloat(tvGift3, "rotation", 0, -360);
        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(tvGift3, "translationX", 0, -(tvFlag.getWidth() + tvGift1.getWidth() * 2 + 30));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShow = true;
                isShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isShowing = false;

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(rotation, rotation1, rotation2, translationX, translationX1, translationX2, valueAnimator);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }


    private void closeGift() {
        if (isShowing) {
            return;
        }
        int mWidth = tvFlag.getWidth() + tvGift1.getWidth() * 3 + 10 * 4;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mWidth+60, tvFlag.getMeasuredWidth());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) value, ViewGroup.LayoutParams.WRAP_CONTENT);
                rl_content.setLayoutParams(layoutParams);
            }
        });
        ObjectAnimator rotation = ObjectAnimator.ofFloat(tvGift1, "rotation", 0, 360);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(tvGift1, "translationX", -(tvFlag.getWidth()- tvGift1.getWidth() -10), 0);
        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(tvGift2, "rotation", 0, 360);
        ObjectAnimator translationX1 = ObjectAnimator.ofFloat(tvGift2, "translationX", (tvFlag.getWidth() - tvGift1.getWidth()*2 -10), 0);
        ObjectAnimator rotation2 = ObjectAnimator.ofFloat(tvGift3, "rotation", 0, 360);
        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(tvGift3, "translationX", (tvFlag.getWidth() - tvGift1.getWidth() *3 - 20), 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.playTogether(rotation, rotation1, rotation2, translationX, translationX1, translationX2, valueAnimator);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShow = false;
                isShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isShowing = false;

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(defWidth - 10 - team_size / 2, 10 + team_size / 2, team_size / 2, team1BgPaint);

    }
}
