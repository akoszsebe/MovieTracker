package com.example.movietracker.ui.moviedetails.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseViewHolder;
import com.example.movietracker.data.networking.models.CastMember;

public class CastMemberViewHolder extends BaseViewHolder {
    private TextView name;

    CastMemberViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_name);
    }

    @Override
    public void bind(Object item, ViewHolderClickListener viewHolderClickListener) {
        name.setText(((CastMember)item).getName());
    }
}
