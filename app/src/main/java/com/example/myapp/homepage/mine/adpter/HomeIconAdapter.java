package com.example.myapp.homepage.mine.adpter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;
import com.example.myapp.homepage.mine.bean.MineIconBean;

public class HomeIconAdapter extends BaseQuickAdapter<MineIconBean, BaseViewHolder> {
    private int itemViewWidth;
    private int recyclerViewWidth; // recyclerView的宽度

    public void setRecyclerViewWidth(int recyclerViewWidth) {
        this.recyclerViewWidth = recyclerViewWidth;
    }

    public HomeIconAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineIconBean item) {
        // 重新计算item的宽
        if (itemViewWidth == 0 && recyclerViewWidth > 0) {
            itemViewWidth = recyclerViewWidth / 4;
            ViewGroup.LayoutParams itemViewLayoutParams = helper.itemView.getLayoutParams();
            itemViewLayoutParams.width = itemViewWidth;
            helper.itemView.setLayoutParams(itemViewLayoutParams);
        }

        View view = helper.getView(R.id.rl_content);
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setGone(R.id.tv_tip, false);
        if (item.getTip_icon() && !TextUtils.isEmpty(item.getIcon_info())) {
            ImageView imageView = helper.getView(R.id.iv_icon);
//            GlideUtils.load(mContext, item.getIcon_info(), imageView);
            RequestOptions requestOptions = new RequestOptions();
            Glide.with(mContext)
                    .load(item.getIcon_info())
                    .apply(requestOptions)
                    .into(imageView);
        } else {
            helper.setImageResource(R.id.iv_icon, item.getIcon());
        }
    }

}
