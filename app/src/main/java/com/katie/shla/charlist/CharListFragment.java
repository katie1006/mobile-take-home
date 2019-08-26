package com.katie.shla.charlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.BaseFragment;
import com.katie.shla.utils.Injector;
import com.katie.shla.utils.list.ListContract;
import com.katie.shla.utils.list.ListParentPresenter;

import java.util.List;

public class CharListFragment extends BaseFragment implements ListContract.View<Character> {

    public static final String TAG = "char_list";
    public static final String ARG_CHAR_URLS = "char_urls";

    private ListContract.ListView<Character> adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView root = (RecyclerView) inflater.inflate(R.layout.fragment_char_list, container, false);

        CharListAdapter listAdapter = new CharListAdapter();
        adapter = listAdapter;
        root.setAdapter(listAdapter);
        root.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] urls = null;
        Bundle arg = getArguments();
        if (arg != null) {
            urls = arg.getStringArray(ARG_CHAR_URLS);
        }

        if (navigator != null) {
            navigator.showLoading();
        }

        new ListParentPresenter<Character>().subscribe(this, Injector.getListRepoCharacter(), urls);
        CharacterListPresenter listPresenter = new CharacterListPresenter();
        listPresenter.bindRepo(Injector.getCharacterPool());
        adapter.subscribe(null, listPresenter);

        return root;
    }

    @Override
    public void showList(List<Character> data) {
        if (navigator != null) {
            navigator.hideLoading();
        }
        adapter.updateList(data);
    }
}
