package com.example.myapp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

public class StationChildBean  {
    /**
     * lng : 114.933127
     * lat : 31.95958
     * name : 槐店服务区中石化加油站（东）
     * si_id : 5
     * sa_id : 3
     * code : 411522
     * price : [{"fuel_no":"2001","price":"597","unit":"元/升","fuel_name":"0# 柴油"}]
     */

    private String lng;
    private String lat;
    private String name;
    private String si_id;
    private String sa_id;
    private String code;
    private List<PriceBean> price;

    public StationChildBean() {
    }


    public String getLng() {
        if(TextUtils.isEmpty(lng)) {
            lng = "0";
        }
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        if(TextUtils.isEmpty(lat)) {
            lat = "0";
        }
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSi_id() {
        return si_id;
    }

    public void setSi_id(String si_id) {
        this.si_id = si_id;
    }

    public String getSa_id() {
        return sa_id;
    }

    public void setSa_id(String sa_id) {
        this.sa_id = sa_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<PriceBean> getPrice() {
        return price;
    }

    public void setPrice(List<PriceBean> price) {
        this.price = price;
    }

}
