package com.example.myapp.bean;


import java.util.List;

public class SearchWayBean  {
    private List<LineBean>line;
    private List<NewPointsBean>points;
    private String collected_id;//0 .1


    public String getCollected_id() {
        return collected_id;
    }

    public void setCollected_id(String collected_id) {
        this.collected_id = collected_id;
    }

    public List<LineBean> getLine() {
        return line;
    }

    public void setLine(List<LineBean> line) {
        this.line = line;
    }

    public List<NewPointsBean> getPoints() {
        return points;
    }

    public void setPoints(List<NewPointsBean> points) {
        this.points = points;
    }

}
