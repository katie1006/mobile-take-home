package com.katie.shla.chardetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.katie.shla.R;
import com.katie.shla.utils.BaseFragment;

public class CharDetailFragment extends BaseFragment {

    public static final String TAG = "char_detail";
    public static final String ARG_CHARACTER_ID = "character_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_char_detail, container, false);
        return root;
    }
}
