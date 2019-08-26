package com.katie.shla.utils.list;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseListPresenter<T> implements ListContract.ListPresenter<T> {

    protected final ArrayList<T> data = new ArrayList<>();
    protected ListContract.ListView<T> view;
    protected ListContract.DetailView<T> detailView;

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindItemView(ListContract.ItemView<T> itemView, int position) {
        if (position < 0 || position >= data.size()) {
            return;
        }

        itemView.bind(data.get(position));
    }

    @Override
    public void onBindItemView(ListContract.ItemView<T> itemView, int position, List<Object> payloads) {
        if (position < 0 || position >= data.size()) {
            return;
        }

        itemView.bind(data.get(position), payloads);
    }

    @Override
    public void update(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    @Override
    public void subscribe(ListContract.ListView<T> view, ListContract.DetailView<T> detailView) {
        this.view = view;
        this.detailView = detailView;
    }

    @Override
    public void unsubscribe() {
        view = null;
        detailView = null;
    }

    @Override
    public void onItemClicked(int position) {
        if (position < 0 || position >= data.size()) {
            return;
        }
        if (detailView != null) {
            detailView.showDetail(data.get(position));
        }
    }

    @Override
    public void onItemLongClicked(int position) { }

    @Override
    @Nullable
    public T getDataAt(int position) {
        if (position < 0 || position >= data.size()) {
            return null;
        } else {
            return data.get(position);
        }
    }
}
