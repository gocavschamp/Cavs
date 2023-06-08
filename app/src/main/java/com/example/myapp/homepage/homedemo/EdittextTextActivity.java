package com.example.myapp.homepage.homedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moonlight.flyvideo.R;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.ui.BaseActivityWithTitle;

public class EdittextTextActivity extends BaseActivityWithTitle {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext_text);
        setTitle("edit  test");
    }

    @Override
    protected void initData() {

    }
}
