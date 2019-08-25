package com.katie.shla.episode;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Episode;
import com.katie.shla.utils.list.ListViewHolder;

public class EpisodeViewHolder extends ListViewHolder<Episode> {

    private final TextView name;

    public EpisodeViewHolder(@NonNull View root) {
        super(root);
        name = root.findViewById(R.id.name);
    }

    @Override
    public void bind(Episode data) {
        name.setText(data.name);
    }
}
