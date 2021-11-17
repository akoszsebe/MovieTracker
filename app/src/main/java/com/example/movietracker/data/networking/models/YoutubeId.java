package com.example.movietracker.data.networking.models;

import com.google.gson.annotations.SerializedName;

public class YoutubeId {
    private String kind;
    @SerializedName("videoId")
    private String videoId;

    public String getKind() {
        return kind;
    }

    public void setKind(String value) {
        this.kind = value;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String value) {
        this.videoId = value;
    }
}
