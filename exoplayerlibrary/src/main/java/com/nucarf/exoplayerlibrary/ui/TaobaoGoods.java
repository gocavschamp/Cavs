package com.nucarf.exoplayerlibrary.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang on 2017/5/10.
 */

public class TaobaoGoods implements Parcelable {
    private String id;
    private String type;
    private String object_id;
    private String goods_id;
    private String goods_name;
    private String goods_pic;
    private String goods_price;
    private String goods_detail_url;

    protected TaobaoGoods(Parcel in) {
        id = in.readString();
        type = in.readString();
        object_id = in.readString();
        goods_id = in.readString();
        goods_name = in.readString();
        goods_pic = in.readString();
        goods_price = in.readString();
        goods_detail_url = in.readString();
    }

    public static final Creator<TaobaoGoods> CREATOR = new Creator<TaobaoGoods>() {
        @Override
        public TaobaoGoods createFromParcel(Parcel in) {
            return new TaobaoGoods(in);
        }

        @Override
        public TaobaoGoods[] newArray(int size) {
            return new TaobaoGoods[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_detail_url() {
        return goods_detail_url;
    }

    public void setGoods_detail_url(String goods_detail_url) {
        this.goods_detail_url = goods_detail_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(type);
        parcel.writeString(object_id);
        parcel.writeString(goods_id);
        parcel.writeString(goods_name);
        parcel.writeString(goods_pic);
        parcel.writeString(goods_price);
        parcel.writeString(goods_detail_url);
    }
}
