package com.katie.shla.utils.list;

import java.util.List;

public interface ListContract {
    interface View<T> {
        void subscribe(ListContract.DetailView<T> detailView);
        void unsubscribe();
        void updateList(List<T> data);
    }

    interface DetailView<T> {
        void showDetail(T data);
    }

    interface ItemView<T> {
        void bind(T data);
    }

    interface Presenter<T> {
        int getItemCount();
        void onBindItemView(ItemView<T> itemView, int position);
        void onItemClicked(int position);
        void update(List<T> data);
        void subscribe(View<T> view, DetailView<T> detailView);
        void unsubscribe();
    }
}
