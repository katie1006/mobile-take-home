package com.katie.shla.utils.list;

import androidx.annotation.Nullable;

import java.util.List;

public interface ListContract {
    interface ListView<T> {
        void subscribe(ListPresenter<T> presenter);
        void unsubscribe();
        void updateList(List<T> data);
        void updateItem(int position, T data);
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
        void subscribeView(ListView<T> view);
        void subscribeParent(ListContract.Presenter<T> parentPresenter);
        void unsubscribe();
        void onNextPage();
        @Nullable
        T getDataAt(int position);
    }

    interface View<T> {
        void showList(List<T> data);
        void showError();
        void showDetail(T data);
    }

    interface Presenter<T> {
        void subscribe(View<T> view, Repo<T> repo, Object... input);
        void unsubscribe();
        void onDataItemClicked(T data);
        void requestNextPage();
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
