package com.katie.shla.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.katie.shla.utils.Injector;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Implementation of AsyncTask designed to fetch repo from the network.
 */
public abstract class DownloadTask<T> extends AsyncTask<String, Integer, DownloadTask.Result<T>> {

    DownloadCallback.NetworkInfoProvider networkInfoProvider = Injector.getNetworkInfoProvider();

    @Nullable
    private DownloadCallback<T> callback;

    public void setCallback(@Nullable DownloadCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * Wrapper class that serves as a union of a result value and an exception. When the download
     * task has completed, either the result value or exception can be a non-null value.
     * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
     */
    static class Result<T> {
        @Nullable
        T resultValue;
        @Nullable
        Exception exception;

        Result(@Nullable T resultValue) {
            this.resultValue = resultValue;
        }

        void setException(@Nullable Exception exception) {
            this.exception = exception;
        }
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    @Override
    protected void onPreExecute() {
        if (callback != null) {
            NetworkInfo networkInfo = networkInfoProvider.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                // If no connectivity, cancel task and update Callback with null repo.
                try {
                    callback.updateFromDownload(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cancel(true);
            }
        }
    }

    /**
     * Defines work to perform on the background thread.
     */
    @Override
    protected DownloadTask.Result<T> doInBackground(String... urls) {
        Result<T> resultWrapper = null;
        if (!isCancelled() && urls != null && urls.length > 0) {
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                T result = downloadUrl(url);
                if (result != null) {
                    resultWrapper = new Result<>(result);
                } else {
                    throw new IOException("No response received.");
                }
            } catch(Exception e) {
                resultWrapper = new Result<>(null);
                resultWrapper.setException(e);
            }
        }
        return resultWrapper;
    }

    /**
     * Updates the DownloadCallback with the result.
     */
    @Override
    protected void onPostExecute(Result<T> result) {
        if (result != null && callback != null) {
            if (result.exception != null) {
                callback.onError(result.exception);
            } else if (result.resultValue != null) {
                try {
                    callback.updateFromDownload(result.resultValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            callback.finishDownloading();
        }
    }

    /**
     * Override to add special behavior for cancelled AsyncTask.
     */
    @Override
    protected void onCancelled(Result result) {
    }

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    private T downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        T result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            // Timeout for reading InputStream arbitrarily set to 3000ms.
            connection.setReadTimeout(3000);
            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connection.setConnectTimeout(3000);
            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
            if (stream != null) {
                result = processInputStream(stream);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    @Nullable
    abstract T processInputStream(InputStream stream);

}
