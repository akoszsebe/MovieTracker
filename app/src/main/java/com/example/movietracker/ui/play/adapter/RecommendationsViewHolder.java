package com.example.movietracker.ui.play.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Video;
import com.example.movietracker.data.networking.models.YoutubeItem;
import com.example.movietracker.utils.Constants;

public class RecommendationsViewHolder extends BaseViewHolder {
    private TextView name;
    private ImageView image;

    public RecommendationsViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_name);
        image = itemView.findViewById(R.id.imageView_image);
    }

    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
        YoutubeItem video = (YoutubeItem) item;
        name.setText(video.getSnippet().getTitle());
        Glide.with(image.getContext())
                .load(String.format(Constants.YOUTUBE_THUMBNAIL_IMAGE_URL, video.getId().getVideoId()))
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
