package com.example.myapp.homepage.homedemo.multitem;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.myapp.bean.ArticleBean;
import com.example.myapp.bean.StringBean;
import com.example.myapp.bean.Student;

import java.util.List;

/**
 * Created by yuwenming on 2019/12/5.
 */
public class MultipleItem implements MultiItemEntity {
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    private int itemType;
    private StringBean stringBean;
    private ArticleBean articleBean;
    private Student student;

    public StringBean getStringBean() {
        return stringBean;
    }

    public void setStringBean(StringBean stringBean) {
        this.stringBean = stringBean;
    }

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
