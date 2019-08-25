package com.katie.shla.data.jsonconverter;

import androidx.annotation.Nullable;

import com.katie.shla.data.models.Character;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterConverter extends BaseJsonConverter<Character> {

    @Nullable
    @Override
    public Character getObject(JSONObject jsonInput) {
        try {
            // required
            int id = jsonInput.getInt("id");
            String name = jsonInput.getString("name");
            String imageUrl = jsonInput.getString("image");
            String url = jsonInput.getString("url");

            String statusString = jsonInput.getString("status");
            Character.Status status;
            switch (statusString) {
                case "Alive":
                    status = Character.Status.ALIVE;
                    break;
                case "Dead":
                    status = Character.Status.DEAD;
                    break;
                default:
                    status = Character.Status.UNKNOWN;
            }

            JSONArray episodeJsonArray = jsonInput.getJSONArray("episode");
            ArrayList<String> episodeList = new ArrayList<>();
            for (int i = 0; i < episodeJsonArray.length(); i++) {
                String charUrl = episodeJsonArray.optString(i);
                if (!charUrl.isEmpty()) {
                    episodeList.add(charUrl);
                }
            }

            // optional
            String species = jsonInput.optString("species");
            String type = jsonInput.optString("type");
            String gender = jsonInput.optString("gender");
            String origin = getLocation(jsonInput.optJSONObject("origin"));
            String location = getLocation(jsonInput.optJSONObject("location"));

            return new Character(id, name, status, species, type, gender, origin, location, imageUrl, episodeList.toArray(new String[0]), url);
        } catch (Exception e) {
            return null;
        }
    }

    private String getLocation(JSONObject jsonInput) {
        try {
            if (jsonInput != null) {
                return jsonInput.optString("name");
            }
        } catch (Exception e) {
            // ignore
        }

        return "";
    }
}
