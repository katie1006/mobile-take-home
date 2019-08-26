package com.katie.shla.utils.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseListAdapter<T>
        extends RecyclerView.Adapter<ListViewHolder<T>> implements ListContract.ListView<T> {

    protected ListContract.ListPresenter<T> presenter = null;

    @Override
    public void subscribe(ListContract.DetailView<T> detailView, ListContract.ListPresenter<T> presenter) {
        this.presenter = presenter;
        this.presenter.subscribe(this, detailView);
    }

    @Override
    public void unsubscribe() {
        presenter.unsubscribe();
        presenter = null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder<T> holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onItemClicked(position);
            }
        });

        if (presenter != null) {
            presenter.onBindItemView(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return presenter == null ? 0 : presenter.getItemCount();
    }

    @Override
    public void updateList(List<T> data) {
        presenter.update(data);
        notifyDataSetChanged();
    }
}
