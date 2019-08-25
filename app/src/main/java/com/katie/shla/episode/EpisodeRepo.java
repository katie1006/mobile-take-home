package com.katie.shla.episode;

import com.katie.shla.data.models.Episode;
import com.katie.shla.network.DownloadCallback;
import com.katie.shla.network.services.EpisodeNetworkService;
import com.katie.shla.utils.Injector;

import java.util.ArrayList;
import java.util.List;

public class EpisodeRepo implements DownloadCallback<List<Episode>>, EpisodeContract.Repo {

    private final EpisodeNetworkService service = Injector.getEpisodeNetworkService();
    private final ArrayList<EpisodeContract.RepoObserver> observers = new ArrayList<>();

    public EpisodeRepo() {
        service.addCallback(this);
    }

    @Override
    public void updateFromDownload(List<Episode> result) throws Exception {
        for (EpisodeContract.RepoObserver observer : observers) {
            observer.onDataUpdated(result);
        }
    }

    @Override
    public void onError(Exception e) {
        for (EpisodeContract.RepoObserver observer : observers) {
            observer.onError(e);
        }
    }

    @Override
    public void finishDownloading() { }

    @Override
    public void addObserver(EpisodeContract.RepoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void requestData() {
        service.getEpisodes();
    }

    @Override
    public void destroy() {
        observers.clear();
        service.disconnect();
    }
}
