package com.example.trotwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

public class GenreInfoList implements Parcelable {
    public String title;
    public String Id;
    public String thumbnail;

    public GenreInfoList(String title, String id, String thumbnail) {
        this.title = title;
        Id = id;
        this.thumbnail = thumbnail;
    }

    protected GenreInfoList(Parcel in) {
        title = in.readString();
        Id = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<GenreInfoList> CREATOR = new Creator<GenreInfoList>() {
        @Override
        public GenreInfoList createFromParcel(Parcel in) {
            return new GenreInfoList(in);
        }

        @Override
        public GenreInfoList[] newArray(int size) {
            return new GenreInfoList[size];
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
