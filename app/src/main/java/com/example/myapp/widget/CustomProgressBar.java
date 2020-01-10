package com.example.myapp.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.myapp.R;
import com.nucarf.base.utils.NumberUtils;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * Created by yuwenming on 2020/1/10.
 */
public class CustomProgressBar extends View {

    private int mInnerSize;
    private int mOutSize;
    private int mTextSize;
    private int mOutTextColor;
    private int mInnerTextColor;
    private String mText;
    private int mTextColor;
    private Paint outPaint;
    private Paint innerPaint;
    private Paint textPaint;
    private int defWidth;
    private int defHeight;
    private Canvas canvas;
    private float sweepValue = 0f;

    public CustomProgressBar(Context context) {
        this(context, null, 0);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        mText = typedArray.getString(R.styleable.CustomProgressBar_bar_text);
        int def_text_color = context.getResources().getColor(R.color.main_color);
        int out_text_color = context.getResources().getColor(R.color.color_e6e6e6);
        int inner_text_color = context.getResources().getColor(R.color.color_0099ff);
        mTextColor = typedArray.getColor(R.styleable.CustomProgressBar_bar_text_color, def_text_color);
        mOutTextColor = typedArray.getColor(R.styleable.CustomProgressBar_bar_out_color, out_text_color);
        mInnerTextColor = typedArray.getColor(R.styleable.CustomProgressBar_bar_inner_color, inner_text_color);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomProgressBar_bar_text_size, 15);
        mOutSize = typedArray.getDimensionPixelSize(R.styleable.CustomProgressBar_bar_out_size, 25);
        mInnerSize = typedArray.getDimensionPixelSize(R.styleable.CustomProgressBar_bar_inner_size, 25);
        typedArray.recycle();
        outPaint = new Paint();
        innerPaint = new Paint();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        innerPaint.setAntiAlias(true);
        outPaint.setAntiAlias(true);
        initTextPanit();
        initInnerPanit();
        initOuterPanit();
        init();
    }

    private void init() {

        resetValue(Float.parseFloat(mText));

    }

    public void resetValue(float value) {
        this.animate().cancel();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, value);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float aFloat = (float) animation.getAnimatedValue();
                mText = NumberUtils.totalMoney(aFloat + "");
                sweepValue = (aFloat / 100f) * 360f;
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mText = value + "";
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        defWidth = AutoSizeUtils.dp2px(getContext(), 100);
        defHeight = AutoSizeUtils.dp2px(getContext(), 100);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            defWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            defHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        defHeight = defWidth = Math.min(defHeight, defWidth);
        setMeasuredDimension(defWidth, defHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        drawText(canvas);
        drawOuter(canvas);
        drawInner(sweepValue);

    }

    private void drawInner(float sweepAngle) {
        if (canvas == null) {
            return;
        }
        if (innerPaint == null) {
            return;
        }
        RectF rect = new RectF(mInnerSize / 2, mInnerSize / 2, defWidth - mInnerSize / 2, defHeight - mInnerSize / 2);
        canvas.drawArc(rect, -90f, sweepAngle, false, innerPaint);
    }

    private void drawOuter(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        if (outPaint == null) {
            return;
        }
        canvas.drawCircle(defWidth / 2, defHeight / 2, defWidth / 2 - mOutSize / 2, outPaint);

    }

    private void drawText(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        Rect rect = new Rect();
        String text = mText + "%";
        if (textPaint == null) {
            return;
        }
        textPaint.getTextBounds(text, 0, text.length(), rect);
        int dx = defWidth / 2 - rect.right / 2;
        int dy = defHeight / 2 - rect.bottom;
        canvas.drawText(text, dx, dy, textPaint);

    }

    private void initOuterPanit() {
        outPaint.setColor(mOutTextColor);
        outPaint.setStrokeCap(Paint.Cap.ROUND);//
        outPaint.setStrokeWidth(mOutSize);
        outPaint.setStyle(Paint.Style.STROKE);
    }

    private void initInnerPanit() {
        innerPaint.setColor(mInnerTextColor);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);//
        innerPaint.setStrokeWidth(mInnerSize);
        innerPaint.setStyle(Paint.Style.STROKE);
    }

    private void initTextPanit() {
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);
    }
}
