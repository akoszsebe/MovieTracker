package com.example.movietracker.ui.playrecommendation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movietracker.App;
import com.example.movietracker.R;
import com.example.movietracker.base.BasePlayerFragment;

public class PlayRecommendationFragment extends BasePlayerFragment<PlayRecommendationMvpPresenter> implements PlayRecommendationMvpView {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_play_recommendations;
    }

    @Override
    protected void injectDependencies() {
        App.instance().appComponent().inject(this);
    }

    @Override
    protected void attachToPresenter() {
        presenter.attach(this);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String videoId = PlayRecommendationFragmentArgs.fromBundle(arguments).getVideoId();
            presenter.extractVideo(videoId);
        }
        enableNext(false);
        enablePrev(false);
    }

    @Override
    protected boolean isRecommendationsEnabled() {
        return false;
    }

    @Override
    protected void unsubscribePresenter() {
        presenter.unsubscribe();
    }
}
