package com.katie.shla.data.tasks;

import com.katie.shla.data.jsonconverter.JsonConverter;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.Injector;

public class CharacterResponseJsonTask extends ResponseJsonTask<Character> {

    @Override
    JsonConverter<Character> getJsonConverter() {
        return Injector.getCharacterJsonConverter();
    }
}
