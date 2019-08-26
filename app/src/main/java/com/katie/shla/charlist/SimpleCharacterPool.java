package com.katie.shla.charlist;

import com.katie.shla.data.models.Character;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleCharacterPool implements CharacterContract.CharacterPool {

    private final Set<Integer> killedCharIds = new HashSet<>();

    @Override
    public void notifyCharacterKilled(int charId) {
        killedCharIds.add(charId);
    }

    @Override
    public void updateCharacterStatus(List<Character> target) {
        for (Character candidate : target) {
            if (killedCharIds.contains(candidate.id)) {
                candidate.status = Character.Status.DEAD;
            }
        }
    }
}
