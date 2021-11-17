package com.example.movietracker.data.networking.api;


import com.example.movietracker.data.networking.models.CreditsResponse;
import com.example.movietracker.data.networking.models.GenresResponse;
import com.example.movietracker.data.networking.models.MovieDetails;
import com.example.movietracker.data.networking.models.MovieResponse;
import com.example.movietracker.data.networking.models.ReviewsResponse;
import com.example.movietracker.data.networking.models.VideosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbAPI {

    @GET("genre/movie/list")
    Call<GenresResponse> getMovieGenres(
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> getCredits(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getReviews(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<VideosResponse> getMovieVideos(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByGenre(
            @Query("api_key") String apiKey,
            @Query("with_genres") int genreId
    );
}
