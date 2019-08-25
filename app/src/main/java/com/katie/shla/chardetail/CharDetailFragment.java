package com.katie.shla.chardetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.BaseFragment;

public class CharDetailFragment extends BaseFragment {

    public static final String TAG = "char_detail";
    public static final String ARG_CHARACTER_DATA = "character_data";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_char_detail, container, false);
        Bundle arg = getArguments();
        if (arg != null) {
            Character data = (Character) arg.getSerializable(ARG_CHARACTER_DATA);
            ((TextView) root.findViewById(R.id.test_text)).setText(data.name + ", " + data.gender);
        }


        return root;
    }
}
