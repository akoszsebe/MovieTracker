package com.example.movietracker.ui.genreselection;

import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.GenresResponse;

import java.util.List;

public interface GenreSelectionMvpView {
    void showGenres(List<Genre> response);

    void navigateToMoviesPage(GenresResponse genres);

    void elementUpdated(int index);
}
