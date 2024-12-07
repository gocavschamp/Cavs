package com.example.myapp.activity.speak;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.GlideUtils;

import java.util.Locale;

import kotlin.random.Random;
import uk.co.senab.photoview.PhotoView;

/**
 * @Description TODO
 * @Author
 * @Date 2020/10/20 16:20
 */
public class LearnAbcAdapter extends BaseQuickAdapter<LearnAbc,BaseViewHolder> {

    public LearnAbcAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LearnAbc item) {
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.nameLittle,item.getName().toLowerCase(Locale.ROOT));
        PhotoView pic = helper.getView(R.id.pic);
        pic.setZoomable(false);
//        if (item.getSrc()==0){
//            GlideUtils.loadCenterInside(mContext,item.getUrl(),pic);
//        }else {
//            GlideUtils.load(mContext,item.getSrc(),pic);
//        }
        helper.setVisible(R.id.play, Random.Default.nextBoolean());
        helper.addOnClickListener(R.id.play);

    }
}
