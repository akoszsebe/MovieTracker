package com.example.movietracker.data.networking.models;

public class YoutubeItem {
    private  YoutubeId id;
    private Snippet snippet;

    public YoutubeId getId() {
        return id;
    }

    public void setId(YoutubeId id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}
