package com.example.myapp.bean;

import java.io.Serializable;

/**
 * Created by ${yuwenming} on 2018/8/16.
 */

public class GiftBean implements Serializable {
    private String present_id;//礼物id
    private int bean_number;//礼物需要的趣豆数量
    private int buy_type;//1钻石  2金币  3现金  4复活卡  5趣豆
    private String present_name;// 礼物名称
    private String pic_url;
    private int popularity;// 人气值
    private int hot_flag; //1不热门 2热门
    public int group; //礼物数量
    private int size_flag; //1小礼物 2大礼物
    private String animation_url;// scga动画url
    private String tip_message;//送礼物提示
    public String userName;//

    public GiftBean() {
    }

    public GiftBean(String present_name, String userName) {
        this.present_name = present_name;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTip_message() {
        return tip_message;
    }

    public void setTip_message(String tip_message) {
        this.tip_message = tip_message;
    }

    public int getSize_flag() {
        return size_flag;
    }

    public void setSize_flag(int size_flag) {
        this.size_flag = size_flag;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getHot_flag() {
        return hot_flag;
    }

    public void setHot_flag(int hot_flag) {
        this.hot_flag = hot_flag;
    }

    public String getAnimation_url() {
        return animation_url;
    }

    public void setAnimation_url(String animation_url) {
        this.animation_url = animation_url;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public String getPresent_id() {
        return present_id;
    }

    public void setPresent_id(String present_id) {
        this.present_id = present_id;
    }

    public int getBean_number() {
        return bean_number;
    }

    public void setBean_number(int bean_number) {
        this.bean_number = bean_number;
    }

    public String getPresent_name() {
        return present_name;
    }

    public void setPresent_name(String present_name) {
        this.present_name = present_name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
