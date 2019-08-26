package com.katie.shla.utils.list;

import androidx.annotation.Nullable;

import java.util.List;

public interface ListContract {
    interface ListView<T> {
        void subscribe(ListContract.DetailView<T> detailView, ListPresenter<T> presenter);
        void unsubscribe();
        void updateList(List<T> data);
        void updateItem(int position, T data);
    }

    interface DetailView<T> {
        void showDetail(T data);
    }

    interface ItemView<T> {
        void bind(T data);
        void bind(T data, List<Object> payloads);
    }

    interface ListPresenter<T> {
        int getItemCount();
        void onBindItemView(ItemView<T> itemView, int position);
        void onBindItemView(ItemView<T> itemView, int position, List<Object> payloads);
        void onItemClicked(int position);
        void onItemLongClicked(int position);
        void update(List<T> data);
        void subscribe(ListView<T> view, DetailView<T> detailView);
        void unsubscribe();
        @Nullable
        T getDataAt(int position);
    }

    interface View<T> {
        void showList(List<T> data);
        void showError();
    }

    interface Presenter<T> {
        void subscribe(View<T> view, Repo<T> repo, Object... input);
        void unsubscribe();
    }

    interface Repo<T> {
        void addObserver(RepoObserver<T> observer);
        void destroy();
        void requestData(Object... input);
    }

    interface RepoObserver<T> {
        void onDataUpdated(List<T> result);
        void onError();
    }
}
