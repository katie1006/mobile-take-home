package com.katie.shla.charlist;

import com.katie.shla.data.models.Character;
import com.katie.shla.network.services.CharacterNetworkService;
import com.katie.shla.utils.Injector;
import com.katie.shla.utils.list.ListRepo;

public class CharacterRepo extends ListRepo<Character, CharacterNetworkService> {

    public CharacterRepo() {
        super(Injector.getCharacterNetworkService());
    }

    @Override
    public void requestData(Object... input) {
        if (input instanceof String[]) {
            service.getCharacters((String[]) input);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        service.disconnect();
    }
}
