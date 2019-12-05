package com.example.myapp.bean;

import com.example.myapp.homepage.homedemo.multitem.MultipleItem;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 *
 * @author hzy
 * */
public class ArticleBean extends MultipleItem {

    private int curPage;
    private int offset;
    private String name;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    @Override
    public int getItemType() {
        return MultipleItem.TWO;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<ArticleListBean> datas;

    public List<ArticleListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleListBean> datas) {
        this.datas = datas;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public String getName() {
        return name;
    }
}
