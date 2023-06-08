package com.example.myapp.homepage.mine.bean;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;
import com.example.myapp.bean.StringBean;

public class CityListAdapter extends BaseQuickAdapter<StringBean, BaseViewHolder> {
    private int type = 2;

    public CityListAdapter(int layout) {
        super(layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, StringBean item) {
        TextView tv_content = helper.getView(R.id.tv_content);
        RelativeLayout rl_content = helper.getView(R.id.rl_content);
        tv_content.setText(item.getName());
        tv_content.setSelected(item.isChoice());
        if (type == 1) {
            rl_content.setBackgroundColor(mContext.getResources().getColor(R.color.color_f5f5f5));
            if(item.isChoice()) {
                rl_content.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }else {
                rl_content.setBackgroundColor(mContext.getResources().getColor(R.color.color_f5f5f5));

            }
        } else {
            rl_content.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }

    public void setType(int type) {
        this.type = type;
    }
}
