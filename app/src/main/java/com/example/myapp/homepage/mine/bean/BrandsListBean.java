package com.example.myapp.homepage.mine.bean;

import java.io.Serializable;

public class BrandsListBean implements Serializable {
    /**
     * name : 全部
     * id : 0
     * other_id : 1
     */

    private String name;
    private String value;
    private int id;
    private String other_id;
    private boolean isChoice = false;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }
}
