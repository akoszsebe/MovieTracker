package com.example.movietracker.ui.play;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movietracker.App;
import com.example.movietracker.R;
import com.example.movietracker.base.BasePlayerFragment;
import com.example.movietracker.data.networking.models.RecommendationsResponse;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.data.networking.models.YoutubeItem;
import com.example.movietracker.ui.play.adapter.RecommendationsRecyclerAdapter;
import com.example.movietracker.utils.Constants;

public class PlayFragment extends BasePlayerFragment<PlayMvpPresenter> implements PlayMvpView {
    private static final int NEXT_OVERLAY_START_OFFSET = 20000;

    private RecommendationsRecyclerAdapter recommendationsAdapter;
    private LinearLayout nextControls;
    private Button cancel;
    private FrameLayout playNext;
    private ImageView playNextView;
    private ProgressBar overlayProgress;

    private boolean hasNextVideo;
    private boolean isNextVideoOptionCanceled;
    private long lastRemainingTime = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_play;
    }

    @Override
    protected void injectDependencies() {
        App.instance().appComponent().inject(this);
    }

    @Override
    protected void attachToPresenter() {
        presenter.attach(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nextControls = view.findViewById(R.id.layout_next);
        cancel = view.findViewById(R.id.button_cancel);
        playNext = view.findViewById(R.id.imageButton_play);
        playNextView = view.findViewById(R.id.imageView_play);
        overlayProgress = view.findViewById(R.id.progress_overlay);
        recommendationsAdapter = new RecommendationsRecyclerAdapter(item -> {
            presenter.navigateToPlayRecommendations((YoutubeItem) item);
        });
        recommendationsHorizontalGridView.setAdapter(recommendationsAdapter);
        Bundle arguments = getArguments();
        if (arguments != null) {
            presenter.setVideos(PlayFragmentArgs.fromBundle(arguments).getVideos());
            presenter.setIndex(PlayFragmentArgs.fromBundle(arguments).getIndex());
        }
        initializePlayer();
    }

    @Override
    protected boolean isRecommendationsEnabled() {
        return true;
    }

    private void initializePlayer() {
        presenter.loadSelectedVideo();
        playerView.setControllerVisibilityListener(visibility -> {
            if (visibility == View.VISIBLE) {
                recommendationsContainer.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    @Override
    protected void onNextPressed() {
        super.onNextPressed();
        recommendationsContainer.fullScroll(ScrollView.FOCUS_UP);
        buttonPause.requestFocus();
        presenter.setProgress((int) (player.getCurrentPosition() * 100 / player.getDuration()));
        presenter.loadNextVideo();
    }

    @Override
    protected void onPrevPressed() {
        super.onPrevPressed();
        recommendationsContainer.fullScroll(ScrollView.FOCUS_UP);
        buttonPause.requestFocus();
        presenter.setProgress((int) (player.getCurrentPosition() * 100 / player.getDuration()));
        presenter.loadPrevVideo();
    }

    @Override
    protected void videoRemainingTime(long remainingSec) {
        if (remainingSec > lastRemainingTime) {
            isNextVideoOptionCanceled = false;
        }
        lastRemainingTime = remainingSec;
        if (hasNextVideo && !isNextVideoOptionCanceled) {
            if (remainingSec == 0 && nextControls.getVisibility() == View.VISIBLE) {
                nextControls.setVisibility(View.GONE);
                presenter.loadNextVideo();
            } else if (remainingSec <= NEXT_OVERLAY_START_OFFSET) {
                if (nextControls.getVisibility() != View.VISIBLE) {
                    playerView.hideController();
                    nextControls.setVisibility(View.VISIBLE);
                    playNext.requestFocus();
                    playNext.setOnClickListener(v -> {
                        presenter.loadNextVideo();
                        nextControls.setVisibility(View.GONE);
                    });
                    cancel.setOnClickListener(v -> {
                        nextControls.setVisibility(View.GONE);
                        isNextVideoOptionCanceled = true;
                    });
                }
                if (nextControls.getVisibility() == View.VISIBLE) {
                    overlayProgress.setProgress((int) (remainingSec), true);
                }
            }
        } else if (isNextVideoOptionCanceled && remainingSec == 0) {
            onBackPressed();
        }

    }

    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.setProgress((int) (player.getCurrentPosition() * 100 / player.getDuration()));
    }

    @Override
    protected void unsubscribePresenter() {
        presenter.unsubscribe();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void selectedVideo(Video video) {
        hasNextVideo = false;
        isNextVideoOptionCanceled = false;
        title.setText(video.getName());
        presenter.extractVideo(video);
        presenter.loadRecommendations(video.getKey());
        presenter.loadNextOverlay(video);
    }

    @Override
    public void showRecommendations(RecommendationsResponse recommendations) {
        recommendationsAdapter.submitList(recommendations.getItems());
    }

    @Override
    public void navigateToPlayRecommendations(String videoId) {
        pausePlayer();
        NavDirections direction = PlayFragmentDirections.actionPlayFragmentToPlayRecommendationFragment(videoId);
        if (this.getView() != null) {
            Navigation.findNavController(this.getView()).navigate(direction);
        }
    }

    @Override
    public void setNextOverlay(Video video) {
        hasNextVideo = true;
        Glide.with(playNextView.getContext())
                .load(String.format(Constants.YOUTUBE_THUMBNAIL_IMAGE_URL, video.getKey()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.details_placeholder_image)
                .into(playNextView);
    }
}
