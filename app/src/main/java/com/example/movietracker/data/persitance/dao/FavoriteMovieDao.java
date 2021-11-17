package com.example.movietracker.data.persitance.dao;

import java.util.List;

public interface FavoriteMovieDao {
    void insertFavorite(String movieId);

    void deleteFavorite(String movieId);

    List<String> getAllFavoriteMovieIds();
}
