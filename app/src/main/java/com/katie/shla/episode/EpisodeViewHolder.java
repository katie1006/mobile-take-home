package com.katie.shla.episode;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Episode;
import com.katie.shla.utils.list.ListViewHolder;

public class EpisodeViewHolder extends ListViewHolder<Episode> {

    private final TextView name;
    private final TextView episode;
    private final TextView airDate;
    private final TextView numCharacters;

    public EpisodeViewHolder(@NonNull View root) {
        super(root);
        name = root.findViewById(R.id.name);
        episode = root.findViewById(R.id.episode);
        airDate = root.findViewById(R.id.airDate);
        numCharacters = root.findViewById(R.id.numCharacters);
    }

    @Override
    public void bind(Episode data) {
        name.setText(data.name);
        episode.setText(data.episodeCode);
        airDate.setText(data.airDate);
        numCharacters.setText(numCharacters.getResources().getString(R.string.num_character, data.charUrls.length));
    }
}
