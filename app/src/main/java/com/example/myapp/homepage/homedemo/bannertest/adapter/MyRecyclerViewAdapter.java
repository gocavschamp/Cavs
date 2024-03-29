package com.example.myapp.homepage.homedemo.bannertest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moonlight.flyvideo.R;
import com.example.myapp.homepage.homedemo.bannertest.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.util.BannerUtils;

public class MyRecyclerViewAdapter  {
//    private Context context;
//
//    public MyRecyclerViewAdapter(Context context) {
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType== R.layout.item) {
//            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
//        }else{
//            return new MyBannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner, parent, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof MyViewHolder) {
//            ((MyViewHolder) holder).cardView.setBackgroundColor(Color.parseColor(DataBean.getRandColor()));
//        }else if (holder instanceof MyBannerViewHolder){
//            Banner banner=((MyBannerViewHolder) holder).banner;
//            banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));
//            banner.setBannerRound(BannerUtils.dp2px(5));
//            banner.setIndicator(new RoundLinesIndicator(context));
//            banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
//        }
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position%2==0){
//            return R.layout.item;
//        }else{
//            return R.layout.banner;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 10;
//    }
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        public CardView cardView;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            cardView = itemView.findViewById(R.id.card_view);
//        }
//    }
//
//    class MyBannerViewHolder extends RecyclerView.ViewHolder {
//        public Banner banner;
//
//        public MyBannerViewHolder(@NonNull View itemView) {
//            super(itemView);
//            banner = itemView.findViewById(R.id.banner);
//        }
//    }
}