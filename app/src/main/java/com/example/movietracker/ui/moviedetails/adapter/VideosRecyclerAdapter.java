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
import com.example.movietracker.data.networking.models.Video;

public class VideosRecyclerAdapter extends ListAdapter<Video, BaseViewHolder> {
    private static VideoDiffCallback videoDiffCallback = new VideoDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    public VideosRecyclerAdapter(BaseViewHolder.ViewHolderClickListener viewHolderClickListener) {
        super(videoDiffCallback);
        this.viewHolderClickListener = viewHolderClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_video, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Video value = getItem(position);
        holder.bind(value, viewHolderClickListener);
    }

    private static class VideoDiffCallback extends DiffUtil.ItemCallback<Video> {
        @Override
        public boolean areItemsTheSame(@NonNull Video oldItem, @NonNull Video newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Video oldItem, @NonNull Video newItem) {
            return oldItem.equals(newItem);
        }
    }
}
