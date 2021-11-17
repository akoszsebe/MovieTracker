package com.example.movietracker.ui.movies.adapter;

import android.os.Build;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.utils.Constants;
import com.example.movietracker.utils.Utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MovieViewHolder extends BaseViewHolder {
    private static final int ANIMATION_TEXT_OVERFLOW_START_OFFSET = 2000;
    private static final int TEXT_OVERFLOW_OFFSET = 30;
    private ImageView poster;
    private ImageView favorite;
    private RelativeLayout details;
    private TextView date;
    private TextView title;
    private TextView genres;
    private TextView rate;
    private int radius;

    private String savedTitle = "";

    MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        poster = itemView.findViewById(R.id.poster_iv);
        details = itemView.findViewById(R.id.details_layout);
        date = itemView.findViewById(R.id.textView_date);
        title = itemView.findViewById(R.id.textView_title);
        genres = itemView.findViewById(R.id.textView_genres);
        rate = itemView.findViewById(R.id.textView_rates);
        favorite = itemView.findViewById(R.id.imageView_favorite);
        radius = Utils.convertDpToPixels(itemView.getContext(), 12);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
        Movie movie = (Movie) item;
        Glide.with(poster.getContext())
                .load(Constants.POSTER_URL + movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.poster_placeholder)
                .into(poster);
        title.setText(movie.getTitle());
        String releaseDate = movie.getReleaseDate();
        if (releaseDate != null) {
            date.setText(String.format(Locale.getDefault(), "%s", releaseDate.substring(0, 4)));
        }
        genres.setText(String.join(", ", movie.getGenreNames()));
        rate.setText(String.format(Locale.getDefault(), "IMDB %.1f", movie.getVoteAverage()));
        itemView.setOnClickListener(v -> {
            if (this.viewHolderClickListener != null) {
                this.viewHolderClickListener.onClicked(movie);
            }
        });
        title.setMovementMethod(new ScrollingMovementMethod());
        savedTitle = movie.getTitle();
        if (movie.isFavorite()){
            favorite.setVisibility(View.VISIBLE);
        } else {
            favorite.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChanged(View v, boolean hasFocus) {
        final Handler handler = new Handler();
        super.onFocusChanged(v, hasFocus);
        if (hasFocus) {
            handler.post(() -> {
                details.setVisibility(View.VISIBLE);
                ((CardView) v).setRadius(radius);
            });
            if (title.getText().length() > TEXT_OVERFLOW_OFFSET) {
                title.setText(savedTitle);
                Animation animSequential;
                animSequential = AnimationUtils.loadAnimation(title.getContext(), R.anim.scroll_animation);
                animSequential.setStartOffset(ANIMATION_TEXT_OVERFLOW_START_OFFSET);
                animSequential.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation anim = AnimationUtils.loadAnimation(title.getContext(), R.anim.scroll_animation);
                        anim.setAnimationListener(this);
                        title.startAnimation(anim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                title.startAnimation(animSequential);
            }
        } else {
            handler.post(() -> {
                details.setVisibility(View.GONE);
                ((CardView) v).setRadius(0);
            });

        }
    }
}
