package com.katie.shla.data.tasks;

import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.utils.AsyncCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class ResponseJsonTask<T> extends AsyncTask<String, Integer, List<T>> {

    protected JsonConverter<T> jsonConverter;

    @Nullable
    private AsyncCallback<T> callback;

    public void setCallback(@Nullable AsyncCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        jsonConverter = getJsonConverter();
    }

    abstract JsonConverter<T> getJsonConverter();

    @Override
    protected List<T> doInBackground(String... strings) {
        List<T> results = new ArrayList<>();
        for (String input : strings) {
            if (!isCancelled() && jsonConverter != null) {
                try {
                    JSONObject candidateJsonObj = new JSONObject(input);
                    T candidate = jsonConverter.getObject(candidateJsonObj);
                    if (candidate != null) {
                        results.add(candidate);
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(List<T> result) {
        if (callback != null) {
            if (!result.isEmpty()) {
                callback.onResult(result);
            } else {
                callback.onError();
            }
            callback.onFinish();
        }
    }
}
