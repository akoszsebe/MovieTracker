package com.example.movietracker.ui.genreselection.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.utils.Utils;

public class ContinueViewHolder extends BaseViewHolder {

    public ContinueViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
        itemView.setOnClickListener(v -> {
            if (this.viewHolderClickListener != null){
                this.viewHolderClickListener.onClicked(item);
            }
        });
    }
}
