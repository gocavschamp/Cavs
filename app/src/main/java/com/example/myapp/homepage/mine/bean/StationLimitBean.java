package com.example.myapp.homepage.mine.bean;

import java.io.Serializable;
import java.util.List;

public class StationLimitBean implements Serializable {

    private List<FuelsListBean> fuels_list;
    private List<BrandsListBean> brands_list;
    private List<BrandsListBean> other_list;
    private List<DistanceBean> distance_list;
    private List<BrandsListBean> station_type_list;
    private List<FuelsBean> default_fuels_list;//默认油品
    private List<String> tags;//热词tag

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<DistanceBean> getDistance_list() {
        return distance_list;
    }

    public void setDistance_list(List<DistanceBean> distance_list) {
        this.distance_list = distance_list;
    }

    public List<BrandsListBean> getStation_type_list() {
        return station_type_list;
    }

    public void setStation_type_list(List<BrandsListBean> station_type_list) {
        this.station_type_list = station_type_list;
    }

    public List<FuelsListBean> getFuels_list() {
        return fuels_list;
    }

    public void setFuels_list(List<FuelsListBean> fuels_list) {
        this.fuels_list = fuels_list;
    }

    public List<BrandsListBean> getBrands_list() {
        return brands_list;
    }

    public void setBrands_list(List<BrandsListBean> brands_list) {
        this.brands_list = brands_list;
    }

    public List<BrandsListBean> getOther_list() {
        return other_list;
    }

    public void setOther_list(List<BrandsListBean> other_list) {
        this.other_list = other_list;
    }

    public List<FuelsBean> getDefault_fuels_list() {
        return default_fuels_list;
    }

    public void setDefault_fuels_list(List<FuelsBean> default_fuels_list) {
        this.default_fuels_list = default_fuels_list;
    }
}
