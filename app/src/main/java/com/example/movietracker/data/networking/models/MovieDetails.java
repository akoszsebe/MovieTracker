package com.example.movietracker.data.networking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails implements Parcelable {

    private String overview;
    private List<Genre> genres;
    private String title;
    private int runtime;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private float voteAverage;

    public MovieDetails() {
    }

    public String getOverview() {
        return overview;
    }

    public MovieDetails setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public MovieDetails setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieDetails setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getRuntime() {
        return runtime;
    }

    public MovieDetails setRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public MovieDetails setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public MovieDetails setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public MovieDetails setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public MovieDetails setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeTypedList(this.genres);
        dest.writeString(this.title);
        dest.writeInt(this.runtime);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.voteAverage);
    }

    protected MovieDetails(Parcel in) {
        this.overview = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.title = in.readString();
        this.runtime = in.readInt();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.voteAverage = in.readFloat();
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}
