package com.example.movietracker.data.networking.models;

import java.util.List;

public class GenreMovies {
    private int scrollIndex = -1;
    private String genreName;
    private List<Movie> movies;

    public GenreMovies(String genreName, List<Movie> movies) {
        this.genreName = genreName;
        this.movies = movies;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getScrollIndex() { return scrollIndex; }

    public void setScrollIndex(int scrollIndex) { this.scrollIndex = scrollIndex; }
}
