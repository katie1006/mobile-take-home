package com.katie.shla.utils.list;

import java.util.List;

public interface ListContract {
    interface ListView<T> {
        void subscribe(ListContract.DetailView<T> detailView, ListPresenter<T> presenter);
        void unsubscribe();
        void updateList(List<T> data);
    }

    interface DetailView<T> {
        void showDetail(T data);
    }

    interface ItemView<T> {
        void bind(T data);
    }

    interface ListPresenter<T> {
        int getItemCount();
        void onBindItemView(ItemView<T> itemView, int position);
        void onItemClicked(int position);
        void update(List<T> data);
        void subscribe(ListView<T> view, DetailView<T> detailView);
        void unsubscribe();
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
