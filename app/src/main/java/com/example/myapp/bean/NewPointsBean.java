package com.example.myapp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

public class NewPointsBean  {

    /**
     * name : 槐店服务区中石化
     * lng : 114.933127
     * sa_id : 3
     * lat : 31.95958
     * code : 411522
     * child : [{"lng":"114.933127","lat":"31.95958",
     * "name":"槐店服务区中石化加油站（东）",
     * "si_id":"5","sa_id":"3",
     * "code":"411522",
     * "price":[
     * {"fuel_no":"2001",
     * "price":"597","
     * unit":"元/升",
     * "fuel_name":"0# 柴油"}]
     * },{"lng":"114.930950233311","lat":"31.958159965726","name":"槐店服务区中石化加油站（西）","si_id":"6","sa_id":"3","code":"411522","price":[{"fuel_no":"2001","price":"608","unit":"元/升","fuel_name":"0# 柴油"}]}]
     */

    private String name;
    private String lng;
    private String sa_id;
    private String lat;
    private String code;
    private String distance;//距我距离
    private String distances;//距线的距离
    private String station_type;// 1高速2线下3撬装站0为无
    private String toStartDistance;
    private String in_line;
    private String is_discount;//1 有优惠 0无优惠
    private String channel;//是否是和悦油站 3为和悦

    private List<StationChildBean> child;

    public String getIs_discount() {
        return is_discount;
    }

    public void setIs_discount(String is_discount) {
        this.is_discount = is_discount;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDistances() {
        return distances;
    }

    public void setDistances(String distances) {
        this.distances = distances;
    }

    public String getStation_type() {
        return station_type;
    }

    public void setStation_type(String station_type) {
        this.station_type = station_type;
    }

    public String getToStartDistance() {
        return toStartDistance;
    }

    public void setToStartDistance(String toStartDistance) {
        this.toStartDistance = toStartDistance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIn_line() {
        return in_line;
    }

    public void setIn_line(String in_line) {
        this.in_line = in_line;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSa_id() {
        return sa_id;
    }

    public void setSa_id(String sa_id) {
        this.sa_id = sa_id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<StationChildBean> getChild() {
        return child;
    }

    public void setChild(List<StationChildBean> child) {
        this.child = child;
    }


}
