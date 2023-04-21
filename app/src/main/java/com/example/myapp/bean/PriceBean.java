package com.example.myapp.bean;

public class PriceBean {
    /**
     * fuel_no : 2001
     * price : 597
     * unit : 元/升
     * fuel_name : 0# 柴油
     */

    private String fuel_no;
    private String price;
    private String unit;
    private String fuel_name;

    public String getFuel_no() {
        return fuel_no;
    }

    public void setFuel_no(String fuel_no) {
        this.fuel_no = fuel_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFuel_name() {
        return fuel_name;
    }

    public void setFuel_name(String fuel_name) {
        this.fuel_name = fuel_name;
    }
}
