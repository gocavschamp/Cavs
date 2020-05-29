package com.example.myapp.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yuwenming on 2019/11/6.
 */
public class CustomLinealayout extends RelativeLayout {
    private Paint paint = new Paint();
    private AnimatorSet animatorSet;

    public CustomLinealayout(Context context) {
        super(context, null);
        init();

    }

    public CustomLinealayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomLinealayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomLinealayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        ArrayList<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RaderCircleView circleView = new RaderCircleView(getContext());
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
            layoutParams.addRule(CENTER_IN_PARENT, TRUE);
            circleView.setLayoutParams(layoutParams);
            addView(circleView);
            //分析该动画后将其拆分为缩放、渐变
            ObjectAnimator alpha = ObjectAnimator.ofFloat(circleView, "alpha", 1, 0.5f, 0);
            alpha.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            alpha.setStartDelay(i * 300);
            alpha.setRepeatMode(ObjectAnimator.RESTART);
            alpha.setDuration(1500);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(circleView, "scaleX", 1, 3, 5);
            scaleX.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleX.setRepeatMode(ObjectAnimator.RESTART);
            scaleX.setStartDelay(i * 300);
            scaleX.setDuration(1500);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(circleView, "scaleY", 1, 3, 5);
            scaleY.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleY.setRepeatMode(ObjectAnimator.RESTART);
            scaleY.setStartDelay(i * 300);
            scaleY.setDuration(1500);
            animatorList.add(alpha);
            animatorList.add(scaleX);
            animatorList.add(scaleY);
        }

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorList);

    }

    /**
     * 开始动画
     */
    public void startRippleAnimation() {
        animatorSet.start();
    }

    /**
     * 停止动画
     */
    public void stopRippleAnimation() {
        animatorSet.end();
    }

    private void addAnim(RaderCircleView circleView) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(circleView, "alpha", 1, 3, 5);
        alpha.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
        alpha.setRepeatMode(ObjectAnimator.RESTART);
        alpha.setDuration(1500);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(circleView, "scaleX", 1, 3, 5);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
        scaleX.setRepeatMode(ObjectAnimator.RESTART);
        scaleX.setDuration(1500);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(circleView, "scaleY", 1, 3, 5);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
        scaleY.setRepeatMode(ObjectAnimator.RESTART);
        scaleY.setDuration(1500);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        paint.setColor(Color.GREEN);
//        paint.setAntiAlias(true);
//        paint.setDither(true);
//        int width = getWidth();
//        int height = getHeight();
//        canvas.drawCircle(width / 3, height / 3, 30, paint);
//        canvas.drawCircle(width / 2, height / 3, 40, paint);
//        canvas.drawCircle(width / 3.5f, height / 3.5f, 50, paint);

    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
