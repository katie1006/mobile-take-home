package com.katie.shla.utils;

import com.katie.shla.data.models.Character;

public interface NavigationProvider {
    void showLoading();
    void hideLoading();
    void showCharacterList(String[] urls);
    void showCharacterDetail(Character charData);
}
