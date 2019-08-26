package com.katie.shla.utils.list;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseListPresenter<T> implements ListContract.ListPresenter<T> {

    protected final ArrayList<T> data = new ArrayList<>();
    protected ListContract.ListView<T> view;
    private ListContract.Presenter<T> parentPresenter;

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
    public void subscribeView(ListContract.ListView<T> view) {
        this.view = view;
    }

    @Override
    public void subscribeParent(ListContract.Presenter<T> parentPresenter) {
        this.parentPresenter = parentPresenter;
    }

    @Override
    public void unsubscribe() {
        view = null;
        this.parentPresenter = null;
    }

    @Override
    public void onItemClicked(int position) {
        if (position < 0 || position >= data.size()) {
            return;
        }

        if (parentPresenter != null) {
            parentPresenter.onDataItemClicked(data.get(position));
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

    @Override
    public void onNextPage() {
        if (parentPresenter != null) {
            parentPresenter.requestNextPage();
        }
    }
}
