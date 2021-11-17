package com.example.movietracker.ui.genreselection.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Genre;

public class GenreRecyclerAdapter extends ListAdapter<Genre, BaseViewHolder> {
    private static GenreDiffCallback genreDiffCallback = new GenreDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    public GenreRecyclerAdapter(BaseViewHolder.ViewHolderClickListener clickListener) {
        super(genreDiffCallback);
        viewHolderClickListener = clickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_continue, parent, false);
            return new ContinueViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_genre, parent, false);
        return new GenreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Genre value = getItem(position);
        holder.bind(value,viewHolderClickListener);

    }

    @Override
    public int getItemViewType(int position) {
        return position + 1 != getItemCount() ? 0 : 1;
    }

    private static class GenreDiffCallback extends DiffUtil.ItemCallback<Genre> {
        @Override
        public boolean areItemsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
            return oldItem.equals(newItem);
        }
    }
}
