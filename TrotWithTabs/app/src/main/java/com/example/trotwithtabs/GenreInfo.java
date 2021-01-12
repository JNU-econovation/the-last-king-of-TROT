package com.example.trotwithtabs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class GenreInfo implements Parcelable {
    String title;
    String Id;
    String thumbnail;

    public GenreInfo(String title, String id, String thumbnail) {
        this.title = title;
        Id = id;
        this.thumbnail = thumbnail;
    }

    protected GenreInfo(Parcel in) {
        title = in.readString();
        Id = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<GenreInfo> CREATOR = new Creator<GenreInfo>() {
        @Override
        public GenreInfo createFromParcel(Parcel in) {
            return new GenreInfo(in);
        }

        @Override
        public GenreInfo[] newArray(int size) {
            return new GenreInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getId() {
        return Id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "GenreInfo{" +
                "title='" + title + '\'' +
                ", Id='" + Id + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

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
