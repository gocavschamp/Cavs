package com.example.myapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LineBean  {
    private String distance;
    private String polyline;//polyline=116.407082,39.904205;116.407074,39.904484;116.40667,39.904461;116.4067,39.903893;116.4067,39.903465;


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

}
