package com.katie.shla.utils.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListViewHolder<T> extends RecyclerView.ViewHolder implements ListContract.ItemView<T> {
    public ListViewHolder(@NonNull View root) {
        super(root);
    }
}
