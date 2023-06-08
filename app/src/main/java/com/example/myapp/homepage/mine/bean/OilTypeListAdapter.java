package com.example.myapp.homepage.mine.bean;

import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;

import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class OilTypeListAdapter extends BaseQuickAdapter<FuelsListBean, BaseViewHolder> {

    private OilTypeItemAdapter oilTypeItemAdapter;
    private int uiType = 0;//0油站列表ui 1加油偏好界面ui 2搜索结果筛选ui

    public OilTypeListAdapter(int layout) {
        super(layout);

    }

    @Override
    protected void convert(BaseViewHolder helper, FuelsListBean item) {
        helper.setText(R.id.tv_oil_name, item.getName());
        RecyclerView recycleview = helper.getView(R.id.recycleview);
        TextView tv_oil_name = helper.getView(R.id.tv_oil_name);
        if (helper.getLayoutPosition() - getHeaderLayoutCount() == 0) {
            if (uiType == 2) {
                tv_oil_name.setPadding(AutoSizeUtils.dp2px(mContext, 15), AutoSizeUtils.dp2px(mContext, 10), 0, 0);
            } else {
                tv_oil_name.setPadding(AutoSizeUtils.dp2px(mContext, 15), AutoSizeUtils.dp2px(mContext, 20), 0, 0);
            }
        } else {
//            tv_oil_name.setPadding(AutoSizeUtils.dp2px(mContext, 15), AutoSizeUtils.dp2px(mContext, 20), 0, 0);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recycleview.getLayoutParams();
        if (uiType == 2) {
            layoutParams.bottomMargin = AutoSizeUtils.dp2px(mContext, 15);
            tv_oil_name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            layoutParams.bottomMargin = AutoSizeUtils.dp2px(mContext, 30);
            tv_oil_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        recycleview.setLayoutParams(layoutParams);
        List<FuelsBean> fuel_info = item.getFuel_info();
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));
        if (uiType == 1) {
            oilTypeItemAdapter = new OilTypeItemAdapter(R.layout.default_oil_type_item);
        } else if (uiType == 2) {
            oilTypeItemAdapter = new OilTypeItemAdapter(R.layout.filter_oil_type_item);
        } else {
            oilTypeItemAdapter = new OilTypeItemAdapter(R.layout.filter_oil_type_item);
        }
        recycleview.setAdapter(oilTypeItemAdapter);
        oilTypeItemAdapter.setNewData(fuel_info);
        oilTypeItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < adapter.getData().size(); i++) {
                    fuel_info.get(i).setChoice(i == position);
                }
                oilTypeItemAdapter.notifyDataSetChanged();
                if (setOnDetailsItemClick != null) {
                    setOnDetailsItemClick.ClickDetailsItem(helper.getLayoutPosition() - getHeaderLayoutCount(), position, fuel_info.get(position));
                }
                for (int i = 0; i < getData().size(); i++) {
                    if (i != (helper.getLayoutPosition() - getHeaderLayoutCount())) {
                        FuelsListBean fuelsListBean = getData().get(i);
                        for (int j = 0; j < fuelsListBean.getFuel_info().size(); j++) {
                            fuelsListBean.getFuel_info().get(j).setChoice(false);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    public void setOnDetailsItemClickListener(SetOnDetailsItemClick setOnDetailsItemClick) {
        this.setOnDetailsItemClick = setOnDetailsItemClick;
    }

    private SetOnDetailsItemClick setOnDetailsItemClick;

    public void setUiType(int uiType) {
        this.uiType = uiType;
    }

    public interface SetOnDetailsItemClick {
        void ClickDetailsItem(int parentPosition, int position, FuelsBean fuelsBean);
    }
}
