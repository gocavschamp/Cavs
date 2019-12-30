package com.tools.ad;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


import com.tools.ad.bean.Advertisement;

/**
 * Created tt on 2016/12/26.
 */
public class ADLayout extends FrameLayout implements View.OnClickListener {

    private final String TAG = " ADLayout";

    private FrameLayout root;
    private ImageView adImg;
    private ImageView close;
    private ArrayList<Advertisement> advertisements;
    private Advertisement advertisement;
    private ADListener listener;

    public ADLayout(Context context) {
        super(context);
        initView();
    }

    public ADLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ADLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ADLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.ad_layout, this);
        root = (FrameLayout) findViewById(R.id.root);
        adImg = (ImageView) root.findViewById(R.id.adImg);
        close = (ImageView) root.findViewById(R.id.close);
        adImg.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.adImg) {
            if (advertisement != null && listener != null) {
                listener.onClickAD(advertisement.getUrl());
            }
        }
        if (i == R.id.close) {
            if (listener != null)
                listener.onCloseAD();
            hide();
        }
    }

    public void setADListener(ADListener listener) {
        this.listener = listener;
    }

    public void setAdvertisements(ArrayList<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public void setIsLive() {
        if (advertisements != null && advertisements.size() > 0) {
            show(advertisements.get(0));
        }
    }

    public void setCurrentPlayTime(String currentTime) {
        if (advertisement != null) {
            if (currentTime.equals(advertisement.getPivot().getEnd_time())) {
                hide();
            }
            if (advertisements != null && advertisements.size() > 0) {
                for (Advertisement advertisement : advertisements) {
                    if (advertisement.getPivot() != null && currentTime.equals(advertisement.getPivot().getBegin_time())) {
                        show(advertisement);
                    }
                }
            }
        } else if (advertisements != null && advertisements.size() > 0) {
            for (Advertisement advertisement : advertisements) {
                if (advertisement.getPivot() != null && currentTime.equals(advertisement.getPivot().getBegin_time())) {
                    show(advertisement);
                }
            }
        }
    }

    public void releaseData() {
        Log.e(TAG, "releaseAD");
        root.setVisibility(View.GONE);
        if (advertisements != null) {
            advertisements.clear();
            advertisements = null;
        }
        advertisement = null;
    }

    public void finishADLayout() {
        releaseData();
        listener = null;
    }

    public void show(Advertisement advertisement) {
        Log.e(TAG, "showAD");
        this.advertisement = advertisement;
        Glide.with(getContext()).load(advertisement.getCover()).placeholder(R.mipmap.default_43).error(R.mipmap.default_43).centerCrop().into(adImg);
        root.setVisibility(View.VISIBLE);
    }

    public void hide() {
        Log.e(TAG, "hideAD");
        root.setVisibility(View.GONE);
        advertisement = null;
    }
}
