package com.example.myapp.homepage.homedemo.bottomsheet;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.example.myapp.bean.ArticleListBean;
import com.nucarf.base.utils.GlideUtils;

public class BottomSheetAdapter extends BaseQuickAdapter<ArticleListBean,BaseViewHolder> {
    public BottomSheetAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListBean item) {
        helper.setText(R.id.tv_content,item.getTitle())
                .setText(R.id.btn,item.getAuthor());
        ImageView image = helper.getView(R.id.iv_pic);
        GlideUtils.load(mContext,item.getEnvelopePic(),image);

    }

}
