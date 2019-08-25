package com.katie.shla.episode;

import com.katie.shla.data.models.Episode;

import java.util.List;

public interface EpisodeContract {
    interface View {
        void showEpisodeList(List<Episode> episodes);
        void showError();
    }

    interface Presenter {
        void subscribe(View view);
        void unsubscribe();
    }

    interface Repo {
        void addObserver(EpisodeContract.RepoObserver observer);
        void requestData();
        void destroy();
    }

    interface RepoObserver {
        void onDataUpdated(List<Episode> result);
        void onError();
    }
}
