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
import com.example.movietracker.data.networking.models.Movie;

public class MovieRecyclerAdapter extends ListAdapter<Movie, BaseViewHolder> {
    private static MovieDiffCallback movieDiffCallback = new MovieDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    MovieRecyclerAdapter(BaseViewHolder.ViewHolderClickListener viewHolderClickListener) {
        super(movieDiffCallback);
        this.viewHolderClickListener = viewHolderClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Movie value = getItem(position);
        holder.bind(value, viewHolderClickListener);
    }

    private static class MovieDiffCallback extends DiffUtil.ItemCallback<Movie> {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    }
}
