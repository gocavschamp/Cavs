package com.example.myapp.bean;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 *
 * @author hzy
 * */
public class ArticleBean {

    private int curPage;
    private int offset;
    private int over;
    private int pageCount;
    private int size;
    private int total;
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

    public int isOver() {
        return over;
    }

    public void setOver(int over) {
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
}
