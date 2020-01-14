package com.example.myapp.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapp.R;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.NumberUtils;

import me.jessyan.autosize.utils.AutoSizeUtils;

import static com.nucarf.base.utils.ScreenUtil.disableHardwareAccelerated;

/**
 * Created by yuwenming on 2020/1/10.
 */
public class CustomProgressBar extends View {

    private Paint recPaint;
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
    private float starValue = -90f;

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
        disableHardwareAccelerated(this);
        outPaint = new Paint();
        innerPaint = new Paint();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        recPaint = new Paint();
        recPaint.setAntiAlias(true);
        innerPaint.setAntiAlias(true);
        outPaint.setAntiAlias(true);
        initTextPanit();
        initInnerPanit();
        initOuterPanit();
        mText = NumberUtils.totalMoney(mText + "");
        sweepValue = (Float.parseFloat(mText) / 100f) * 360f;
        resetValue(sweepValue);
    }

    private void init() {

        resetValue(Float.parseFloat(mText));

    }

//    @Override
//    protected void onWindowVisibilityChanged(int visibility) {
//        super.onWindowVisibilityChanged(visibility);
//        if (visibility == VISIBLE) {
//        }
//
//    }
//
//    @Override
//    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
//        super.onVisibilityChanged(changedView, visibility);
//        if (visibility == VISIBLE) {
//            init();
//        }
//    }
//
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
////        init();
//    }

    public void resetValue(float value) {
        this.animate().cancel();
//        float aFloat = value;
//        mText = NumberUtils.totalMoney(aFloat + "");
//        sweepValue = (aFloat / 100f) * 360f;
//        invalidate();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, value);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float aFloat = (float) animation.getAnimatedValue();
                mText = NumberUtils.totalMoney(aFloat + "");
                sweepValue = (aFloat / 100f) * 360f;
//                LogUtils.e("angle", sweepValue + "");
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
        setMeasuredDimension(defWidth * 2, defHeight * 2);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setMeasuredDimension(defWidth * 2, defHeight * 2);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        setMeasuredDimension(defWidth * 2, defHeight * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        drawText(canvas);
        drawOuter(canvas);
        drawInner(sweepValue);
        drawTextBox(canvas);
    }

    private void drawTextBox(Canvas canvas) {
        Rect rectText = new Rect();
        String text = mText + "%";
        textPaint.getTextBounds(text, 0, text.length(), rectText);
        double x = Math.sin(Math.PI * sweepValue / 180) * (defHeight / 2f);
        double y = Math.cos(Math.PI * sweepValue / 180) * (defWidth / 2f);

        if (0 < sweepValue && sweepValue <= 90) {
            int dx = defWidth + (int) x;
            int dy = defHeight - (int) y;
            recPaint.setColor(getContext().getResources().getColor(R.color.color_ff4081));
            canvas.drawRect(dx, dy - textPaint.getFontSpacing()+rectText.bottom, dx + rectText.right, dy+rectText.bottom, recPaint);
            canvas.drawText(text, dx, dy, textPaint);
            canvas.drawLine(defWidth, defHeight, dx, dy, textPaint);
        }
        if (90 < sweepValue && sweepValue <= 180) {
            int dx = defWidth + (int) x;
            int dy = defHeight - (int) y;
            recPaint.setColor(getContext().getResources().getColor(R.color.colorPrimary));
            canvas.drawRect(dx, dy, dx + rectText.right, dy + textPaint.getFontSpacing(), recPaint);
            canvas.drawText(text, dx, dy + mTextSize, textPaint);
            canvas.drawLine(defWidth, defHeight, dx, dy, textPaint);

        }
        if (180 < sweepValue && sweepValue <= 270) {
            int dx = defWidth + (int) x;
            int dy = defHeight - (int) y;
            recPaint.setColor(getContext().getResources().getColor(R.color.result_point_color));
            canvas.drawRect(dx - rectText.right, dy + textPaint.getFontSpacing()+rectText.bottom, dx, dy+rectText.bottom, recPaint);
            canvas.drawText(text, dx - rectText.right, dy + textPaint.getFontSpacing(), textPaint);
            canvas.drawLine(defWidth, defHeight, dx, dy, textPaint);

        }
        if (270 < sweepValue && sweepValue <= 360) {
            int dx = defWidth + (int) x;
            int dy = defHeight - (int) y;
            recPaint.setColor(getContext().getResources().getColor(R.color.color_f5a623));
            canvas.drawRect(dx - rectText.right, dy - textPaint.getFontSpacing()+rectText.bottom, dx, dy+rectText.bottom, recPaint);
            canvas.drawText(text, dx - rectText.right, dy, textPaint);
            canvas.drawLine(defWidth, defHeight, dx, dy, textPaint);
        }
//        textPaint.getTextBounds(text, 0, text.length(), rect);
//        int dx = defWidth / 2 - rect.right / 2;
//        int dy = defHeight / 2 + rect.bottom;
//        canvas.drawText(text, dx, dy, textPaint);
//        Path path = new Path();
//        path.moveTo();
//        canvas.drawPath(path, innerPaint);

    }

    private void drawInner(float sweepAngle) {
        RectF rect = new RectF(defWidth / 2 + mInnerSize / 2, defHeight / 2 + mInnerSize / 2, defWidth + defWidth / 2 - mInnerSize / 2, defHeight + defHeight / 2 - mInnerSize / 2);
        float start = 0;
        if (0 < sweepValue  ) {
            innerPaint.setColor(getContext().getResources().getColor(R.color.color_ff4081));
            canvas.drawArc(rect, start - 90, sweepAngle-start, false, innerPaint);

        }
        if (90 < sweepValue  ) {
            innerPaint.setColor(getContext().getResources().getColor(R.color.colorPrimary));
            start = 90;
            canvas.drawArc(rect, start - 90, sweepAngle-start, false, innerPaint);

        }
        if (180 < sweepValue ) {
            innerPaint.setColor(getContext().getResources().getColor(R.color.result_point_color));
            start = 180;
            canvas.drawArc(rect, start - 90, sweepAngle-start, false, innerPaint);


        }
        if (270 < sweepValue ) {
            innerPaint.setColor(getContext().getResources().getColor(R.color.color_f5a623));
            start = 270;
            canvas.drawArc(rect, start - 90, sweepAngle-start, false, innerPaint);

        }
//        canvas.drawArc(rect, start - 90, sweepAngle-start, false, innerPaint);
//        starValue+=sweepValue;
    }

    private void drawOuter(Canvas canvas) {
        canvas.drawCircle(defWidth, defHeight, defWidth / 2 - mOutSize / 2, outPaint);

    }

    private void drawText(Canvas canvas) {
        Rect rect = new Rect();
        String text = mText + "%";
        textPaint.getTextBounds(text, 0, text.length(), rect);
        int dx = defWidth - rect.right / 2;
        int dy = defHeight + rect.bottom;
        canvas.drawText(text, dx, dy, textPaint);

    }

    private void initOuterPanit() {
        outPaint.setColor(mOutTextColor);
        outPaint.setStrokeCap(Paint.Cap.ROUND);//
        outPaint.setStrokeWidth(mOutSize);
        outPaint.setStyle(Paint.Style.STROKE);
        recPaint.setColor(mOutTextColor);
        recPaint.setStrokeCap(Paint.Cap.ROUND);//
        recPaint.setStrokeWidth(mOutSize);
    }

    private void initInnerPanit() {
        innerPaint.setColor(mInnerTextColor);
        innerPaint.setStrokeCap(Paint.Cap.BUTT);//
        innerPaint.setStrokeWidth(mInnerSize);
        innerPaint.setStyle(Paint.Style.STROKE);
    }

    private void initTextPanit() {
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);
    }
}
