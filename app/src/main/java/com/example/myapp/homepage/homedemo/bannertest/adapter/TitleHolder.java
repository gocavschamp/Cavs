package com.example.myapp.homepage.homedemo.bannertest.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moonlight.flyvideo.R;


public class TitleHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public TitleHolder(@NonNull View view) {
        super(view);
        title = view.findViewById(R.id.bannerTitle);
    }
}
