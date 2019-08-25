package com.katie.shla.utils;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.katie.shla.R;

public class BaseFragment extends Fragment {
    protected NavigationProvider navigator;

    public void setNavigationProvider(NavigationProvider provider) {
        navigator = provider;
    }

    public void showError() {
        if (navigator != null) {
            navigator.hideLoading();
        }
        Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        navigator = null;
    }
}
