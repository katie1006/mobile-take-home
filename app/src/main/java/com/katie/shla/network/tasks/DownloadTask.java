package com.katie.shla.network.tasks;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.katie.shla.network.NetworkInfoProvider;
import com.katie.shla.utils.AsyncCallback;
import com.katie.shla.utils.AsyncResult;
import com.katie.shla.utils.Injector;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Implementation of AsyncTask designed to fetch repo from the network.
 */
public abstract class DownloadTask<T> extends AsyncTask<String, Integer, AsyncResult<T>> {

    private NetworkInfoProvider networkInfoProvider;

    @Nullable
    private AsyncCallback<T> callback;

    public void setCallback(@Nullable AsyncCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    @Override
    protected void onPreExecute() {
        networkInfoProvider = Injector.getNetworkInfoProvider();
        if (callback != null && !isConnected()) {
            // If no connectivity, cancel task and update Callback with null repo.
            callback.onFinish();
            cancel(true);
        }
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = networkInfoProvider == null ? null : networkInfoProvider.getActiveNetworkInfo();
        return networkInfo != null &&
                networkInfo.isConnected() &&
                (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Defines work to perform on the background thread.
     */
    @Override
    protected AsyncResult<T> doInBackground(String... urls) {
        AsyncResult<T> resultWrapper = new AsyncResult<>();
        for (String urlString : urls) {
            if (isCancelled() || !isConnected()) {
                break;
            }

            try {
                URL url = new URL(urlString);
                T result = downloadUrl(url);
                if (result != null) {
                    resultWrapper.results.add(result);
                }
            } catch (Exception e) {
                resultWrapper.exception = e;
            }
        }
        return resultWrapper;
    }

    /**
     * Updates the DownloadCallback with the result.
     */
    @Override
    protected void onPostExecute(AsyncResult<T> result) {
        if (callback != null) {
            if (result.results.isEmpty()) {
                callback.onError();
            } else {
                try {
                    callback.onResult(result.results);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            callback.onFinish();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback = null;
        networkInfoProvider = null;
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
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
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
