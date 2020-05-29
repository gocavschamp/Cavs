package com.nucarf.exoplayerlibrary.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nucarf.exoplayerlibrary.R;
import com.nucarf.exoplayerlibrary.ui.TaobaoGoods;
import com.nucarf.exoplayerlibrary.verplayer.GoodsListener;

import java.util.List;


/**
 * Created by FrancesLiu on 2016/8/3.
 */
public class GoodsAdapter extends RecyclerView.Adapter {
    Context activity;
    List<TaobaoGoods> list;
    GoodsListener listener;


    public GoodsAdapter(Context activity, GoodsListener plistener) {
        this.activity = activity;
        this.listener = plistener;

    }

    public void setList(List<TaobaoGoods> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_goods_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final TaobaoGoods mBean = list.get(position);
        ScreenUtils.setFrameLayoutParams(viewHolder.good_item_root_layout, ScreenUtils.dip2px(activity, 85), ScreenUtils.dip2px(activity, 85));
        ScreenUtils.setRelativeLayoutParams(viewHolder.mGoodsIv, ScreenUtils.dip2px(activity, 80), ScreenUtils.dip2px(activity, 80));
        Glide.with(activity).load(mBean.getGoods_pic()).placeholder(R.mipmap.default_img_11).error(R.mipmap.default_img_11).centerCrop().into(viewHolder.mGoodsIv);
        viewHolder.good_item_root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickGoodsItem(mBean.getGoods_id());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mGoodsIv;
        RelativeLayout good_item_root_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            mGoodsIv = (ImageView) itemView.findViewById(R.id.goods_item_iv);
            good_item_root_layout = (RelativeLayout) itemView.findViewById(R.id.good_item_root_layout);
        }
    }
}
