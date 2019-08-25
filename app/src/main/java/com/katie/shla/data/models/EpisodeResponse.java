package com.katie.shla.data.models;

import java.util.List;

public class EpisodeResponse {
    public final List<Episode> episodes;
    public final String nextUrl;

    public EpisodeResponse(List<Episode> episodes, String nextUrl) {
        this.episodes = episodes;
        this.nextUrl = nextUrl;
    }
}
