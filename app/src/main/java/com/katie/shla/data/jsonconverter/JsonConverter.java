package com.katie.shla.data.jsonconverter;

import androidx.annotation.Nullable;

import org.json.JSONObject;

public interface JsonConverter<T> {
    @Nullable
    T getObject(JSONObject jsonInput);
}
