package com.katie.shla.network;

import android.net.NetworkInfo;

import androidx.annotation.Nullable;

public interface NetworkInfoProvider {
    /**
     * Get the device's active network status in the form of a NetworkInfo object.
     */
    @Nullable
    NetworkInfo getActiveNetworkInfo();
}
