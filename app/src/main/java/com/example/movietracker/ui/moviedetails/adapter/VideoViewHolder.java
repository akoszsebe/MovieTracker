package com.example.movietracker.ui.moviedetails.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.utils.Constants;

public class VideoViewHolder extends BaseViewHolder {
    private TextView name;
    private ImageView image;
    private ProgressBar progress;

    VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_name);
        image = itemView.findViewById(R.id.imageView_image);
        progress = itemView.findViewById(R.id.progressBar_videoProgress);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
        Video video = (Video) item;
        name.setText(video.getName());
        progress.setProgress(video.getProgress());
        progress.setProgress(video.getProgress());
        Glide.with(image.getContext())
                .load(String.format(Constants.YOUTUBE_THUMBNAIL_IMAGE_URL, video.getKey()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.details_placeholder_image)
                .into(image);
        itemView.setOnClickListener(v -> {
            if (this.viewHolderClickListener != null) {
                this.viewHolderClickListener.onClicked(video);
            }
        });
    }
}
