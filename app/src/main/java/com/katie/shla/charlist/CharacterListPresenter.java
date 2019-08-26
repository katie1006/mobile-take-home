package com.katie.shla.charlist;

import com.katie.shla.data.models.Character;
import com.katie.shla.utils.list.BaseListPresenter;

import java.util.List;

public class CharacterListPresenter extends BaseListPresenter<Character> {

    private CharacterContract.CharacterPool repository;

    public void bindRepo(CharacterContract.CharacterPool repo) {
        this.repository = repo;
    }

    @Override
    public void update(List<Character> data) {
        if (repository != null) {
            repository.updateCharacterStatus(data);
        }

        super.update(data);
    }

    @Override
    public void onItemLongClicked(int position) {
        super.onItemLongClicked(position);
        Character candidate = getDataAt(position);
        if (candidate != null) {
            repository.notifyCharacterKilled(candidate.id);
            candidate.status = Character.Status.DEAD;
            view.updateItem(position, candidate);
        }
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        repository = null;
    }
}
