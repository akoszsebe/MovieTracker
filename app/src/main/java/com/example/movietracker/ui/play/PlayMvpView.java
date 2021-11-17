package com.example.movietracker.ui.play;

import com.example.movietracker.base.BasePlayerMvpView;
import com.example.movietracker.data.networking.models.MovieResponse;
import com.example.movietracker.data.networking.models.RecommendationsResponse;
import com.example.movietracker.data.networking.models.Video;

public interface PlayMvpView extends BasePlayerMvpView {
    void selectedVideo(Video video);

    void showRecommendations(RecommendationsResponse recommendations);

    void navigateToPlayRecommendations(String videoId);

    void setNextOverlay(Video video);
}
