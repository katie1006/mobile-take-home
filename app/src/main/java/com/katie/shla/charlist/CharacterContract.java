package com.katie.shla.charlist;

import com.katie.shla.data.models.Character;
import com.katie.shla.imagecache.ImageRepository;

public interface CharacterContract {
    interface ImagePresenter {
        void requestImageLoading(ImageRepository.ImageHolder target, String url);
        void cancelImageLoading(Character data);
    }
}
