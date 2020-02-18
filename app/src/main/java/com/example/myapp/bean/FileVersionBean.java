package com.example.myapp.bean;

public class FileVersionBean {
    /**
     * v : 2
     * cv : 2.6.0
     */

    private String v;
    private int is_native = 1;
    private long id;
    private String cv;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIs_native() {
        return is_native;
    }

    public void setIs_native(int is_native) {
        this.is_native = is_native;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
