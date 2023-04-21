package com.example.myapp.activity.webViewer;


import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;


/**
 * Created by ${yuwenming} on 2018/10/31.
 */
public class LabelAdapter extends BaseQuickAdapter<WebHistory, BaseViewHolder> {


    public LabelAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, WebHistory item) {
        helper.setText(R.id.tvTitle, item.getTitle() +" | " +item.getNote())
                .setText(R.id.btnCollect, 1 == item.getIscollect() ? "已收藏" : "收藏");
        helper.addOnClickListener(R.id.ivDelete,R.id.btnCollect);
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
