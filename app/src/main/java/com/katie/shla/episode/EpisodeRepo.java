package com.katie.shla.episode;

import com.katie.shla.data.models.Episode;
import com.katie.shla.network.services.EpisodeNetworkService;
import com.katie.shla.utils.Injector;
import com.katie.shla.utils.list.ListRepo;

public class EpisodeRepo extends ListRepo<Episode, EpisodeNetworkService> {


    public EpisodeRepo() {
        super(Injector.getEpisodeNetworkService());
    }

    @Override
    public void requestData(Object... input) {
        service.getEpisodes();
    }

    @Override
    public void destroy() {
        super.destroy();
        service.disconnect();
    }
}
