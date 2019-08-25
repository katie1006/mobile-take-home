package com.katie.shla.charlist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.list.ListViewHolder;

public class CharacterViewHolder extends ListViewHolder<Character> {

    private final TextView name;

    public CharacterViewHolder(@NonNull View root) {
        super(root);
        name = root.findViewById(R.id.name);
    }

    @Override
    public void bind(Character data) {
        name.setText(data.name);
    }
}
