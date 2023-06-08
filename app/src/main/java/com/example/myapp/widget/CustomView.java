package com.example.myapp.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.LogUtils;

import java.util.Locale;

/**
 * Created by yuwenming on 2019/11/4.
 */
public class CustomView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint line_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint circle_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint text_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint a_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint b_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint c_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint d_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private Matrix mBitmapMatrix;
    private RectF mBitmapRectF;
    private boolean canDrag;
    PointF lastPoint = new PointF(0, 0);
    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        // 调整图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 960/2;
        options.outHeight = 800/2;

        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.lakers_test, options);
        mBitmapRectF = new RectF(0,0,bitmap.getWidth(), bitmap.getHeight());
        mBitmapMatrix = new Matrix();
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lakers_test);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // ▼ 判断是否是第一个手指 && 是否包含在图片区域内
                if (event.getPointerId(event.getActionIndex()) == 0 && mBitmapRectF.contains((int)event.getX(), (int)event.getY())){
                    canDrag = true;
                    lastPoint.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // ▼ 判断是否是第一个手指
                if (event.getPointerId(event.getActionIndex()) == 0){
                    canDrag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果存在第一个手指，且这个手指的落点在图片区域内
                if (canDrag) {
                    // ▼ 注意 getX 和 getY
                    int index = event.findPointerIndex(0);
                    // Log.i(TAG, "index="+index);
                    mBitmapMatrix.postTranslate(event.getX(index)-lastPoint.x, event.getY(index)-lastPoint.y);
                    lastPoint.set(event.getX(index), event.getY(index));

                    mBitmapRectF = new RectF(0,0,bitmap.getWidth(), bitmap.getHeight());
                    mBitmapMatrix.mapRect(mBitmapRectF);

                    invalidate();
                }
                break;
        }

        return true;
    }
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.main_color));
        float[] line_points = {0, 0, 50, 100, 200, 300};
        line_paint.setColor(Color.RED);
        line_paint.setAntiAlias(true);
        line_paint.setStrokeWidth(2f);
        canvas.drawLines(line_points, line_paint);
        circle_paint.setColor(Color.GREEN);
        canvas.drawCircle(166, 166, 66, circle_paint);
        canvas.drawArc(100, 100, 200, 266, 65, 145, true, line_paint);
        text_paint.setColor(Color.YELLOW);
        text_paint.setAntiAlias(true);
        text_paint.setTextSize(45);
        RadialGradient shader = new RadialGradient(400, 400, 170, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        circle_paint.setShader(shader);
        canvas.drawCircle(400, 400, 170, circle_paint);
//        text_paint.setShader(shader);
        String text = "湖人总冠军";
        text_paint.setShadowLayer(15, 5, 5, Color.GREEN);
        canvas.drawText("湖人总冠军", 255, 300, text_paint);
        canvas.translate(0, 100);
        text_paint.setFakeBoldText(true);
        canvas.drawText("湖人总冠军", 255, 300, text_paint);
        canvas.translate(0, 100);
        text_paint.setUnderlineText(true);
        text_paint.setTextSkewX(-0.5f);
        text_paint.setLetterSpacing(0.6f);
        canvas.drawText("湖人总冠军", 255, 300, text_paint);
        text_paint.setTextLocale(Locale.JAPAN);
        canvas.drawText("湖人总冠军", 255, 300 + text_paint.getFontSpacing(), text_paint);
        text_paint.setTextLocale(Locale.ENGLISH);
        Rect rect = new Rect();
        text_paint.getTextBounds(text, 0, text.length(), rect);
        LogUtils.e("bounds--" + rect.left + "-" + rect.top + "-" + rect.right + "-" + rect.bottom);
        rect.left += 255;
        rect.top += 300 + text_paint.getFontSpacing() * 2;
        rect.right += 255;
        rect.bottom += 300 + text_paint.getFontSpacing() * 2;
        circle_paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, circle_paint);
        canvas.drawText("湖人总冠军", 55, 300 + text_paint.getFontSpacing() * 2, text_paint);
        float measureText = text_paint.measureText(text);
        canvas.drawLine(255, 300 + text_paint.getFontSpacing() * 2, 255 + measureText, 300 + text_paint.getFontSpacing() * 2, circle_paint);
//
//        canvas.save();
//        canvas.clipRect(25, 300, 25 + bitmap.getWidth(), 300 + bitmap.getHeight());
        canvas.drawBitmap(bitmap, mBitmapMatrix, text_paint);
//        canvas.restore();
//        canvas.save();
//        canvas.rotate(45,75,400);
//        canvas.scale(1.1f,1.1f);
////        canvas.skew(0,0.5f);
//        canvas.drawBitmap(bitmap, 125, 400, text_paint);
//        canvas.restore();
//        Camera camera = new Camera();

//        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bitmap, "rotationY", 0.0f, 90.0f, 0.0F);
//        objectAnimator.setDuration(2000);
//        objectAnimator.setRepeatCount(Animation.INFINITE);
//        objectAnimator.setRepeatMode(Animation.RESTART);
//        objectAnimator.start();


    }
}
