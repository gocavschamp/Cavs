package com.example.myapp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.moonlight.flyvideo.BuildConfig;
import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.LogUtils;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * Created by yuwenming on 2019/11/6.
 */
@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {
    private Paint paint = new Paint();
    private boolean topleft;
    private boolean topright;
    private boolean bottomleft;
    private boolean bottomright;
    private int radius;
    private int mWidth;
    private int mHeight;
    private Paint paint1;

    public CustomImageView(Context context) {
        super(context, null, 0);
    }


    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);

    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.animate().rotationX(720).setInterpolator(new AnticipateInterpolator()).scaleX(1.1f).setDuration(5000).start();
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
            topleft = typedArray.getBoolean(R.styleable.CustomImageView_top_left, false);
            topright = typedArray.getBoolean(R.styleable.CustomImageView_top_right, false);
            bottomleft = typedArray.getBoolean(R.styleable.CustomImageView_bottom_left, false);
            bottomright = typedArray.getBoolean(R.styleable.CustomImageView_bottom_right, false);
            radius = typedArray.getDimensionPixelSize(R.styleable.CustomImageView_radius, 0);
            radius = AutoSizeUtils.dp2px(context, radius);
            typedArray.recycle();
        }
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.RED);
        paint1.setStrokeWidth(5f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.e("tag---", "" + mWidth + "-" + mHeight);

        Path path1 = new Path();
        if (Math.min(mWidth, mHeight) > radius) {
            Path path =new Path();
            if (topleft) {
                path.moveTo(0, radius);
                path.quadTo(0, 0, radius, 0);
                path.lineTo(mWidth - radius, 0);
            } else {
                path.moveTo(0, 0);
                path.lineTo(mWidth - radius, 0);
            }
            if (topright) {
                path.quadTo(mWidth, 0, mWidth, radius);
                path.lineTo(mWidth, mHeight - radius);
            } else {
                path.lineTo(mWidth, 0);
                path.lineTo(mWidth, mHeight - radius);
            }
            if (bottomright) {
                path.quadTo(mWidth, mHeight, mWidth - radius, mHeight);
                path.lineTo(radius, mHeight);
            } else {
                path.lineTo(mWidth, mHeight);
                path.lineTo(radius, mHeight);
            }
            if (bottomleft) {
                path.quadTo(0, mHeight, 0, mHeight - radius);
                path.lineTo(0, mHeight - radius);
            } else {
                path.lineTo(0, mHeight);
                path.lineTo(0, radius);
            }
//            path.addRoundRect(,,,,);
            canvas.clipPath(path);

        }
        super.onDraw(canvas);
        if (BuildConfig.DEBUG) {
            this.paint.setColor(Color.RED);
            this.paint.setAntiAlias(true);
            this.paint.setDither(true);
            this.paint.setTextSize(35);
            this.paint.setStyle(Paint.Style.FILL);
            int height = mHeight;
            int width = mWidth;
            String text = "尺寸：" + width + "*" + height;
            Rect rect = new Rect();
            this.paint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, width / 2 - rect.right / 2, height / 2 + rect.bottom, this.paint);
            path1.moveTo(0,0);
            path1.lineTo(mWidth,mHeight);
            canvas.drawPath(path1, paint1);
        }

    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(35);
        paint.setStyle(Paint.Style.FILL);
        int height = mHeight;
        int width = mWidth;
        String text = "LACKERS";
        Rect rect = new Rect(0, 0, 200, 100);
//        paint.getTextBounds(text, 0, text.length(), rect);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.RED);
        canvas.drawText(text, 20, 60, paint);
        super.onDrawForeground(canvas);
    }
}
