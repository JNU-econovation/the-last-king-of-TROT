package com.example.trotwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

public class SongJjimList implements Parcelable {
    public String Id;
    public String title;
    public String thumbnail;
    public int state;

    public SongJjimList(String id, String title, String thumbnail, int state) {
        Id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.state = state;
    }

    protected SongJjimList(Parcel in) {
        Id = in.readString();
        title = in.readString();
        thumbnail = in.readString();
        state = in.readInt();
    }

    public static final Creator<SongJjimList> CREATOR = new Creator<SongJjimList>() {
        @Override
        public SongJjimList createFromParcel(Parcel in) {
            return new SongJjimList(in);
        }

        @Override
        public SongJjimList[] newArray(int size) {
            return new SongJjimList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeInt(state);
    }
}

