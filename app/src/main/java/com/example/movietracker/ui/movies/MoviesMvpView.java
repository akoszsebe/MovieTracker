package com.example.movietracker.ui.movies;

import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.data.networking.models.GenreMovies;

import java.util.List;

public interface MoviesMvpView {
    void showGenreMovies(List<GenreMovies> genreMovies);

    void setTitle(String text);

    void navigateToMovieDetailsPage(Movie movie);

    void refreshBookmarks(GenreMovies bookmarks);

    void addBookmarks(List<GenreMovies> genreMovies);
}
