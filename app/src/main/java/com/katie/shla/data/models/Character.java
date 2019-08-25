package com.katie.shla.data.models;

import java.io.Serializable;

public class Character implements Serializable {
    public enum Status {
        ALIVE,
        DEAD,
        UNKNOWN
    }

    public final int id;
    public final String name;
    public final Status status;
    public final String species;
    public final String type;
    public final String gender;
    public final String origin;
    public final String location;
    public final String imageUrl;
    public final String[] episodeUrls;
    public final String url;

    public Character(int id, String name, Status status, String species, String type, String gender, String origin, String location, String imageUrl, String[] episodeUrls, String url) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.imageUrl = imageUrl;
        this.episodeUrls = episodeUrls;
        this.url = url;
    }
}
