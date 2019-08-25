package com.katie.shla.utils;

public interface NavigationProvider {
    void showLoading();
    void hideLoading();
    void showCharacterList(int episodeId);
    void showCharacterDetail(int characterId);
}
