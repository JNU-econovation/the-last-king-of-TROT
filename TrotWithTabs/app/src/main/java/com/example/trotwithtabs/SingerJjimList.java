package com.example.trotwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

public class SingerJjimList implements Parcelable {
    public String name;
    //public int state;

    public SingerJjimList(String name) {
        this.name = name;
        //this.state = state;
    }

    protected SingerJjimList(Parcel in) {
        name = in.readString();
        //state = in.readInt();
    }

    public static final Creator<SingerJjimList> CREATOR = new Creator<SingerJjimList>() {
        @Override
        public SingerJjimList createFromParcel(Parcel in) {
            return new SingerJjimList(in);
        }

        @Override
        public SingerJjimList[] newArray(int size) {
            return new SingerJjimList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        //dest.writeInt(state);
    }
}

