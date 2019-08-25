package com.katie.shla.utils.list;

import com.katie.shla.network.services.StringNetworkService;
import com.katie.shla.utils.AsyncCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class ListRepo<T, R extends StringNetworkService<T, ?>> implements AsyncCallback<T>, ListContract.Repo<T> {
    protected final ArrayList<ListContract.RepoObserver<T>> observers = new ArrayList<>();
    protected final R service;

    public ListRepo(R service) {
        this.service = service;
        service.addCallback(this);
    }

    @Override
    public void onResult(List<T> result) {
        for (ListContract.RepoObserver<T> observer : observers) {
            observer.onDataUpdated(result);
        }
    }

    @Override
    public void onError() {
        for (ListContract.RepoObserver observer : observers) {
            observer.onError();
        }
    }

    @Override
    public void onFinish() { }

    @Override
    public void addObserver(ListContract.RepoObserver<T> observer) {
        observers.add(observer);
    }

    @Override
    public void destroy() {
        observers.clear();
        service.disconnect();
    }
}
