package com.example.myapp.homepage;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;

/**
 * @auther yuwenming
 * @createTime：2023/5/17 18:04
 * @desc:
 **/
public class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_info, item);

    }
}
