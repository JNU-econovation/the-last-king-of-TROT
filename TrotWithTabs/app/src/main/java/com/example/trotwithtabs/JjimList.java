package com.example.trotwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

public class JjimList implements Parcelable {
    public String title;
    public String Id;
    public String thumbnail;

    public JjimList(String title, String id, String thumbnail) {
        this.title = title;
        Id = id;
        this.thumbnail = thumbnail;
    }

    protected JjimList(Parcel in) {
        title = in.readString();
        Id = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<JjimList> CREATOR = new Creator<JjimList>() {
        @Override
        public JjimList createFromParcel(Parcel in) {
            return new JjimList(in);
        }

        @Override
        public JjimList[] newArray(int size) {
            return new JjimList[size];
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

