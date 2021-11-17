package com.example.movietracker.data.networking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    private int page;
    private List<Movie> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public MovieResponse setPage(int page) {
        this.page = page;
        return this;
    }

    public List<Movie> getResults() {
        return results;
    }

    public MovieResponse setResults(List<Movie> results) {
        this.results = results;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public MovieResponse setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public MovieResponse setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public MovieResponse() {
    }
}
