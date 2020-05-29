package com.example.myapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by yuwenming on 2020/1/9.
 */
public class RaderCircleView extends View {
    Paint circle_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public RaderCircleView(Context context) {
        super(context);
        init();
    }


    public RaderCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RaderCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int ridius = Math.min(getWidth(), getHeight()) / 2;
        RadialGradient shader = new RadialGradient(400, 400, 170, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        circle_paint.setShader(shader);
        canvas.drawCircle(ridius, ridius, ridius, circle_paint);

    }
}
