package com.katie.shla.data.tasks;

import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.data.models.EpisodeResponse;
import com.katie.shla.utils.Injector;

public class EpisodeResponseJsonTask extends ResponseJsonTask<EpisodeResponse> {

    @Override
    JsonConverter<EpisodeResponse> getJsonConverter() {
        return Injector.getEpisodeResponseJsonConverter();
    }
}
