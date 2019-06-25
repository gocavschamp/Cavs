package com.example.myapp.homepage.homedemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class BottomSheetAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public BottomSheetAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }

}
