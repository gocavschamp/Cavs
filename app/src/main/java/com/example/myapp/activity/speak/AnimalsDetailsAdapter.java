package com.example.myapp.activity.speak;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.photoview.PhotoView;
import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.GlideUtils;

/**
 * @Description TODO
 * @Author
 * @Date 2020/10/20 16:20
 */
public class AnimalsDetailsAdapter extends BaseQuickAdapter<Animals,BaseViewHolder> {

    public AnimalsDetailsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Animals item) {
        helper.setText(R.id.name,item.getName());
        PhotoView pic = helper.getView(R.id.pic);
        GlideUtils.load(mContext,item.getSrc(),pic);
        helper.setVisible(R.id.play,!item.getVideo().isEmpty());
        helper.addOnClickListener(R.id.play);

    }
}
