package com.example.myapp.homepage.homedemo.multitem;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.example.myapp.bean.ArticleBean;
import com.example.myapp.bean.StringBean;
import com.example.myapp.bean.Student;

import java.util.List;

/**
 * Created by yuwenming on 2019/12/5.
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleItemQuickAdapter(List<MultipleItem> data) {
        super(data);
        /**
         * addItemType中的type种类，必须和接收到的种类数目一模一样。
         * 种类：有几种type，就要写几个addItemType。少写或者错写，会直接报错！！！
         *  (android.content.res.Resources$NotFoundException: Resource ID *******)
         *  时刻注意！！！！
         *  例如：这个type有10种！。（type=1,2,3...10）你就得写
         *     addItemType(1, R.layout.item_test_one);
         *     addItemType(2, R.layout.item_test_two);
         *     addItemType(3, R.layout.item_test_two);
         *     ....
         *     addItemType(10, R.layout.item_test_two);
         *     漏写一个就会报错！！！血的教训啊！！！！
         */
        try {
            addItemType(MultipleItem.ONE, R.layout.mult_text_view);
            addItemType(MultipleItem.TWO, R.layout.mult_image_view);
            addItemType(MultipleItem.THREE, R.layout.mult_more_view);
        } catch (Exception ex) {
//请忽略这个try {}catch啊！这个加不加都行，。。我当时是为了捕获异常。。。结果没有捕获到
            Log.d("yang_multi", ex.getMessage());
        }


    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.ONE:
                StringBean stringBean = (StringBean) item;
                helper.setText(R.id.tvTitle,stringBean.getName());

                break;
            case MultipleItem.TWO:

                ArticleBean articleBean = (ArticleBean) item;
                helper.setText(R.id.tv_content1,articleBean.getName());

                break;
            case MultipleItem.THREE:
                Student student = (Student) item;
                helper.setText(R.id.tv_content,student.getName());
                break;
        }

    }
}
