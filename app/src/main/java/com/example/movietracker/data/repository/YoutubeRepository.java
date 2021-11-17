package com.example.movietracker.data.repository;

import com.example.movietracker.BuildConfig;
import com.example.movietracker.data.networking.api.YoutubeAPI;
import com.example.movietracker.data.networking.models.RecommendationsResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;

public class YoutubeRepository {
    private YoutubeAPI youtubeAPI;

    @Inject
    public YoutubeRepository(YoutubeAPI youtubeAPI) {
        this.youtubeAPI = youtubeAPI;
    }

    public Single<RecommendationsResponse> getYoutubeRecommendations(String videoId) {
        return Single.create(emitter -> loadYoutubeRecommendationsFromNetwork(emitter, videoId));
    }

    private void loadYoutubeRecommendationsFromNetwork(SingleEmitter<RecommendationsResponse> emitter, String videoId) {
        try {
            RecommendationsResponse recommendationsResponse =
                    youtubeAPI.getYoutubeRecommendations(videoId, BuildConfig.YOUTUBE_API_KEY).execute().body();
            if (recommendationsResponse != null) {
                emitter.onSuccess(recommendationsResponse);
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }
}
