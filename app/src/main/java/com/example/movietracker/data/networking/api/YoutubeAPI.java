package com.example.movietracker.data.networking.api;

import com.example.movietracker.data.networking.models.RecommendationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeAPI {
    @GET("youtube/v3/search?part=snippet&type=video")
    Call<RecommendationsResponse> getYoutubeRecommendations(
            @Query("relatedToVideoId") String relatedToVideoId,
            @Query("key") String apiKey
    );
}
