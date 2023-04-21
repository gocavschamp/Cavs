package com.example.myapp.homepage.mine.bean;

import java.io.Serializable;

public class OtherListBean implements Serializable {
    /**
     * other_id : 1
     * name : 距离最近
     */

    private String other_id;
    private String name;
    private boolean isChoice = false;

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }
    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
