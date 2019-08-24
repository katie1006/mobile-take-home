package com.katie.shla.imagecache;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public interface ImageCache {
    void addBitmap(String key, Bitmap bitmap);
    @Nullable
    Bitmap getBitmap(String key);
}
