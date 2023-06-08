package com.example.myapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;
import com.example.myapp.bean.StringBean;

import java.util.List;

/**
 * @Description TODO
 * @Author
 * @Date 2020/10/20 16:20
 */
public class WebStationAdapter extends BaseQuickAdapter<StringBean,BaseViewHolder> {

    public WebStationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StringBean item) {
        helper.setText(R.id.tv_name,item.getName());

    }
}
