package com.katie.shla.data.jsonconverter;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface JsonConverter<T> {
    @Nullable
    T getObject(JSONObject jsonInput);
    List<T> getArray(JSONArray arrayInput);
}
