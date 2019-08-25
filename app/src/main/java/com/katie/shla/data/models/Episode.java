package com.katie.shla.data.models;

import java.util.Date;

public class Episode {
    public final int id;
    public final String name;
    public final String airDate;
    public final String episodeCode;
    public final String[] charUrls;
    public final String url;

    public Episode(int id, String name, String airDate, String episodeCode, String[] charUrls, String url) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episodeCode = episodeCode;
        this.charUrls = charUrls;
        this.url = url;
    }
}
