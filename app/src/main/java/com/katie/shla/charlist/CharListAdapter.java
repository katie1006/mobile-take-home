package com.katie.shla.charlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.list.ListAdapter;
import com.katie.shla.utils.list.ListViewHolder;

public class CharListAdapter extends ListAdapter<Character> {
    @NonNull
    @Override
    public ListViewHolder<Character> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CharacterViewHolder(inflater.inflate(R.layout.vh_episode, parent, false));
    }
}
