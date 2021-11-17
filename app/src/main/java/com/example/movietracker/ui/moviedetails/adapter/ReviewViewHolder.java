package com.example.movietracker.ui.moviedetails.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.Review;

import java.util.Random;

public class ReviewViewHolder extends BaseViewHolder {
    private TextView text;
    private TextView author;
    private TextView stars;

    private Random random;

    ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.textView_text);
        author = itemView.findViewById(R.id.textView_author);
        stars = itemView.findViewById(R.id.textView_stars);
        random = new Random();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
        Review review = (Review) item;
        if (review.getContent().length() > 170) {
            text.setText(review.getContent().substring(0, 170) + "...");
        } else {
            text.setText(review.getContent());
        }

        author.setText(review.getAuthor());
        StringBuilder startsText = new StringBuilder();
        for (int i = 0; i < random.nextInt(5) + 1; i++) startsText.append("★");
        stars.setText(startsText.toString());
        itemView.setOnClickListener(v -> {
            if (this.viewHolderClickListener != null) {
                this.viewHolderClickListener.onClicked(review);
            }
        });
    }
}
