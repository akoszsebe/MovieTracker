package com.example.movietracker.data.repository;

import com.example.movietracker.BuildConfig;
import com.example.movietracker.data.networking.api.TheMovieDbAPI;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.data.networking.models.VideosResponse;
import com.example.movietracker.data.persitance.dao.VideoProgressDao;
import com.example.movietracker.data.persitance.entity.DbVideoProgress;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;

public class VideoRepository extends ObservableRepository {
    private TheMovieDbAPI theMovieDbAPI;
    private VideoProgressDao videoProgressDao;

    @Inject
    public VideoRepository(TheMovieDbAPI theMovieDbAPI, VideoProgressDao videoProgressDao) {
        this.theMovieDbAPI = theMovieDbAPI;
        this.videoProgressDao = videoProgressDao;
    }

    public Single<VideosResponse> getVideos(String movieId) {
        return Single.create(emitter -> loadVideosFromNetwork(emitter, movieId));
    }

    public Single<Boolean> setVideoProgress(String videoId, int progress) {
        return Single.create(emitter -> saveVideoProgressToDB(emitter, videoId, progress));
    }

    private void loadVideosFromNetwork(SingleEmitter<VideosResponse> emitter, String movieId) {
        try {
            VideosResponse videosResponse =
                    theMovieDbAPI.getMovieVideos(movieId, BuildConfig.TMDB_API_KEY).execute().body();
            if (videosResponse != null) {
                for (Video video : videosResponse.getVideos()) {
                    video.setProgress(videoProgressDao.getVideoProgressById(video.getId()).getProgress());
                }
                emitter.onSuccess(videosResponse);
            } else {
                emitter.onError(new Exception("No data received"));
            }
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }

    private void saveVideoProgressToDB(SingleEmitter<Boolean> emitter, String videoId, int progress) {
        try {
            DbVideoProgress videoProgress = new DbVideoProgress(videoId, progress);
            videoProgressDao.insertVideoProgress(videoProgress);
            notifyRepositoryChanged(DbChangeAction.UPDATE_VIDEO_PROGRESS, videoProgress);
        } catch (Exception exception) {
            emitter.onError(exception);
        }
    }
}
