package com.example.movietracker.ui.moviedetails;

import com.example.movietracker.data.networking.models.CreditsResponse;
import com.example.movietracker.data.networking.models.MovieDetails;
import com.example.movietracker.data.networking.models.ReviewsResponse;
import com.example.movietracker.data.networking.models.VideosResponse;

public interface MovieDetailsMvpView {
    void showMovieDetails(MovieDetails response);

    void showCredits(CreditsResponse credits);

    void showReviews(ReviewsResponse reviews);

    void showVideos(VideosResponse videos);

    void navigateToOverviewPage(String title,String content);

    void navigateToPlayActivity(int index, VideosResponse videosResponse);

    void updateVideos(int index);

    void showIsFavorite(boolean favorite);
}
