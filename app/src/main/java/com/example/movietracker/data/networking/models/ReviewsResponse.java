package com.example.movietracker.data.networking.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {
    private int id;

    @SerializedName("results")
    private List<Review> reviews;

    public ReviewsResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
