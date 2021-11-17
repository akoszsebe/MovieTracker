package com.example.movietracker.base;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.HorizontalGridView;

import com.example.movietracker.R;
import com.example.movietracker.utils.Utils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public abstract class BasePlayerFragment<MvpPresenterType> extends BaseFragment<MvpPresenterType> implements BasePlayerMvpView {
    protected PlayerView playerView;
    protected TextView title;
    private TextView remainingTime;
    private TextView currentTime;
    protected ImageButton buttonPause;
    private ImageButton buttonNext;
    private ImageButton buttonPrev;
    private TextView recommendationsTextView;
    protected ScrollView recommendationsContainer;
    protected HorizontalGridView recommendationsHorizontalGridView;
    private LinearLayout errorLayout;
    private TextView errorTextView;

    protected SimpleExoPlayer player;

    private boolean videoLoaded;

    protected Disposable playbackDisposable;
    protected Observable<Long> playbackProgressObservable =
            Observable.interval(1, TimeUnit.SECONDS)
                    .map(response -> player.getCurrentPosition())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
    protected DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerView = view.findViewById(R.id.playerView_player);
        title = view.findViewById(R.id.textView_title);
        remainingTime = view.findViewById(R.id.textView_remainingTime);
        currentTime = view.findViewById(R.id.textView_currentTime);
        buttonPause = view.findViewById(R.id.button_pause);
        buttonNext = view.findViewById(R.id.button_next);
        buttonPrev = view.findViewById(R.id.button_prev);
        recommendationsTextView = view.findViewById(R.id.textView_recommendations);
        recommendationsContainer = view.findViewById(R.id.scrollView_recommendations);
        recommendationsHorizontalGridView = view.findViewById(R.id.horizontalGridView_recommendation);
        errorLayout = view.findViewById(R.id.linearLayout_error);
        errorTextView = view.findViewById(R.id.textView_error);
        int spacing = Utils.convertDpToPixels(view.getContext(), 8);
        recommendationsHorizontalGridView.setHorizontalSpacing(spacing);
        player = ExoPlayerFactory.newSimpleInstance(getActivity());
        playerView.setPlayer(player);
        playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);
        buttonPause.setOnClickListener(v -> {
            onPausePressed();
        });
        buttonNext.setOnClickListener(v -> {
            onNextPressed();
        });
        buttonPrev.setOnClickListener(v -> {
            onPrevPressed();
        });
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        setRecommendations(isRecommendationsEnabled());
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    videoRemainingTime(0);
                    remainingTime.setText(formatter.format(new Date(0)));
                    currentTime.setText(formatter.format(new Date(player.getDuration())));
                }
            }
        });
    }

    protected abstract boolean isRecommendationsEnabled();

    private void setRecommendations(boolean enabled) {
        if (enabled) {
            recommendationsTextView.setVisibility(View.VISIBLE);
            recommendationsHorizontalGridView.setVisibility(View.VISIBLE);
        } else {
            recommendationsTextView.setVisibility(View.GONE);
            recommendationsHorizontalGridView.setVisibility(View.GONE);
        }
        recommendationsHorizontalGridView.setClickable(enabled);
        recommendationsHorizontalGridView.setFocusable(enabled);
    }

    @Override
    public void playVideo(String downloadUrl) {
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "MovieTracker"));
        MediaSource mediaSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(downloadUrl));
        player.setPlayWhenReady(true);
        player.prepare(mediaSource);
        videoLoaded = true;
    }

    @Override
    public void enableNext(boolean enabled) {
        buttonNext.setEnabled(enabled);
        buttonNext.setClickable(enabled);
        buttonNext.setFocusable(enabled);
    }

    @Override
    public void enablePrev(boolean enabled) {
        buttonPrev.setEnabled(enabled);
        buttonPrev.setClickable(enabled);
        buttonPrev.setFocusable(enabled);
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
        if (playbackDisposable != null) {
            playbackDisposable.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        playPlayer();
        timeChangeOnUi(formatter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playbackDisposable != null) {
            playbackDisposable.dispose();
        }
    }

    @Override
    public void showError(String message) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
    }

    protected void pausePlayer() {
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }

    protected void onNextPressed() {
        videoLoaded = false;
    }

    protected void onPrevPressed() {
        videoLoaded = false;
    }

    protected void playPlayer() {
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    protected void onPausePressed() {
        pausePlayer();
    }

    protected void timeChangeOnUi(DateFormat formatter) {
        playbackDisposable = playbackProgressObservable
                .subscribe(current ->
                {
                    long remaining = player.getDuration() - current;
                    if (videoLoaded && remaining >= 0) {
                        remainingTime.setText(formatter.format(new Date(remaining)));
                        currentTime.setText(formatter.format(new Date(current)));
                        videoRemainingTime(remaining);
                    }
                });
    }

    protected void videoRemainingTime(long remainingSec) {
    }
}
