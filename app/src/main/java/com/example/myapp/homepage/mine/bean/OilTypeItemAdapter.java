package com.example.myapp.homepage.mine.bean;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;

class OilTypeItemAdapter extends BaseQuickAdapter<FuelsBean, BaseViewHolder> {
    public OilTypeItemAdapter(int layout) {
        super(layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, FuelsBean item) {
        helper.setText(R.id.tv_oil_name, TextUtils.isEmpty(item.getName()) ? item.getFuel_name() + "" : item.getName());
        TextView tv_oil_name = helper.getView(R.id.tv_oil_name);
        tv_oil_name.setSelected(item.isChoice());
    }
}
