package com.katie.shla.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.katie.shla.data.jsonconverter.CharacterConverter;
import com.katie.shla.data.jsonconverter.EpisodeConverter;
import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.data.models.Character;
import com.katie.shla.data.models.Episode;
import com.katie.shla.imagecache.ImageCache;
import com.katie.shla.imagecache.SimpleImageCache;
import com.katie.shla.network.BitmapDownloadTask;
import com.katie.shla.network.DownloadCallback;
import com.katie.shla.network.DownloadTask;
import com.katie.shla.network.StringDownloadTask;
import com.katie.shla.network.services.EpisodeNetworkService;

public class Injector {
    private static Context appContext = null;
    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static DownloadTask<String> getStringDownloadTask() {
        return new StringDownloadTask();
    }

    public static DownloadTask<Bitmap> getImageDownloadTask() {
        return new BitmapDownloadTask();
    }

    public static ImageCache getImageCache() {
        return new SimpleImageCache();
    }

    public static DownloadCallback.NetworkInfoProvider getNetworkInfoProvider() {
        return new NetworkInfoProviderImpl(appContext);
    }

    public static JsonConverter<Episode> getEpisodeJsonConverter() {
        return new EpisodeConverter();
    }

    public static JsonConverter<Character> getCharacterJsonConverter() {
        return new CharacterConverter();
    }

    public static EpisodeNetworkService getEpisodeNetworkService() {
        return new EpisodeNetworkService();
    }
}
