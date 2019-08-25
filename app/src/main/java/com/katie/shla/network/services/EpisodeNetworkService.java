package com.katie.shla.network.services;

import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.data.models.Episode;
import com.katie.shla.network.DownloadCallback;
import com.katie.shla.network.DownloadTask;
import com.katie.shla.utils.Injector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpisodeNetworkService {
    private final JsonConverter<Episode> jsonConverter = Injector.getEpisodeJsonConverter();
    private final ArrayList<DownloadCallback<List<Episode>>> callbacks = new ArrayList<>();
    private final ArrayList<DownloadTask> pendingDownloads = new ArrayList<>();
    // todo: obtain this value from api
    private String nextUrl = "https://rickandmortyapi.com/api/episode";

    public void addCallback(DownloadCallback<List<Episode>> callback) {
        callbacks.add(callback);
    }

    public void getEpisodes() {
        if (nextUrl == null || nextUrl.isEmpty()) {
            for (DownloadCallback<List<Episode>> callback : callbacks) {
                callback.finishDownloading();
            }
            return;
        }

        final DownloadTask<String> task = Injector.getStringDownloadTask();
        DownloadCallback<String> downloadCallback = new DownloadCallback<String>() {
            @Override
            public void updateFromDownload(String result) throws Exception {
                // exceptions will be caught by try/catch in DownloadTask
                JSONObject resultJsonObj = new JSONObject(result);
                JSONArray episodeJsonArray = resultJsonObj.getJSONArray("results");
                ArrayList<Episode> episodeList = new ArrayList<>();
                for (int i = 0; i < episodeJsonArray.length(); i++) {
                    try {
                        JSONObject episodeJson = episodeJsonArray.getJSONObject(i);
                        Episode candidate = jsonConverter.getObject(episodeJson);
                        if (candidate != null) {
                            episodeList.add(candidate);
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }

                // get next url
                JSONObject infoObj = resultJsonObj.getJSONObject("info");
                nextUrl = infoObj.optString("next");

                // notify callbacks about repo
                for (DownloadCallback<List<Episode>> callback : callbacks) {
                    callback.updateFromDownload(episodeList);
                }
            }

            @Override
            public void onError(Exception e) {
                for (DownloadCallback<List<Episode>> callback : callbacks) {
                    callback.onError(e);
                }
            }

            @Override
            public void finishDownloading() {
                for (DownloadCallback<List<Episode>> callback : callbacks) {
                    callback.finishDownloading();
                }
                pendingDownloads.remove(task);
            }
        };
        task.setCallback(downloadCallback);
        task.execute(nextUrl);
        pendingDownloads.add(task);
    }

    public void disconnect() {
        for (DownloadTask task : pendingDownloads) {
            task.cancel(true);
        }
        pendingDownloads.clear();
    }
}
