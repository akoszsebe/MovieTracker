package com.example.movietracker.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {
    protected ViewHolderClickListener viewHolderClickListener;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnFocusChangeListener(this::onFocusChanged);
    }

    public void onFocusChanged(View v, boolean hasFocus) {
    }

    public abstract void bind(Object item, ViewHolderClickListener viewHolderClickListener);

    public interface ViewHolderClickListener {
        void onClicked(Object item);
    }
}


