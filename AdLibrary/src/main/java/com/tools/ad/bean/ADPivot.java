package com.tools.ad.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create time on on 2016/12/14.
 */
public class ADPivot implements Parcelable {

    private String video_id;
    private String advertisement_id;
    private String begin_time;
    private String end_time;

    protected ADPivot(Parcel in) {
        video_id = in.readString();
        advertisement_id = in.readString();
        begin_time = in.readString();
        end_time = in.readString();
    }

    public static final Creator<ADPivot> CREATOR = new Creator<ADPivot>() {
        @Override
        public ADPivot createFromParcel(Parcel in) {
            return new ADPivot(in);
        }

        @Override
        public ADPivot[] newArray(int size) {
            return new ADPivot[size];
        }
    };

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getAdvertisement_id() {
        return advertisement_id;
    }

    public void setAdvertisement_id(String advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(video_id);
        parcel.writeString(advertisement_id);
        parcel.writeString(begin_time);
        parcel.writeString(end_time);
    }
}
