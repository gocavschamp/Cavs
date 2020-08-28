package com.example.myapp.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ComPassView extends View {
    private  Context mContext;
    private Canvas mCanvas;

    public ComPassView(Context context) {
        this(context,null);
    }

    public ComPassView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ComPassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext  = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ComPassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas =canvas;
//        mCanvas.drawArc();
    }
}
