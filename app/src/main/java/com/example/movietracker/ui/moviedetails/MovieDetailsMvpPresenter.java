package com.example.movietracker.ui.moviedetails;

import android.util.Log;

import com.example.movietracker.base.BaseMvpPresenter;
import com.example.movietracker.data.networking.models.CreditsResponse;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.data.networking.models.MovieDetails;
import com.example.movietracker.data.networking.models.Review;
import com.example.movietracker.data.networking.models.ReviewsResponse;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.data.networking.models.VideosResponse;
import com.example.movietracker.data.persitance.entity.DbVideoProgress;
import com.example.movietracker.data.repository.MovieRepository;
import com.example.movietracker.data.repository.ObservableRepository;
import com.example.movietracker.data.repository.VideoRepository;

import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsMvpPresenter extends BaseMvpPresenter<MovieDetailsMvpView> {
    private MovieRepository movieRepository;
    private VideoRepository videoRepository;
    private MovieDetails movieDetails;
    private VideosResponse videosResponse;
    private CreditsResponse creditsResponse;
    private ReviewsResponse reviewsResponse;
    private boolean favorite;

    public MovieDetailsMvpPresenter(MovieRepository movieRepository, VideoRepository videoRepository) {
        this.movieRepository = movieRepository;
        this.videoRepository = videoRepository;
        videoRepository.registerRepositoryObserver(changeListener);
    }

    private final VideoRepository.RepositoryDataChangeListener changeListener = (action, item) -> {
        if (action == ObservableRepository.DbChangeAction.UPDATE_VIDEO_PROGRESS) {
            DbVideoProgress videoProgress = (DbVideoProgress) item;
            Video updatedVideo = videosResponse.getVideos().stream().filter(x -> x.getId().equals(videoProgress.getVideo_id())).findFirst().orElse(null);
            if (updatedVideo != null) {
                int updatedVideoIndex = videosResponse.getVideos().indexOf(updatedVideo);
                videosResponse.getVideos().get(updatedVideoIndex).setProgress(videoProgress.getProgress());
                view.updateVideos(updatedVideoIndex);
            }
        }
    };

    public void loadMovieDetails(String movieId) {
        if (movieDetails != null) {
            view.showMovieDetails(movieDetails);
        } else {
            disposables.add(movieRepository.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        movieDetails = response;
                        view.showMovieDetails(movieDetails);
                    }, e -> Log.d("e", "Error fetching movie details:" + e.getMessage())));
        }
    }

    public void loadCredits(String movieId) {
        disposables.add(movieRepository.getCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    creditsResponse = response;
                    view.showCredits(creditsResponse);
                }, e -> Log.d("e", "Error fetching movie credits:" + e.getMessage())));
    }

    public void loadReviews(String movieId) {
        disposables.add(movieRepository.getReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    reviewsResponse = response;
                    view.showReviews(reviewsResponse);
                }, e -> Log.d("e", "Error fetching movie credits:" + e.getMessage())));
    }

    public void loadVideos(String movieId) {
        disposables.add(videoRepository.getVideos(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    videosResponse = response;
                    view.showVideos(videosResponse);
                }, e -> Log.d("e", "Error fetching movie videos:" + e.getMessage())));
    }

    public void navigateToOverviewPage() {
        view.navigateToOverviewPage(movieDetails.getTitle(), movieDetails.getOverview());
    }

    public void navigateToOverviewPage(Review review) {
        view.navigateToOverviewPage(review.getAuthor(), review.getContent());
    }

    public void navigateToPlayActivity(String movieId, Video video) {
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle(movieDetails.getTitle());
        movie.setVoteAverage(movieDetails.getVoteAverage());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setPosterPath(movieDetails.getPosterPath());
        movie.setFavorite(favorite);
        movie.setGenreNames(movieDetails.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        disposables.add(movieRepository.bookmarkMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    int index = videosResponse.getVideos().indexOf(video);
                    view.navigateToPlayActivity(index, videosResponse);
                }, e -> Log.d("e", "Error while bookmarking:" + e.getMessage())));
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        videoRepository.unregisterRepositoryObserver(changeListener);
    }

    public void loadIsFavorite(String movieId) {
        disposables.add(movieRepository.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.contains(movieId)) {
                        favorite = true;
                        view.showIsFavorite(true);
                    }
                }, e -> Log.d("e", "Error fetching movie videos:" + e.getMessage())));
    }

    public void changeFavoriteStatus(String movieId) {
        if (favorite) {
            disposables.add(movieRepository.deleteFavorite(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response) {
                            favorite = false;
                            view.showIsFavorite(false);
                        }
                    }, e -> Log.d("e", "Error fetching movie videos:" + e.getMessage())));
        } else {
            disposables.add(movieRepository.setFavorite(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response) {
                            favorite = true;
                            view.showIsFavorite(true);
                        }
                    }, e -> Log.d("e", "Error fetching movie videos:" + e.getMessage())));
        }
    }
}
