package com.katie.shla.data.tasks;

import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.utils.AsyncCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
                    Object candidateJson = new JSONTokener(input).nextValue();
                    if (candidateJson instanceof JSONObject) {
                        T candidate = jsonConverter.getObject((JSONObject) candidateJson);
                        if (candidate != null) {
                            results.add(candidate);
                        }
                    } else if (candidateJson instanceof JSONArray) {
                        results.addAll(jsonConverter.getArray((JSONArray) candidateJson));
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
