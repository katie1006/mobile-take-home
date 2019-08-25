package com.katie.shla.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import com.katie.shla.network.NetworkInfoProvider;

public class NetworkInfoProviderImpl implements NetworkInfoProvider {

    private Context appContext;

    public NetworkInfoProviderImpl(Context appContext) {
        this.appContext = appContext;
    }

    @Nullable
    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        } else {
            return null;
        }
    }
}
