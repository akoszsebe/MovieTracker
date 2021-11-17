package com.example.movietracker.ui.genreselection.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.utils.GenreMapper;

class GenreViewHolder extends BaseViewHolder {
    private TextView title;
    private ImageView selected;
    private ImageView genreImage;

    GenreViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        selected = itemView.findViewById(R.id.genre_check_imageview);
        genreImage = itemView.findViewById(R.id.imageView_image);
    }

    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
        Genre genre = (Genre) item;
        title.setText(genre.getName());
        selected.setVisibility(genre.isSelected() ? View.VISIBLE : View.INVISIBLE);
        itemView.setOnClickListener(v -> {
            if (this.viewHolderClickListener != null){
                this.viewHolderClickListener.onClicked(genre);
            }
        });
        Glide.with(genreImage.getContext())
                .load(GenreMapper.getImage(genre.getName()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.details_placeholder_image)
                .into(genreImage);
    }
}