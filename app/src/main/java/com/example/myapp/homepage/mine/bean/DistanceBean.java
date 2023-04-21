package com.example.myapp.homepage.mine.bean;

import java.io.Serializable;

public class DistanceBean implements Serializable {

    /**
     * value : 100000
     * name : 100KM
     */

    private String value;
    private String name;
    private boolean isChoice = false;
    // 油品筛选时候使用
    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
