package com.katie.shla.charlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.list.BaseListAdapter;
import com.katie.shla.utils.list.ListViewHolder;

public class CharListAdapter extends BaseListAdapter<Character> {
    @NonNull
    @Override
    public ListViewHolder<Character> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CharacterViewHolder(inflater.inflate(R.layout.vh_character, parent, false));
    }
}
