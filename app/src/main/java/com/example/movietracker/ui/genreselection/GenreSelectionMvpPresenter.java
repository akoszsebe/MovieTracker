package com.example.movietracker.ui.genreselection;

import android.util.Log;

import com.example.movietracker.base.BaseMvpPresenter;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.GenresResponse;
import com.example.movietracker.data.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GenreSelectionMvpPresenter extends BaseMvpPresenter<GenreSelectionMvpView> {

    private MovieRepository movieRepository;
    private List<Genre> genreList;

    public GenreSelectionMvpPresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        genreList = new ArrayList<>();
    }

    void loadGenres() {
        disposables.add(movieRepository.getMovieGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    genreList = response;
                    view.showGenres(genreList);
                }, e -> Log.d("e", "Error fetching genres:" + e.getMessage())));
    }

    public void clickedGenre(Genre genre) {
        int index = genreList.indexOf(genre);
        if (index != -1) {
            if (index + 1 == genreList.size()) {
                disposables.add(movieRepository.updateGenres(genreList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            view.navigateToMoviesPage( new GenresResponse(genreList));
                        }, e -> Log.d("e", "Error updating genres:" + e.getMessage())));
            } else {
                genre.setSelected(!genre.isSelected());
                view.elementUpdated(index);
            }
        }
    }
}
