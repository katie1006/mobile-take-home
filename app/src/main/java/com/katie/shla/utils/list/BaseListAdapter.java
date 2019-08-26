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
                if (presenter != null) {
                    presenter.onItemClicked(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (presenter != null) {
                    presenter.onItemLongClicked(position);
                }
                return true;
            }
        });

        if (presenter != null) {
            presenter.onBindItemView(holder, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder<T> holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (presenter != null) {
            presenter.onBindItemView(holder, position, payloads);
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

    @Override
    public void updateItem(int position, T data) {
        notifyItemChanged(position);
    }
}
