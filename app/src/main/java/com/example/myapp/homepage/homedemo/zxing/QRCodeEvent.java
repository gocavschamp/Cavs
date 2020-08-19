package com.example.myapp.homepage.homedemo.zxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class QRCodeEvent {
    private String text;
    private Bitmap bitmap;

    public QRCodeEvent(String text, Bitmap bitmap) {
        this.text = text;
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
