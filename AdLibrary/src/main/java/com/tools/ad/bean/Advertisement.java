package com.tools.ad.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create time on on 2016/12/14.
 */
public class Advertisement implements Parcelable {

    private String id;
    private String name;
    private String cover;
    private String url;
    private ADPivot pivot;

    protected Advertisement(Parcel in) {
        id = in.readString();
        name = in.readString();
        cover = in.readString();
        url = in.readString();
        pivot = in.readParcelable(ADPivot.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(cover);
        dest.writeString(url);
        dest.writeParcelable(pivot, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Advertisement> CREATOR = new Creator<Advertisement>() {
        @Override
        public Advertisement createFromParcel(Parcel in) {
            return new Advertisement(in);
        }

        @Override
        public Advertisement[] newArray(int size) {
            return new Advertisement[size];
        }
    };

    public ADPivot getPivot() {
        return pivot;
    }

    public void setPivot(ADPivot pivot) {
        this.pivot = pivot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
