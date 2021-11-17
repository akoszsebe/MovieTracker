package com.example.movietracker.ui.play.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.YoutubeItem;

public class RecommendationsRecyclerAdapter extends ListAdapter<YoutubeItem, BaseViewHolder> {
    private static YoutubeItemDiffCallback youtubeItemDiffCallback = new YoutubeItemDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    public RecommendationsRecyclerAdapter(BaseViewHolder.ViewHolderClickListener viewHolderClickListener) {
        super(youtubeItemDiffCallback);
        this.viewHolderClickListener = viewHolderClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_video, parent, false);
        return new RecommendationsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        YoutubeItem value = getItem(position);
        holder.bind(value, viewHolderClickListener);
    }

    private static class YoutubeItemDiffCallback extends DiffUtil.ItemCallback<YoutubeItem> {
        @Override
        public boolean areItemsTheSame(@NonNull YoutubeItem oldItem, @NonNull YoutubeItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull YoutubeItem oldItem, @NonNull YoutubeItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
