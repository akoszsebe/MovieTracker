package com.example.movietracker.ui.moviedetails;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.HorizontalGridView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.movietracker.App;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseFragment;
import com.example.movietracker.data.networking.models.CreditsResponse;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.MovieDetails;
import com.example.movietracker.data.networking.models.Review;
import com.example.movietracker.data.networking.models.ReviewsResponse;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.data.networking.models.VideosResponse;
import com.example.movietracker.ui.moviedetails.adapter.CastsRecyclerAdapter;
import com.example.movietracker.ui.moviedetails.adapter.ReviewsRecyclerAdapter;
import com.example.movietracker.ui.moviedetails.adapter.VideosRecyclerAdapter;
import com.example.movietracker.utils.Constants;
import com.example.movietracker.utils.Utils;

import java.util.Locale;
import java.util.stream.Collectors;

public class MovieDetailsFragment extends BaseFragment<MovieDetailsMvpPresenter> implements MovieDetailsMvpView {
    private ImageView image;
    private TextView title;
    private TextView additionalInfo;
    private TextView description;
    private ImageView expand;
    private ImageView favorite;
    private HorizontalGridView horizontalGridViewReviews;
    private HorizontalGridView horizontalGridViewVideos;
    private HorizontalGridView horizontalGridViewCasts;
    private TextView textViewReviewEmpty;
    private TextView textViewVideosEmpty;

    private CastsRecyclerAdapter castAdapter;
    private ReviewsRecyclerAdapter reviewsAdapter;
    private VideosRecyclerAdapter videosAdapter;
    private int viewClicked = -1;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie_details;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String movieId = MovieDetailsFragmentArgs.fromBundle(arguments).getMovieId();
            presenter.loadCredits(movieId);
            presenter.loadReviews(movieId);
            presenter.loadVideos(movieId);
            castAdapter = new CastsRecyclerAdapter(item -> {
                viewClicked = 0;
            });
            reviewsAdapter = new ReviewsRecyclerAdapter(item -> {
                presenter.navigateToOverviewPage((Review) item);
                viewClicked = 1;
            });
            videosAdapter = new VideosRecyclerAdapter(item -> {
                presenter.navigateToPlayActivity(movieId, (Video) item);
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = view.findViewById(R.id.imageView_image);
        title = view.findViewById(R.id.textView_title);
        additionalInfo = view.findViewById(R.id.textView_additional_info);
        description = view.findViewById(R.id.textView_description);
        expand = view.findViewById(R.id.imageView_expand);
        favorite = view.findViewById(R.id.imageView_favorite);
        textViewReviewEmpty = view.findViewById(R.id.textView_review_empty);
        textViewVideosEmpty = view.findViewById(R.id.textView_videos_empty);
        int spacing = Utils.convertDpToPixels(view.getContext(), 8);
        // casts
        horizontalGridViewCasts = view.findViewById(R.id.horizontalGridView_cast);
        horizontalGridViewCasts.setHorizontalSpacing(spacing);
        horizontalGridViewCasts.setAdapter(castAdapter);
        // reviews
        horizontalGridViewReviews = view.findViewById(R.id.horizontalGridView_review);
        horizontalGridViewReviews.setHorizontalSpacing(spacing);
        horizontalGridViewReviews.setAdapter(reviewsAdapter);
        // videos
        horizontalGridViewVideos = view.findViewById(R.id.horizontalGridView_videos);
        horizontalGridViewVideos.setHorizontalSpacing(spacing);
        horizontalGridViewVideos.setAdapter(videosAdapter);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String movieId = MovieDetailsFragmentArgs.fromBundle(arguments).getMovieId();
            presenter.loadMovieDetails(movieId);
            presenter.loadIsFavorite(movieId);
            favorite.setOnClickListener(v ->{
                presenter.changeFavoriteStatus(movieId);
            });
            expand.setOnClickListener(v -> {
                presenter.navigateToOverviewPage();
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        switch (viewClicked) {
            case 0:
                horizontalGridViewCasts.requestFocus();
                break;
            case 1:
                horizontalGridViewReviews.requestFocus();
                break;
        }
        viewClicked = -1;
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void showMovieDetails(MovieDetails movieDetails) {
        title.setText(movieDetails.getTitle());
        if (movieDetails.getOverview().length() > 140) {
            description.setText(movieDetails.getOverview().substring(0, 140) + "...");
            expand.setVisibility(View.VISIBLE);
        } else {
            description.setText(movieDetails.getOverview());
        }
        additionalInfo.setText(movieDetails.getRuntime() + " min" +
                "  |  " + movieDetails.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")) +
                "  |  ★ IMDB " + movieDetails.getVoteAverage() +
                "  |  " + String.format(Locale.getDefault(), "%s", movieDetails.getReleaseDate().substring(0, 4)));
        Glide.with(image.getContext())
                .load(Constants.BACKDROP_URL + movieDetails.getBackdropPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.details_placeholder_image)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        image.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public void showCredits(CreditsResponse credits) {
        castAdapter.submitList(credits.getCast());
    }

    @Override
    public void showReviews(ReviewsResponse reviews) {
        if (reviews.getReviews().isEmpty()) {
            horizontalGridViewReviews.setFocusable(false);
            textViewReviewEmpty.setVisibility(View.VISIBLE);
        } else {
            horizontalGridViewReviews.setFocusable(true);
            textViewReviewEmpty.setVisibility(View.GONE);
            reviewsAdapter.submitList(reviews.getReviews());
            horizontalGridViewCasts.requestFocus();
        }
    }

    @Override
    public void navigateToOverviewPage(String title, String content) {
        NavDirections direction = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMovieDetailsOverviewFragment(title, content);
        if (this.getView() != null) {
            Navigation.findNavController(this.getView()).navigate(direction);
        }
    }

    public void navigateToPlayActivity(int index, VideosResponse videosResponse){
        NavDirections direction = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPlayActivity(index,videosResponse);
        if (this.getView() != null) {
            Navigation.findNavController(this.getView()).navigate(direction);
        }
    }

    @Override
    public void updateVideos(int index) {
       videosAdapter.notifyItemChanged(index);
    }

    @Override
    public void showIsFavorite(boolean isFavorite) {
        if (isFavorite) {
            favorite.setImageResource(R.drawable.favorite_fill);
        } else {
            favorite.setImageResource(R.drawable.favorite_unfill);
        }
    }

    @Override
    public void showVideos(VideosResponse videos) {
        if (videos.getVideos().isEmpty()) {
            horizontalGridViewVideos.setFocusable(false);
            textViewVideosEmpty.setVisibility(View.VISIBLE);
        } else {
            horizontalGridViewVideos.setFocusable(true);
            textViewVideosEmpty.setVisibility(View.GONE);
            videosAdapter.submitList(videos.getVideos());
            horizontalGridViewCasts.requestFocus();
        }
    }

    @Override
    protected void unsubscribePresenter() {
        presenter.unsubscribe();
    }
}
