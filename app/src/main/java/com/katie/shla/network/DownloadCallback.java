package com.katie.shla.network;

import android.net.NetworkInfo;

import androidx.annotation.Nullable;

public interface DownloadCallback<T> {
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }

    interface NetworkInfoProvider {
        /**
         * Get the device's active network status in the form of a NetworkInfo object.
         */
        @Nullable
        NetworkInfo getActiveNetworkInfo();
    }

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     */
    void updateFromDownload(T result) throws Exception;

    void onError(Exception e);

    /**
     * Indicates that the download operation has finished. This method is called even if the
     * download hasn't completed successfully.
     */
    void finishDownloading();
}
