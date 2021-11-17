package com.example.movietracker.data.networking.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GenresResponse implements Parcelable {

    private List<Genre> genres;

    public GenresResponse(List<Genre> genreList) {
        genres = genreList;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.genres);
    }

    public GenresResponse(Parcel in) {
        this.genres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Creator<GenresResponse> CREATOR = new Creator<GenresResponse>() {
        @Override
        public GenresResponse createFromParcel(Parcel source) {
            return new GenresResponse(source);
        }

        @Override
        public GenresResponse[] newArray(int size) {
            return new GenresResponse[size];
        }
    };
}
