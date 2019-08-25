package com.katie.shla.network.services;

import com.katie.shla.data.models.Episode;
import com.katie.shla.data.models.EpisodeResponse;
import com.katie.shla.data.tasks.ResponseJsonTask;
import com.katie.shla.utils.Injector;

import java.util.List;

public class EpisodeNetworkService extends StringNetworkService<Episode, EpisodeResponse> {

    // todo: obtain this value from api
    private String nextUrl = "https://rickandmortyapi.com/api/episode";

    @Override
    public List<Episode> processResult(List<EpisodeResponse> parsedResult) {
        nextUrl = parsedResult.get(0).nextUrl;
        return parsedResult.get(0).episodes;
    }

    @Override
    public ResponseJsonTask<EpisodeResponse> getParseTask() {
        return Injector.getEpisodeResponseParseTask();
    }

    public void getEpisodes() {
        if (nextUrl == null || nextUrl.isEmpty()) {
            notifyCallbackFinish();
            return;
        }
        startDownload(nextUrl);
    }

}
