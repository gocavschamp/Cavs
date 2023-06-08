package com.example.myapp.homepage.mine.bean;

import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.ScreenUtil;

public class OilBrandListAdapter extends BaseQuickAdapter<BrandsListBean, BaseViewHolder> {
    private int dp36;
    private int tabPosition = 0;

    public OilBrandListAdapter(int layout) {
        super(layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandsListBean item) {
        TextView tv_content = helper.getView(R.id.tv_content);
        tv_content.setText(item.getName());
        tv_content.setSelected(item.isChoice());
        changeView(tv_content);
    }

    // 设置油品筛选布局样式
    private void changeView(TextView tv_content) {
        if (tabPosition == 2) {
            if (tv_content.getTag() != null) {
                return;
            }
            tv_content.setTag(true);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv_content.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            }
            layoutParams.setMargins(0, 0, 0, 0);
            if (dp36 == 0) {
                dp36 = ScreenUtil.dip2px( 36);
            }
            layoutParams.height = dp36;
            tv_content.setGravity(Gravity.CENTER);
            tv_content.setBackgroundResource(R.drawable.selector_pickerview_btn);
            tv_content.setLayoutParams(layoutParams);
        }
    }

    public void setTabPosition(int tabPosition) {
        if (this.tabPosition != tabPosition) {
            this.tabPosition = tabPosition;
            notifyDataSetChanged();
        }
    }

}
