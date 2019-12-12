package com.example.myapp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapp.BuildConfig;

/**
 * Created by yuwenming on 2019/11/6.
 */
@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {
    private Paint paint = new Paint();

    public CustomImageView(Context context) {
        super(context);
        init(context);
    }



    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
//        setWillNotDraw(true);
    }
    private void init(Context context) {
        this.animate().rotationX(720).setInterpolator(new AnticipateInterpolator()).scaleX(1.1f).setDuration(5000).start();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (BuildConfig.DEBUG) {
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setTextSize(35);
            paint.setStyle(Paint.Style.FILL);
            int height = getHeight();
            int width = getWidth();
            String text = "尺寸：" + width + "*" + height;
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, width / 2 - rect.right / 2, height / 2 + rect.bottom, paint);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(35);
        paint.setStyle(Paint.Style.FILL);
        int height = getHeight();
        int width = getWidth();
        String text = "LACKERS";
        Rect rect = new Rect(0,0,200,100);
//        paint.getTextBounds(text, 0, text.length(), rect);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.RED);
        canvas.drawText(text, 20, 60, paint);
        super.onDrawForeground(canvas);
    }
}
