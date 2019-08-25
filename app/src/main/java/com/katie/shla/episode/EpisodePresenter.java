package com.katie.shla.episode;

import com.katie.shla.data.models.Episode;

import java.util.List;

public class EpisodePresenter implements EpisodeContract.Presenter, EpisodeContract.RepoObserver {

    private EpisodeContract.Repo repo;
    private EpisodeContract.View view;

    @Override
    public void subscribe(EpisodeContract.View view) {
        this.view = view;
        repo = new EpisodeRepo();
        repo.addObserver(this);
        repo.requestData();
    }

    @Override
    public void unsubscribe() {
        repo.destroy();
        repo = null;
        view = null;
    }

    @Override
    public void onDataUpdated(List<Episode> result) {
        if (view != null) {
            view.showEpisodeList(result);
        }
    }

    @Override
    public void onError(Exception e) {
        if (view != null) {
            view.showError();
        }
    }
}
