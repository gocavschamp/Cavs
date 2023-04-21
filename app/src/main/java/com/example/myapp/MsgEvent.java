package com.example.myapp;

public class MsgEvent {
    private String key;
    private String value;
    private String type;
    public static final String TYPE_WEB = "type_web";

    public MsgEvent(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MsgEvent(String key, String value) {
        this.key = key;
        this.value = value;

    }
}
