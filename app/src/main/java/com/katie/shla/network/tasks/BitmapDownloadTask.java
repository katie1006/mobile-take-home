package com.katie.shla.network.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.InputStream;

public class BitmapDownloadTask extends DownloadTask<Bitmap> {
    @Nullable
    @Override
    Bitmap processInputStream(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }
}
