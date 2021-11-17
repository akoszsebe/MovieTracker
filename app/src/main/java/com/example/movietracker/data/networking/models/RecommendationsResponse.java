package com.example.movietracker.data.networking.models;

import java.util.List;

public class RecommendationsResponse {
    public List<YoutubeItem> getItems() {
        return items;
    }

    public void setItems(List<YoutubeItem> items) {
        this.items = items;
    }

    private List<YoutubeItem> items;
}
