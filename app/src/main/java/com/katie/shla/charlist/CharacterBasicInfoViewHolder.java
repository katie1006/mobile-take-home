package com.katie.shla.charlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;

public class CharacterBasicInfoViewHolder {
    private final ImageView statusIcon;
    private final TextView name;
    private final TextView totalAppearance;

    public CharacterBasicInfoViewHolder(View root) {
        statusIcon = root.findViewById(R.id.status_icon);
        name = root.findViewById(R.id.name);
        totalAppearance = root.findViewById(R.id.total_appearance);
    }

    public void bind(Character data) {
        if (data.status == Character.Status.DEAD) {
            statusIcon.setVisibility(View.VISIBLE);
            TextViewCompat.setTextAppearance(name, R.style.GeneralText);
            TextViewCompat.setTextAppearance(totalAppearance, R.style.GeneralText);
        } else {
            TextViewCompat.setTextAppearance(name, R.style.GeneralText_Alive);
            TextViewCompat.setTextAppearance(totalAppearance, R.style.GeneralText_Alive);
            statusIcon.setVisibility(View.GONE);
        }

        totalAppearance.setText(totalAppearance.getResources().getString(R.string.total_appearance, data.episodeUrls.length));
        name.setText(data.name);
    }
}
