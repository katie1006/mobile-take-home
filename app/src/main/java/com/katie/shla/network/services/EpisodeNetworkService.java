package com.katie.shla.network.services;

import android.os.AsyncTask;

import com.katie.shla.data.models.Episode;
import com.katie.shla.data.models.EpisodeResponse;
import com.katie.shla.data.tasks.ResponseJsonTask;
import com.katie.shla.network.tasks.DownloadTask;
import com.katie.shla.utils.AsyncCallback;
import com.katie.shla.utils.Injector;

import java.util.ArrayList;
import java.util.List;

public class EpisodeNetworkService {

    private final ArrayList<AsyncCallback<Episode>> callbacks = new ArrayList<>();
    private final ArrayList<AsyncTask> pendingDownloads = new ArrayList<>();
    // todo: obtain this value from api
    private String nextUrl = "https://rickandmortyapi.com/api/episode";

    public void addCallback(AsyncCallback<Episode> callback) {
        callbacks.add(callback);
    }

    public void getEpisodes() {
        if (nextUrl == null || nextUrl.isEmpty()) {
            for (AsyncCallback<Episode> callback : callbacks) {
                callback.onFinish();
            }
            return;
        }

        final DownloadTask<String> downloadTask = Injector.getStringDownloadTask();
        AsyncCallback<String> downloadCallback = new AsyncCallback<String>() {
            @Override
            public void onResult(List<String> downloadResult) {
                final ResponseJsonTask<EpisodeResponse> parseTask = Injector.getEpisodeResponseParseTask();
                AsyncCallback<EpisodeResponse> parseCallback = new AsyncCallback<EpisodeResponse>() {
                    @Override
                    public void onResult(List<EpisodeResponse> result) {
                        // notify callbacks about repo
                        for (AsyncCallback<Episode> callback : callbacks) {
                            callback.onResult(result.get(0).episodes);
                        }
                        nextUrl = result.get(0).nextUrl;
                    }

                    @Override
                    public void onError() {
                        notifyCallbackError();
                    }

                    @Override
                    public void onFinish() {
                        notifyCallbackFinish();
                        pendingDownloads.remove(parseTask);
                    }
                };
                parseTask.setCallback(parseCallback);
                parseTask.execute(downloadResult.get(0));
            }

            @Override
            public void onError() {
                notifyCallbackError();
            }

            @Override
            public void onFinish() {
                // ignore, parsing might not finish yet
                pendingDownloads.remove(downloadTask);
            }
        };
        downloadTask.setCallback(downloadCallback);
        downloadTask.execute(nextUrl);
        pendingDownloads.add(downloadTask);
    }

    public void disconnect() {
        for (AsyncTask task : pendingDownloads) {
            task.cancel(true);
        }
        pendingDownloads.clear();
    }

    private void notifyCallbackError() {
        for (AsyncCallback<Episode> callback : callbacks) {
            callback.onError();
        }
    }

    private void notifyCallbackFinish() {
        for (AsyncCallback<Episode> callback : callbacks) {
            callback.onFinish();
        }
    }
}
