package com.katie.shla.imagecache;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.katie.shla.R;
import com.katie.shla.network.tasks.BitmapDownloadTask;
import com.katie.shla.utils.AsyncCallback;
import com.katie.shla.utils.Injector;

import java.util.HashMap;
import java.util.List;

public class SimpleImageRepo implements ImageRepository {
    private final ImageCache cache = Injector.getImageCache();
    private final HashMap<String, LoadingTaskWrapper> pendingTasks = new HashMap<>();

    public void loadImage(ImageHolder target, final String targetUrl) {
        Bitmap cachedImage = cache.getBitmap(targetUrl);
        if (cachedImage == null) {
            // need to download
            BitmapDownloadTask task = new BitmapDownloadTask();
            task.setCallback(new AsyncCallback<Bitmap>() {
                @Override
                public void onResult(List<Bitmap> result) {
                    if (result.isEmpty()) {
                        return;
                    }

                    // add to cache
                    cache.addBitmap(targetUrl, result.get(0));
                    // load to image view
                    LoadingTaskWrapper wrapper = pendingTasks.get(targetUrl);
                    if (wrapper != null) {
                        wrapper.target.setImageBitmap(result.get(0));
                        // remove task as its finished
                        pendingTasks.remove(targetUrl);
                    }
                }

                @Override
                public void onError() {
                    // load blank to image view
                    LoadingTaskWrapper wrapper = pendingTasks.get(targetUrl);
                    if (wrapper != null) {
                        wrapper.target.setImageResource(R.drawable.error);
                        // remove task as its finished
                        pendingTasks.remove(targetUrl);
                    }
                }

                @Override
                public void onFinish() { }
            });
            task.execute(targetUrl);
            pendingTasks.put(targetUrl, new LoadingTaskWrapper(target, task));
        } else {
            target.setImageBitmap(cachedImage);
        }
    }

    public void cancelImageLoading(String targetUrl) {
        LoadingTaskWrapper wrapper = pendingTasks.get(targetUrl);
        if (wrapper != null) {
            wrapper.downloadTask.cancel(true);
            pendingTasks.remove(targetUrl);
        }
    }

    private static class LoadingTaskWrapper {
        final ImageHolder target;
        final AsyncTask downloadTask;

        public LoadingTaskWrapper(ImageHolder target, AsyncTask downloadTask) {
            this.target = target;
            this.downloadTask = downloadTask;
        }
    }
}
