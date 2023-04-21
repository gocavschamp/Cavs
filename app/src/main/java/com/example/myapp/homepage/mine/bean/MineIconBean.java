package com.example.myapp.homepage.mine.bean;

import java.io.Serializable;

/**
 * Created by ${yuwenming} on 2018/12/5.
 */
public class MineIconBean implements Serializable ,Cloneable{
    private String title;
    private String info;
    private String icon_info;
    private int type;// 1活动 2淘油宝
    private int icon;
    private boolean tip_icon = false; // 默认false
    private boolean show = true; // 默认true

    public MineIconBean() {}

    public MineIconBean(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    @Override
    public MineIconBean clone() throws CloneNotSupportedException {
        return (MineIconBean) super.clone();
    }

    public String getTitle() {
        return title;
    }

    public MineIconBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public MineIconBean setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getIcon_info() {
        return icon_info;
    }

    public MineIconBean setIcon_info(String icon_info) {
        this.icon_info = icon_info;
        return this;
    }

    public int getType() {
        return type;
    }

    public MineIconBean setType(int type) {
        this.type = type;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public MineIconBean setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public boolean getTip_icon() {
        return tip_icon;
    }

    public MineIconBean setTip_icon(boolean tip_icon) {
        this.tip_icon = tip_icon;
        return this;
    }

    public boolean isShow() {
        return show;
    }

    public MineIconBean setShow(boolean show) {
        this.show = show;
        return this;
    }
}
