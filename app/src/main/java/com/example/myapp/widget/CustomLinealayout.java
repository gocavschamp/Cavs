package com.example.myapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by yuwenming on 2019/11/6.
 */
public class CustomLinealayout extends LinearLayout {
    private Paint paint = new Paint();
    public CustomLinealayout(Context context) {
        super(context);
    }

    public CustomLinealayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinealayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomLinealayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setDither(true);
        int width = getWidth();
        int height = getHeight();
        canvas.drawCircle(width/3,height/3,30,paint);
        canvas.drawCircle(width/2,height/3,40,paint);
        canvas.drawCircle(width/3.5f,height/3.5f,50,paint);

    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
