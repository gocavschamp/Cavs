package com.example.myapp.homepage.homedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nucarf.base.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetBihaverActivity extends BaseActivity {

    //    @BindView(R.id.map)
//    MapView map;
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_bihaver);
//        map.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        map.onDestroy();
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.recycleview));
        bottomSheetBehavior.setBottomSheetCallback(new MyBottomSheetCallback());
        recycleview.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

        BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(R.layout.default_normal_layout);
        recycleview.setAdapter(bottomSheetAdapter);
        data = new ArrayList();
        getData();
        bottomSheetAdapter.setNewData(data);
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            data.add("data-------" + i);
        }
    }

    class MyBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View view, int i) {

        }

        @Override
        public void onSlide(@NonNull View view, float v) {

        }
    }
}
