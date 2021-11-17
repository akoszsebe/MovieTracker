package com.example.movietracker.data.networking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    private String id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genre_ids")
    private int[] genreIds;

    private List<String> genreNames;

    private String title;

    @SerializedName("vote_average")
    private float voteAverage;

    private boolean favorite;

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public Movie setId(String id) {
        this.id = id;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Movie setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public Movie setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public Movie setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
