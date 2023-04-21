package com.example.myapp.homepage.homedemo.videolist;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.nucarf.base.utils.GlideUtils;

/**
 * Created by yuwenming on 2019/12/27.
 */
class VideoListAdapter extends BaseQuickAdapter<VideoListData.ResponseBean.VideosBean, BaseViewHolder> {
    public VideoListAdapter(int layout) {
        super(layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoListData.ResponseBean.VideosBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_info, item.getPublish_time() + "--" + item.getFmlike() + "--" + item.getFmplaycnt());
        ImageView iv_video_cover = helper.getView(R.id.iv_video_cover);
        GlideUtils.load(mContext, item.getPoster(), iv_video_cover);
    }
}
