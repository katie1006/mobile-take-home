package com.katie.shla.episode;

import com.katie.shla.data.models.Episode;
import com.katie.shla.utils.list.BaseListPresenter;

import java.util.List;

public class EpisodeListPresenter extends BaseListPresenter<Episode> {
    @Override
    public void update(List<Episode> data) {
        // do not clear, append
        this.data.addAll(data);
    }
}
