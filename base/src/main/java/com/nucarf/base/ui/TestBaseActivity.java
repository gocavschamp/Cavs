package com.nucarf.base.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nucarf.base.R;

public class TestBaseActivity extends BaseActivityWithTitle {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_base);
    }

    @Override
    protected void initData() {
        setTitle("base test activity");

    }
}