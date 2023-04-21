package com.example.myapp.bean;

import com.example.myapp.homepage.homedemo.multitem.MultipleItem;

import java.io.Serializable;

public class StringBean extends MultipleItem implements Serializable {
    private String pay_code;
    private String company_name;
    private String code;
    private boolean isChoice = false;
    private String name;
    private int icon;
    private String value;
    private String id;
    private String type;
    private String token;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }


    @Override
    public int getItemType() {
        return MultipleItem.ONE;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {

        this.pay_code = pay_code;
    }
}
