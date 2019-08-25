package com.katie.shla.episode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.katie.shla.R;
import com.katie.shla.data.models.Episode;
import com.katie.shla.utils.BaseFragment;
import com.katie.shla.utils.list.ListContract;

import java.util.List;

public class EpisodeFragment extends BaseFragment implements EpisodeContract.View, ListContract.DetailView<Episode> {

    public static final String TAG = "episode_list";

    private EpisodeContract.Presenter presenter;
    private ListContract.View<Episode> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EpisodePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView root = (RecyclerView) inflater.inflate(R.layout.fragment_episode, container, false);

        adapter = new EpisodeListAdapter();
        root.setAdapter((EpisodeListAdapter) adapter);
        root.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.subscribe(this);
        adapter.subscribe(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.unsubscribe();
        adapter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
        adapter = null;
    }

    @Override
    public void showEpisodeList(List<Episode> episodes) {
        if (navigator != null) {
            navigator.hideLoading();
        }
        adapter.updateList(episodes);
    }

    @Override
    public void showDetail(Episode data) {
        if (navigator != null) {
            navigator.showCharacterList(data.id);
        }
    }
}
