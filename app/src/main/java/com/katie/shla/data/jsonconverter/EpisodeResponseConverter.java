package com.katie.shla.data.jsonconverter;

import androidx.annotation.Nullable;

import com.katie.shla.data.models.Episode;
import com.katie.shla.data.models.EpisodeResponse;
import com.katie.shla.utils.Injector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EpisodeResponseConverter implements JsonConverter<EpisodeResponse> {

    private final JsonConverter<Episode> episodeConverter = Injector.getEpisodeJsonConverter();

    @Nullable
    @Override
    public EpisodeResponse getObject(JSONObject jsonInput) {
        try {
            JSONArray episodeJsonArray = jsonInput.getJSONArray("results");
            ArrayList<Episode> episodeList = new ArrayList<>();
            for (int i = 0; i < episodeJsonArray.length(); i++) {
                try {
                    JSONObject episodeJson = episodeJsonArray.getJSONObject(i);
                    Episode candidate = episodeConverter.getObject(episodeJson);
                    if (candidate != null) {
                        episodeList.add(candidate);
                    }
                } catch (Exception e) {
                    // ignore
                }
            }

            // get next url
            JSONObject infoObj = jsonInput.getJSONObject("info");
            String nextUrl = infoObj.optString("next");

            return new EpisodeResponse(episodeList, nextUrl);
        } catch (Exception e) {
            return null;
        }
    }
}
