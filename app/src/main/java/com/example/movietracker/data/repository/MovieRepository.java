package com.example.movietracker.data.repository;

import android.util.Log;

import com.example.movietracker.BuildConfig;
import com.example.movietracker.data.networking.api.TheMovieDbAPI;
import com.example.movietracker.data.networking.models.CreditsResponse;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.GenreMovies;
import com.example.movietracker.data.networking.models.GenresResponse;
import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.data.networking.models.MovieDetails;
import com.example.movietracker.data.networking.models.MovieResponse;
import com.example.movietracker.data.networking.models.ReviewsResponse;
import com.example.movietracker.data.persitance.dao.BookmarkedMovieDao;
import com.example.movietracker.data.persitance.dao.FavoriteMovieDao;
import com.example.movietracker.data.persitance.dao.MovieGenreDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;

import static com.example.movietracker.utils.Constants.BOOKMARKED;

public class MovieRepository extends ObservableRepository {

    private TheMovieDbAPI theMovieDbAPI;
    private MovieGenreDao movieGenreDao;
    private BookmarkedMovieDao bookmarkedMovieDao;
    private FavoriteMovieDao favoriteMovieDao;

    @Inject
    public MovieRepository(TheMovieDbAPI theMovieDbAPI, MovieGenreDao movieGenreDao, BookmarkedMovieDao bookmarkedMovieDao, FavoriteMovieDao favoriteMovieDao) {
        this.theMovieDbAPI = theMovieDbAPI;
        this.movieGenreDao = movieGenreDao;
        this.bookmarkedMovieDao = bookmarkedMovieDao;
        this.favoriteMovieDao = favoriteMovieDao;
    }

    public Single<List<Genre>> getMovieGenres() {
        return Single.create(emitter -> loadMovieGenresFromNetwork(emitter));
    }

    public Observable<GenreMovies> getVideosByGenres(List<Genre> genreIds) {
        return Observable.create(emitter -> loadVideosByGenresFromNetwork(emitter, genreIds));
    }

    public Single<GenreMovies> loadBookmarkedMovies() {
        return Single.create(emitter -> loadBookmarkedMoviesFromDb(emitter));
    }

    public Single<Boolean> updateGenres(List<Genre> genreList) {
        return Single.create(emitter -> updateGenresInDB(emitter, genreList));
    }

    public Single<MovieDetails> getMovieDetails(String movieId) {
        return Single.create(emitter -> loadMovieDetailFromNetwork(emitter, movieId));
    }

    public Single<CreditsResponse> getCredits(String movieId) {
        return Single.create(emitter -> loadCreditsFromNetwork(emitter, movieId));
    }

    public Single<ReviewsResponse> getReviews(String movieId) {
        return Single.create(emitter -> loadReviewsFromNetwork(emitter, movieId));
    }

    public Single<Boolean> bookmarkMovie(Movie movie) {
        return Single.create(emitter -> {
            bookmarkMovieInDB(emitter, movie);
        });
    }

    public Single<Boolean> setFavorite(String movieId) {
        return Single.create(emitter -> {
            saveFavoriteMovieInDB(emitter, movieId);
        });
    }

    public Single<Boolean> deleteFavorite(String movieId) {
        return Single.create(emitter -> {
            deleteFavoriteMovieInDB(emitter, movieId);
        });
    }

    public Single<List<String>> getFavorites() {
        return Single.create(emitter -> loadFavoritesFromDb(emitter));
    }

    private void loadMovieGenresFromNetwork(
            SingleEmitter<List<Genre>> emitter
    ) {
        try {
            List<Genre> genresInDb = movieGenreDao.getAllMovieGenres();
            if (genresInDb.isEmpty()) {
                GenresResponse genresResponse =
                        theMovieDbAPI.getMovieGenres(BuildConfig.TMDB_API_KEY).execute().body();
                if (genresResponse != null) {
                    movieGenreDao.insertMovieGenres(genresResponse.getGenres());
                    emitter.onSuccess(genresResponse.getGenres());
                } else {
                    emitter.onError(new Exception("No data received"));
                }
            } else {
                emitter.onSuccess(genresInDb);
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void loadVideosByGenresFromNetwork(ObservableEmitter<GenreMovies> emitter, List<Genre> genres) {

        for (Genre genre : genres) {
            try {
                MovieResponse movieResponse =
                        theMovieDbAPI.getMoviesByGenre(BuildConfig.TMDB_API_KEY, genre.getId()).execute().body();
                if (movieResponse != null) {
                    emitter.onNext(new GenreMovies(genre.getName(), movieResponse.getResults()));
                }
            } catch (Exception exception) {
                Log.e("MovieRepository", exception.getMessage(), exception);
            }
        }
    }

    private void loadMovieDetailFromNetwork(SingleEmitter<MovieDetails> emitter, String
            movieId) {
        try {
            MovieDetails movieResponse =
                    theMovieDbAPI.getMovieDetails(movieId, BuildConfig.TMDB_API_KEY).execute().body();
            if (movieResponse != null) {
                emitter.onSuccess(movieResponse);
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void loadCreditsFromNetwork(SingleEmitter<CreditsResponse> emitter, String
            movieId) {
        try {
            CreditsResponse creditsResponse =
                    theMovieDbAPI.getCredits(movieId, BuildConfig.TMDB_API_KEY).execute().body();
            if (creditsResponse != null) {
                emitter.onSuccess(creditsResponse);
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void loadReviewsFromNetwork(SingleEmitter<ReviewsResponse> emitter, String
            movieId) {
        try {
            ReviewsResponse reviewsResponse =
                    theMovieDbAPI.getReviews(movieId, BuildConfig.TMDB_API_KEY).execute().body();
            if (reviewsResponse != null) {
                emitter.onSuccess(reviewsResponse);
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void updateGenresInDB(
            SingleEmitter<Boolean> emitter,
            List<Genre> genreList) {
        try {
            movieGenreDao.updateGenres(genreList);
            emitter.onSuccess(true);
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void bookmarkMovieInDB(SingleEmitter<Boolean> emitter, Movie movie) {
        try {
            bookmarkedMovieDao.insertMovie(movie);
            notifyRepositoryChanged(DbChangeAction.UPDATE_VIDEO_PROGRESS, movie);
            emitter.onSuccess(true);
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void loadBookmarkedMoviesFromDb(SingleEmitter<GenreMovies> emitter) {
        try {
            List<Movie> movies =
                    bookmarkedMovieDao.getAllBookmarkedMovies();
            if (movies != null) {
                emitter.onSuccess(new GenreMovies(BOOKMARKED, movies));
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void saveFavoriteMovieInDB(SingleEmitter<Boolean> emitter, String movieId) {
        try {
            favoriteMovieDao.insertFavorite(movieId);
            notifyRepositoryChanged(DbChangeAction.INSERT_FAVORITE_MOVIE, movieId);
            emitter.onSuccess(true);
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void deleteFavoriteMovieInDB(SingleEmitter<Boolean> emitter, String movieId) {
        try {
            favoriteMovieDao.deleteFavorite(movieId);
            notifyRepositoryChanged(DbChangeAction.DELETE_FAVORITE_MOVIE, movieId);
            emitter.onSuccess(true);
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }


    private void loadFavoritesFromDb(SingleEmitter<List<String>> emitter) {
        try {
            List<String> movieIds =
                    favoriteMovieDao.getAllFavoriteMovieIds();
            if (movieIds != null) {
                emitter.onSuccess(movieIds);
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }
}
