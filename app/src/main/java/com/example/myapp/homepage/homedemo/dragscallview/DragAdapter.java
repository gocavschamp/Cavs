package com.example.myapp.homepage.homedemo.dragscallview;


import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;

import java.util.List;

public class DragAdapter extends BaseItemDraggableAdapter<DragBean, BaseViewHolder> {
    public DragAdapter(int layout, List<DragBean> data) {
        super(layout,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DragBean item) {
        holder.setText(R.id.text1,"这是第几个位置" + holder.getLayoutPosition());
        holder.setText(R.id.text2, "这是第几个数据" + holder.getLayoutPosition());
        if(item.getType() ==0) {
            holder.setBackgroundColor(R.id.ll_content,mContext.getResources().getColor(R.color.white));
        }else {
            holder.setBackgroundColor(R.id.ll_content,mContext.getResources().getColor(R.color.black_20));
        }
//        holder.setTextColor(android.R.id.text2, R.color.colorTextAssistant);
    }
}
