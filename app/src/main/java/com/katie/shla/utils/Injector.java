package com.katie.shla.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.katie.shla.data.jsonconverter.CharacterConverter;
import com.katie.shla.data.jsonconverter.EpisodeConverter;
import com.katie.shla.data.jsonconverter.EpisodeResponseConverter;
import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.data.models.Character;
import com.katie.shla.data.models.Episode;
import com.katie.shla.data.models.EpisodeResponse;
import com.katie.shla.data.tasks.EpisodeResponseJsonTask;
import com.katie.shla.data.tasks.ResponseJsonTask;
import com.katie.shla.imagecache.ImageCache;
import com.katie.shla.imagecache.SimpleImageCache;
import com.katie.shla.network.NetworkInfoProvider;
import com.katie.shla.network.tasks.BitmapDownloadTask;
import com.katie.shla.network.tasks.DownloadTask;
import com.katie.shla.network.tasks.StringDownloadTask;
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

    public static ResponseJsonTask<EpisodeResponse> getEpisodeResponseParseTask() {
        return new EpisodeResponseJsonTask();
    }

    public static ImageCache getImageCache() {
        return new SimpleImageCache();
    }

    public static NetworkInfoProvider getNetworkInfoProvider() {
        return new NetworkInfoProviderImpl(appContext);
    }

    public static JsonConverter<Episode> getEpisodeJsonConverter() {
        return new EpisodeConverter();
    }

    public static JsonConverter<Character> getCharacterJsonConverter() {
        return new CharacterConverter();
    }

    public static JsonConverter<EpisodeResponse> getEpisodeResponseJsonConverter() {
        return new EpisodeResponseConverter();
    }

    public static EpisodeNetworkService getEpisodeNetworkService() {
        return new EpisodeNetworkService();
    }
}
