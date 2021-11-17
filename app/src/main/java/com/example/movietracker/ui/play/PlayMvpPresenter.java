package com.example.movietracker.ui.play;

import android.util.Log;

import com.example.movietracker.base.BaseMvpPresenter;
import com.example.movietracker.data.networking.models.MovieResponse;
import com.example.movietracker.data.networking.models.RecommendationsResponse;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.data.networking.models.VideosResponse;
import com.example.movietracker.data.networking.models.YoutubeItem;
import com.example.movietracker.data.repository.MovieRepository;
import com.example.movietracker.data.repository.VideoRepository;
import com.example.movietracker.data.repository.YoutubeRepository;
import com.example.movietracker.utils.YoutubeExtractorHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlayMvpPresenter extends BaseMvpPresenter<PlayMvpView> {
    private YoutubeExtractorHelper youtubeExtractorHelper;
    private YoutubeRepository youtubeRepository;
    private VideoRepository videoRepository;
    private List<Video> videos;
    private int index;
    private RecommendationsResponse recommendations;

    public PlayMvpPresenter(YoutubeExtractorHelper youtubeExtractorHelper, YoutubeRepository youtubeRepository, VideoRepository videoRepository) {
        this.youtubeExtractorHelper = youtubeExtractorHelper;
        this.youtubeRepository = youtubeRepository;
        this.videoRepository = videoRepository;
    }

    public void extractVideo(Video video) {
        disposables.add(youtubeExtractorHelper.extract(video.getKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
                    view.playVideo(response);
                }, error -> {
                    view.showError(error.getMessage());
                }));
    }

    public void loadRecommendations(String videoId) {
        disposables.add(youtubeRepository.getYoutubeRecommendations(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    recommendations = response;
                    view.showRecommendations(recommendations);
                }, e -> Log.d("e", "Error fetching movie credits:" + e.getMessage())));
    }

    public void setVideos(VideosResponse videosResponse) {
        this.videos = videosResponse.getVideos();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void loadSelectedVideo() {
        view.selectedVideo(videos.get(index));
        checkIndex();
    }

    public void loadNextVideo() {
        if (videos.size() > (index + 1)) {
            view.selectedVideo(videos.get(++index));
        }
        checkIndex();
    }

    public void loadPrevVideo() {
        if (0 <= index - 1) {
            view.selectedVideo(videos.get(--index));
        }
        checkIndex();
    }

    private void checkIndex() {
        if (index == 0) {
            view.enablePrev(false);
        } else {
            view.enablePrev(true);
        }
        if (index == videos.size() - 1) {
            view.enableNext(false);
        } else {
            view.enableNext(true);
        }
    }

    public void navigateToPlayRecommendations(YoutubeItem item) {
        view.navigateToPlayRecommendations(item.getId().getVideoId());
    }

    public void loadNextOverlay(Video video) {
        int index = videos.indexOf(video);
        if (index != -1) {
            if (index < videos.size() - 1) {
                view.setNextOverlay(videos.get(++index));
            }
        }
    }

    public void setProgress(int progress){
        disposables.add(videoRepository.setVideoProgress(videos.get(index).getId(),progress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                }, e -> Log.d("e", "Error fetching movie credits:" + e.getMessage())));
    }
}
