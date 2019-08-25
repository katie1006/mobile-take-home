package com.katie.shla.charlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.katie.shla.R;
import com.katie.shla.utils.BaseFragment;

public class CharListFragment extends BaseFragment {

    public static final String TAG = "char_list";
    public static final String ARG_EPISODE_ID = "episode_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_char_list, container, false);
        root.findViewById(R.id.test_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigator != null) {
                    navigator.showCharacterDetail(1);
                }
            }
        });
        return root;
    }
}
