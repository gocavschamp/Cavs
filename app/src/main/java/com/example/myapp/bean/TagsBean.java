package com.example.myapp.bean;

import com.example.myapp.homepage.homedemo.multitem.MultipleItem;

/**
 * Created by hzy on 2019/1/24
 *
 * @author hzy
 * */
public class TagsBean extends MultipleItem {

    /**
     * name : 公众号
     * url : /wxarticle/list/414/1
     */

    private String name;
    private String url;

    @Override
    public int getItemType() {
        return MultipleItem.THREE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
