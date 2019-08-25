package com.katie.shla.data.jsonconverter;

import androidx.annotation.Nullable;

import com.katie.shla.data.models.Episode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EpisodeConverter implements JsonConverter<Episode> {

    @Override
    @Nullable
    public Episode getObject(JSONObject jsonInput) {
        try {
            // required
            int id = jsonInput.getInt("id");
            String name = jsonInput.getString("name");
            String episodeCode = jsonInput.getString("episode");
            String url = jsonInput.getString("url");

            JSONArray characterJsonArray = jsonInput.getJSONArray("characters");
            ArrayList<String> characterList = new ArrayList<>();
            for (int i = 0; i < characterJsonArray.length(); i++) {
                String charUrl = characterJsonArray.optString(i);
                if (!charUrl.isEmpty()) {
                    characterList.add(charUrl);
                }
            }

            // optional
            String airDate = jsonInput.getString("air_date");

            return new Episode(id, name, airDate, episodeCode, characterList.toArray(new String[0]), url);
        } catch (Exception e) {
            return null;
        }
    }
}
