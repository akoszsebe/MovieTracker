package com.example.movietracker.ui.movies.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.leanback.widget.HorizontalGridView;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.GenreMovies;
import com.example.movietracker.utils.Utils;

public class GenreMoviesViewHolder extends BaseViewHolder {
    private TextView title;
    private HorizontalGridView horizontalGridView;

    GenreMoviesViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textView_title);
        horizontalGridView = itemView.findViewById(R.id.gridview_movies);
    }

    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        GenreMovies genreMovies = (GenreMovies) item;
        title.setText(genreMovies.getGenreName());
        int spacing = Utils.convertDpToPixels(horizontalGridView.getContext(), 8);
        horizontalGridView.setHorizontalSpacing(spacing);
        horizontalGridView.setVerticalSpacing(spacing);
        MovieRecyclerAdapter adapter = new MovieRecyclerAdapter(viewHolderClickListener);
        adapter.submitList(genreMovies.getMovies());
        horizontalGridView.setAdapter(adapter);
        if (genreMovies.getScrollIndex() != -1) {
            horizontalGridView.scrollToPosition(genreMovies.getScrollIndex());
            genreMovies.setScrollIndex(-1);
        }
    }
}
