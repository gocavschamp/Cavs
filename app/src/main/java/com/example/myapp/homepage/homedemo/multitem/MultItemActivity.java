package com.example.myapp.homepage.homedemo.multitem;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapp.R;
import com.example.myapp.bean.ArticleBean;
import com.example.myapp.bean.StringBean;
import com.example.myapp.bean.Student;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.ui.BaseActivityWithTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultItemActivity extends BaseActivityWithTitle {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private LinearLayoutManager linearLayoutManager;
    private MultipleItemQuickAdapter multipleItemQuickAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_item);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recycleview.setLayoutManager(linearLayoutManager);
        List data = new ArrayList<MultipleItem>();
        for (int i = 0; i < 4; i++) {
            StringBean stringBean = new StringBean();
            stringBean.setItemType(MultipleItem.ONE);
            stringBean.setName("创建的stringbean"+i);
            data.add(stringBean);
        }
        for (int i = 0; i < 2; i++) {
            ArticleBean articleBean = new ArticleBean();
            articleBean.setItemType(MultipleItem.TWO);
            articleBean.setName("创建的---articleBean"+i);
            data.add(articleBean);
        }
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setItemType(MultipleItem.THREE);
            student.setName("创建的---Student"+i);
            data.add(student);
        }

        multipleItemQuickAdapter = new MultipleItemQuickAdapter(data);
        recycleview.setAdapter(multipleItemQuickAdapter);
        multipleItemQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if(firstCompletelyVisibleItemPosition>=0) {
                    MultipleItem multipleItem = multipleItemQuickAdapter.getData().get(firstCompletelyVisibleItemPosition);
                    if(firstCompletelyVisibleItemPosition<4) {
                        StringBean multipleItem1 = (StringBean) multipleItem;
                        titlelayout.setTitleText(multipleItem1.getName());
                    }else if(firstCompletelyVisibleItemPosition<6) {
                        ArticleBean multipleItem1 = (ArticleBean) multipleItem;
                        titlelayout.setTitleText(multipleItem1.getName());
                    }else {
                        Student multipleItem1 = (Student) multipleItem;
                        titlelayout.setTitleText(multipleItem1.getName());
                    }

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
