package com.example.movietracker.data.networking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosResponse implements Parcelable {
    private int id;
    @SerializedName("results")
    private List<Video> videos;

    public VideosResponse() {
    }

    protected VideosResponse(Parcel in) {
        id = in.readInt();
        videos = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<VideosResponse> CREATOR = new Creator<VideosResponse>() {
        @Override
        public VideosResponse createFromParcel(Parcel in) {
            return new VideosResponse(in);
        }

        @Override
        public VideosResponse[] newArray(int size) {
            return new VideosResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(videos);
    }
}
