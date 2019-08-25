package com.katie.shla.network.services;

import android.os.AsyncTask;

import com.katie.shla.data.tasks.ResponseJsonTask;
import com.katie.shla.network.tasks.DownloadTask;
import com.katie.shla.utils.AsyncCallback;
import com.katie.shla.utils.Injector;

import java.util.ArrayList;
import java.util.List;

public abstract class StringNetworkService<T, R> {
    protected final ArrayList<AsyncCallback<T>> callbacks = new ArrayList<>();
    protected final ArrayList<AsyncTask> pendingDownloads = new ArrayList<>();

    public void addCallback(AsyncCallback<T> callback) {
        callbacks.add(callback);
    }

    protected void notifyCallbackResult(List<T> result) {
        for (AsyncCallback<T> callback : callbacks) {
            callback.onResult(result);
        }
    }

    protected void notifyCallbackFinish() {
        for (AsyncCallback<T> callback : callbacks) {
            callback.onFinish();
        }
    }

    protected void notifyCallbackError() {
        for (AsyncCallback<T> callback : callbacks) {
            callback.onError();
        }
    }

    public void disconnect() {
        for (AsyncTask task : pendingDownloads) {
            task.cancel(true);
        }
        pendingDownloads.clear();
    }

    protected void startDownload(String url) {
        final DownloadTask<String> downloadTask = Injector.getStringDownloadTask();
        AsyncCallback<String> downloadCallback = new AsyncCallback<String>() {
            @Override
            public void onResult(List<String> result) {
                final ResponseJsonTask<R> parseTask = getParseTask();
                AsyncCallback<R> parseCallback = new AsyncCallback<R>() {
                    @Override
                    public void onResult(List<R> result) {
                        notifyCallbackResult(processResult(result));
                    }

                    @Override
                    public void onError() {
                        notifyCallbackError();
                    }

                    @Override
                    public void onFinish() {
                        notifyCallbackFinish();
                        pendingDownloads.remove(parseTask);
                    }
                };
                parseTask.setCallback(parseCallback);
                parseTask.execute(result.get(0));
            }

            @Override
            public void onError() {
                notifyCallbackError();
            }

            @Override
            public void onFinish() {
                // don't notify, parsing might not finish yet
                pendingDownloads.remove(downloadTask);
            }
        };
        downloadTask.setCallback(downloadCallback);
        downloadTask.execute(url);
        pendingDownloads.add(downloadTask);
    }

    public abstract List<T> processResult(List<R> parsedResult);
    public abstract ResponseJsonTask<R> getParseTask();
}
