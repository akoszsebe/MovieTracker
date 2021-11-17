package com.example.movietracker.data.persitance.dao;

import com.example.movietracker.data.networking.models.Genre;

import java.util.List;

public interface MovieGenreDao {
    void insertGenre(Genre genre);

    void updateGenre(Genre genre);

    List<Genre> getAllMovieGenres();

    void insertMovieGenres(List<Genre> genres);

    void updateGenres(List<Genre> genres);
}
