package com.katie.shla.utils;

import android.graphics.Bitmap;

import com.katie.shla.imagecache.ImageCache;
import com.katie.shla.imagecache.SimpleImageCache;
import com.katie.shla.network.BitmapDownloadTask;
import com.katie.shla.network.DownloadTask;
import com.katie.shla.network.StringDownloadTask;

public class Injector {
    public static DownloadTask<String> getStringDownloadTask() {
        return new StringDownloadTask();
    }

    public static DownloadTask<Bitmap> getImageDownloadTask() {
        return new BitmapDownloadTask();
    }

    public static ImageCache getImageCache() {
        return new SimpleImageCache();
    }
}
