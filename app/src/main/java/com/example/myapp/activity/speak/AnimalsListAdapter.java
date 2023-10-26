package com.example.myapp.activity.speak;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.bean.StringBean;
import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.GlideUtils;

/**
 * @Description TODO
 * @Author
 * @Date 2020/10/20 16:20
 */
public class AnimalsListAdapter extends BaseQuickAdapter<Animals,BaseViewHolder> {

    public AnimalsListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Animals item) {
        helper.setText(R.id.name,item.getName());
        ImageView pic = helper.getView(R.id.pic);
        GlideUtils.load(mContext,item.getSrc(),pic);
        helper.addOnClickListener(R.id.speak);

    }
}
