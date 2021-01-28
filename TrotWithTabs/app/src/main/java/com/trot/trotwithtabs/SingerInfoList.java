package com.trot.trotwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

public class SingerInfoList implements Parcelable {
    public String title;
    public String Id;
    public String thumbnail;

    public SingerInfoList(String title, String id, String thumbnail) {
        this.title = title;
        Id = id;
        this.thumbnail = thumbnail;
    }

    protected SingerInfoList(Parcel in) {
        title = in.readString();
        Id = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<SingerInfoList> CREATOR = new Creator<SingerInfoList>() {
        @Override
        public SingerInfoList createFromParcel(Parcel in) {
            return new SingerInfoList(in);
        }

        @Override
        public SingerInfoList[] newArray(int size) {
            return new SingerInfoList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(Id);
        dest.writeString(thumbnail);
    }
}
