package com.katie.shla.data.jsonconverter;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJsonConverter<T> implements JsonConverter<T> {
    @Nullable
    @Override
    public List<T> getArray(JSONArray arrayInput) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < arrayInput.length(); i++) {
            try {
                T candidate = getObject(arrayInput.getJSONObject(i));
                if (candidate != null) {
                    result.add(candidate);
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return result;
    }
}
