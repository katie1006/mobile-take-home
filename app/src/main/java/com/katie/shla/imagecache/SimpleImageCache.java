package com.katie.shla.imagecache;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

/**
 * Image memory cache built based on Google's example.
 */
public class SimpleImageCache implements ImageCache {
    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    private final int cacheSize = maxMemory / 8;

    private LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(@NonNull String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };

    @Override
    public void addBitmap(String key, Bitmap bitmap) {
        if (getBitmap(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    @Override
    @Nullable
    public Bitmap getBitmap(String key) {
        return memoryCache.get(key);
    }

}
