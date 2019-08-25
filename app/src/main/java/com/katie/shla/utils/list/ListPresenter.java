package com.katie.shla.utils.list;

import java.util.ArrayList;
import java.util.List;

public class ListPresenter<T> implements ListContract.Presenter<T> {

    protected final ArrayList<T> repo = new ArrayList<>();
    protected ListContract.View<T> view;
    protected ListContract.DetailView<T> detailView;

    @Override
    public int getItemCount() {
        return repo.size();
    }

    @Override
    public void onBindItemView(ListContract.ItemView<T> itemView, int position) {
        if (position < 0 || position >= repo.size()) {
            return;
        }

        itemView.bind(repo.get(position));
    }

    @Override
    public void update(List<T> data) {
        repo.clear();
        repo.addAll(data);
    }

    @Override
    public void subscribe(ListContract.View<T> view, ListContract.DetailView<T> detailView) {
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
        if (position < 0 || position >= repo.size()) {
            return;
        }
        detailView.showDetail(repo.get(position));
    }
}
