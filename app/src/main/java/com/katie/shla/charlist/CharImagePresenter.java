package com.katie.shla.charlist;

import com.katie.shla.data.models.Character;
import com.katie.shla.imagecache.ImageRepository;
import com.katie.shla.utils.Injector;

public class CharImagePresenter implements CharacterContract.ImagePresenter {
    private ImageRepository imageRepo = Injector.getImageRepo();

    @Override
    public void requestImageLoading(ImageRepository.ImageHolder target, String url) {
        imageRepo.loadImage(target, url);
    }

    @Override
    public void cancelImageLoading(Character data) {
        imageRepo.cancelImageLoading(data.imageUrl);
    }
}
