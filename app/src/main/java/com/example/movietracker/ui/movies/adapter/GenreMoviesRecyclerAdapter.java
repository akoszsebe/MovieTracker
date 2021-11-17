package com.example.movietracker.ui.movies.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.GenreMovies;

public class GenreMoviesRecyclerAdapter  extends ListAdapter<GenreMovies, BaseViewHolder> {
    private static GenreMoviesDiffCallback genreMoviesDiffCallback = new GenreMoviesDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    public GenreMoviesRecyclerAdapter(BaseViewHolder.ViewHolderClickListener clickListener) {
        super(genreMoviesDiffCallback);
        viewHolderClickListener = clickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.colum_item_genre_movies, parent, false);
        return new GenreMoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        GenreMovies value = getItem(position);
        holder.bind(value,viewHolderClickListener);
    }

    private static class GenreMoviesDiffCallback extends DiffUtil.ItemCallback<GenreMovies> {
        @Override
        public boolean areItemsTheSame(@NonNull GenreMovies oldItem, @NonNull GenreMovies newItem) {
            return oldItem.getGenreName() == newItem.getGenreName();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull GenreMovies oldItem, @NonNull GenreMovies newItem) {
            return oldItem.equals(newItem);
        }
    }
}
