package com.example.movietracker.ui.playrecommendation;

import com.example.movietracker.base.BaseMvpPresenter;
import com.example.movietracker.utils.YoutubeExtractorHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlayRecommendationMvpPresenter extends BaseMvpPresenter<PlayRecommendationMvpView> {
    private YoutubeExtractorHelper youtubeExtractorHelper;


    public PlayRecommendationMvpPresenter(YoutubeExtractorHelper youtubeExtractorHelper) {
        this.youtubeExtractorHelper = youtubeExtractorHelper;
    }


    public void extractVideo(String videoId) {
        disposables.add(youtubeExtractorHelper.extract(videoId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
                    view.playVideo(response);
                }, error -> {
                    view.showError(error.getMessage());
                }));
    }
}
