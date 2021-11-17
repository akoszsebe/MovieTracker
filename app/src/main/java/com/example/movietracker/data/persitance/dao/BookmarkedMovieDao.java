package com.example.movietracker.data.persitance.dao;

import com.example.movietracker.data.networking.models.Movie;

import java.util.List;

public interface BookmarkedMovieDao {
    void insertMovie(Movie movie);

    void deleteMovie(Movie movie);

    List<Movie> getAllBookmarkedMovies();
}
