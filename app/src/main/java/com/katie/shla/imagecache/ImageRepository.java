package com.katie.shla.imagecache;

import android.graphics.Bitmap;

public interface ImageRepository {
    void loadImage(ImageHolder target, final String targetUrl);
    void cancelImageLoading(String targetUrl);

    interface ImageHolder {
        void setImageBitmap(Bitmap bitmap);
        void setImageResource(int resId);
    }
}
