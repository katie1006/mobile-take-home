package com.katie.shla.utils.list;

import java.util.List;

public class ListParentPresenter<T> implements ListContract.Presenter<T>, ListContract.RepoObserver<T> {
    private ListContract.Repo<T> repo;
    private ListContract.View<T> view;

    @Override
    public void subscribe(ListContract.View<T> view, ListContract.Repo<T> repo, Object... input) {
        this.view = view;
        this.repo = repo;
        repo.addObserver(this);
        repo.requestData(input);
    }

    @Override
    public void unsubscribe() {
        repo.destroy();
        repo = null;
        view = null;
    }

    @Override
    public void onDataUpdated(List<T> result) {
        if (view != null) {
            view.showList(result);
        }
    }

    @Override
    public void onError() {
        if (view != null) {
            view.showError();
        }
    }

    @Override
    public void onDataItemClicked(T data) {
        if (view != null) {
            view.showDetail(data);
        }
    }

    @Override
    public void requestNextPage() {
        repo.requestData();
    }
}
