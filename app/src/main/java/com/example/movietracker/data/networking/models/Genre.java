package com.example.movietracker.data.networking.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {

    private int id;
    private String name;
    private boolean selected;

    public Genre() {
    }

    public Genre(int id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public Genre setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    protected Genre(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}

