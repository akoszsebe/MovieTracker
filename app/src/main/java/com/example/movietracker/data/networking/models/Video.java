package com.example.movietracker.data.networking.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    private String id;
    private String key;
    private String site;
    private String type;
    private String name;
    private int progress;

    public Video() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() { return progress; }

    public void setProgress(int progress) { this.progress = progress; }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(site);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeInt(progress);
    }

    protected Video(Parcel in) {
        id = in.readString();
        key = in.readString();
        site = in.readString();
        type = in.readString();
        name = in.readString();
        progress = in.readInt();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };


}
