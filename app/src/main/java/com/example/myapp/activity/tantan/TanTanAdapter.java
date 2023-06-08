package com.example.myapp.activity.tantan;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moonlight.flyvideo.R;
import com.nucarf.base.utils.GlideUtils;
import com.nucarf.base.widget.LabelsView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${yuwenming} on 2018/10/31.
 */
public class TanTanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public TanTanAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        AutoUtils.auto(helper.getConvertView());
//        helper.setText(R.id.tv_name, item.getNick_name())
//                .setText(R.id.tv_age, "" + item.getAge())
//                .setText(R.id.tv_constellation, "" + item.getConstellation())
//                .setText(R.id.tv_city, "" + item.getCity())
//                .setGone(R.id.tv_city, !TextUtils.isEmpty(item.getCity()))
//                .setText(R.id.tv_pic_count, "" + item.getPic_number())
//                .setText(R.id.tv_like_count, "" + item.getPraise_number() + "赞");
//        ImageView iv_pic = helper.getView(R.id.iv_pic);
//        GlideUtils.load(mContext, item.getPic_url(), iv_pic);
//        TextView tv_age = helper.getView(R.id.tv_age);
//        TextView tv_constellation = helper.getView(R.id.tv_constellation);
//        GradientDrawable background = (GradientDrawable) tv_age.getBackground();
//        GradientDrawable tv_constellation_background = (GradientDrawable) tv_constellation.getBackground();
//        background.setColor(Color.parseColor("#FFFF55A9"));
//        tv_constellation_background.setColor(Color.parseColor("#FFFF55A9"));
//        Drawable opponent_drawable = mContext.getResources().getDrawable(R.drawable.icon_woman_little);
//        if (item.getSex() == 1) {
//            background.setColor(Color.parseColor("#7741FF"));
//            tv_constellation_background.setColor(Color.parseColor("#7741FF"));
//            opponent_drawable = mContext.getResources().getDrawable(R.drawable.icon_man_little);
//        }
//        opponent_drawable = BitmapUtil.getTextViewDrawable(BitmapUtil.tintDrawable(opponent_drawable, Color.WHITE));
//        // param 左上右下
//        tv_age.setCompoundDrawables(opponent_drawable, null, null, null);
//        LabelsView labelsView = helper.getView(R.id.lv_labels);
//        List<String> labelstr = item.getCharacters();
//        if (null != labelstr) {
//            List<int[]> colors = new ArrayList<>();
//            for (int i = 0; i < labelstr.size(); i++) {
//                String colStr = GlobalVar.label_colors[i];
//                int[] col = new int[]{Color.parseColor(colStr), 0};
//                colors.add(col);
//            }
//            labelsView.setLabels(labelstr, colors);
//        }
    }


}
