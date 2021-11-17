package com.example.movietracker.ui.moviedetails.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Review;

public class ReviewsRecyclerAdapter extends ListAdapter<Review, BaseViewHolder> {
    private static ReviewDiffCallback reviewDiffCallback = new ReviewDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    public ReviewsRecyclerAdapter(BaseViewHolder.ViewHolderClickListener viewHolderClickListener) {
        super(reviewDiffCallback);
        this.viewHolderClickListener = viewHolderClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_review, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Review value = getItem(position);
        holder.bind(value, viewHolderClickListener);
    }

    private static class ReviewDiffCallback extends DiffUtil.ItemCallback<Review> {
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }
    }
}
