package com.example.myapp.homepage.homedemo.dragscallview;


import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;

import java.util.List;

public class DragAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public DragAdapter(int layout, List<String> data) {
        super(layout,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String item) {
        holder.setText(R.id.text1,"这是第几个位置" + holder.getLayoutPosition());
        holder.setText(R.id.text2, "这是第几个数据" + holder.getLayoutPosition());
//        holder.setTextColor(android.R.id.text2, R.color.colorTextAssistant);
    }
}
