package com.katie.shla.network.services;

import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.data.models.Character;
import com.katie.shla.data.tasks.ResponseJsonTask;
import com.katie.shla.utils.Injector;

import java.util.List;

public class CharacterNetworkService extends StringNetworkService<Character, Character> {
    private final JsonConverter<Character> jsonConverter = Injector.getCharacterJsonConverter();

    @Override
    public List<Character> processResult(List<Character> parsedResult) {
        return parsedResult;
    }

    @Override
    public ResponseJsonTask<Character> getParseTask() {
        return Injector.getCharacterResponseParseTask();
    }

    public void getCharacters(String[] urls) {
        if (urls.length == 0) {
            notifyCallbackFinish();
            return;
        }

        // obtain a list of ids from input urls
        StringBuilder urlBuilder = new StringBuilder("https://rickandmortyapi.com/api/character/");
        for (String url : urls) {
            String[] urlSplit = url.split("/");
            urlBuilder.append(urlSplit[urlSplit.length - 1]).append(",");
        }
        // remove the last comma
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        startDownload(urlBuilder.toString());

    }
}
