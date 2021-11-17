package com.example.movietracker.ui.movies;

import android.util.Log;

import com.example.movietracker.base.BaseMvpPresenter;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.data.repository.MovieRepository;
import com.example.movietracker.data.networking.models.GenreMovies;
import com.example.movietracker.data.repository.ObservableRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.movietracker.utils.Constants.BOOKMARKED;

public class MoviesMvpPresenter extends BaseMvpPresenter<MoviesMvpView> {
    private MovieRepository movieRepository;
    private List<GenreMovies> genreMovies = new ArrayList<>();
    private List<Genre> filteredGenres;
    private GenreMovies bookmarks;
    private List<String> favoriteMovieIds = new ArrayList<>();

    public MoviesMvpPresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        movieRepository.registerRepositoryObserver(changeListener);
    }

    private final MovieRepository.RepositoryDataChangeListener changeListener = (action, item) -> {
        if (action == ObservableRepository.DbChangeAction.UPDATE_VIDEO_PROGRESS) {
            Movie movie = (Movie) item;
            if (bookmarks != null) {
                Movie movieFromList = bookmarks.getMovies().stream().filter(x -> x.getId().equals(movie.getId())).findFirst().orElse(null);
                if (movieFromList == null) {
                    bookmarks.getMovies().add(movie);
                    view.refreshBookmarks(bookmarks);
                }
            } else {
                bookmarks = new GenreMovies(BOOKMARKED, new ArrayList<>());
                bookmarks.getMovies().add(movie);
                List<GenreMovies> sorted = genreMovies.stream().sorted(Comparator.comparing(GenreMovies::getGenreName)).collect(Collectors.toList());
                sorted.add(0, bookmarks);
                view.addBookmarks(sorted);
            }
        } else if (action == ObservableRepository.DbChangeAction.INSERT_FAVORITE_MOVIE) {
            String movieId = (String) item;
            changeFavoriteFlag(movieId, true);
        } else if (action == ObservableRepository.DbChangeAction.DELETE_FAVORITE_MOVIE) {
            String movieId = (String) item;
            changeFavoriteFlag(movieId, false);
        }
    };

    private void changeFavoriteFlag(String movieId, boolean isFavorite) {
        for (GenreMovies genreMovie : genreMovies) {
            genreMovie.getMovies().forEach(movie -> {
                if (movie.getId().equals(movieId)) {
                    movie.setFavorite(isFavorite);
                }
            });
        }
        bookmarks.getMovies().forEach(movie -> {
            if (movie.getId().equals(movieId)) {
                movie.setFavorite(isFavorite);
            }
        });
    }

    public void init(List<Genre> allGenres){
        loadMoviesByGenres(allGenres);
        disposables.add(movieRepository.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favorites -> {
                    favoriteMovieIds = favorites;
                    loadMoviesByGenres(allGenres);
                    loadBookmarkedMovies();
                }));
    }

    public void findAndScrollToPosition(Movie lastNavigatedMovie, List<GenreMovies> currentList) {
        GenreMovies g = currentList.stream().filter(x -> x.getMovies().indexOf(lastNavigatedMovie) > 0).collect(Collectors.toList()).stream().findFirst().orElse(null);
        if (g != null) {
            g.setScrollIndex(g.getMovies().indexOf(lastNavigatedMovie));
        }
    }

    public void loadGenres() {
        String selectedGenres = filteredGenres.stream().map(Genre::getName).collect(Collectors.joining(" | "));
        view.setTitle(selectedGenres);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        movieRepository.unregisterRepositoryObserver(changeListener);
    }

    private void loadMoviesByGenres(List<Genre> allGenres) {
        filteredGenres = allGenres.stream().filter(Genre::isSelected).sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
        disposables.add(movieRepository.getVideosByGenres(filteredGenres)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    for (Movie movie : response.getMovies()) {
                        List<String> genreNames = new ArrayList<>();
                        for (int genreId : movie.getGenreIds()) {
                            Optional<Genre> first = allGenres.stream().filter(g -> genreId == g.getId()).findFirst();
                            first.ifPresent(g -> genreNames.add(g.getName()));
                        }
                        movie.setGenreNames(genreNames);
                        if (favoriteMovieIds.contains(movie.getId())) {
                            movie.setFavorite(true);
                        }
                    }
                    return response;
                })
                .subscribe(response -> {
                            genreMovies.add(response);
                            List<GenreMovies> sorted = genreMovies.stream().sorted(Comparator.comparing(GenreMovies::getGenreName)).collect(Collectors.toList());
                            if (bookmarks != null) {
                                sorted.add(0, bookmarks);
                            }
                            view.showGenreMovies(sorted);
                        },
                        e -> Log.d("e", "Error fetching popular movies:" + e.getMessage())
                )
        );

    }

    private void loadBookmarkedMovies() {
        disposables.add(movieRepository.loadBookmarkedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    for (Movie movie : response.getMovies()) {
                        if (favoriteMovieIds.contains(movie.getId())) {
                            movie.setFavorite(true);
                        }
                    }
                    return response;
                })
                .subscribe(response -> {
                            if (!response.getMovies().isEmpty()) {
                                bookmarks = response;
                                List<GenreMovies> sorted = genreMovies.stream().sorted(Comparator.comparing(GenreMovies::getGenreName)).collect(Collectors.toList());
                                sorted.add(0, bookmarks);
                                view.showGenreMovies(sorted);
                            }
                        },
                        e -> Log.d("e", "Error fetching bookmarked movies:" + e.getMessage())
                )
        );
    }

}
