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
import com.example.movietracker.data.networking.models.CastMember;

public class CastsRecyclerAdapter extends ListAdapter<CastMember, BaseViewHolder> {
    private static CastMemberDiffCallback castMemberDiffCallback = new CastMemberDiffCallback();
    private BaseViewHolder.ViewHolderClickListener viewHolderClickListener;

    public CastsRecyclerAdapter(BaseViewHolder.ViewHolderClickListener viewHolderClickListener) {
        super(castMemberDiffCallback);
        this.viewHolderClickListener = viewHolderClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_castmember, parent, false);
        return new CastMemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        CastMember value = getItem(position);
        holder.bind(value, viewHolderClickListener);
    }

    private static class CastMemberDiffCallback extends DiffUtil.ItemCallback<CastMember> {
        @Override
        public boolean areItemsTheSame(@NonNull CastMember oldItem, @NonNull CastMember newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CastMember oldItem, @NonNull CastMember newItem) {
            return oldItem.equals(newItem);
        }
    }
}
