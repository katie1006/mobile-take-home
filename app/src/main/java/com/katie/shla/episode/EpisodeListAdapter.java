package com.katie.shla.episode;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Episode;
import com.katie.shla.utils.list.ListAdapter;
import com.katie.shla.utils.list.ListViewHolder;

public class EpisodeListAdapter extends ListAdapter<Episode> {

    @NonNull
    @Override
    public ListViewHolder<Episode> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EpisodeViewHolder(inflater.inflate(R.layout.vh_episode, parent, false));
    }
}
