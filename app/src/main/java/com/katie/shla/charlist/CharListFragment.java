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
import com.katie.shla.utils.list.BaseListPresenter;

import java.util.List;

public class CharListFragment extends BaseFragment implements ListContract.View<Character>, ListContract.DetailView<Character> {

    public static final String TAG = "char_list";
    public static final String ARG_CHAR_URLS = "char_urls";

    private ListContract.Presenter<Character> presenter;
    private ListContract.ListView<Character> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListParentPresenter<>();
    }

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

        presenter.subscribe(this, Injector.getListRepoCharacter(), urls);
        adapter.subscribe(this, Injector.<Character>getListPresenter());

        return root;
    }

    @Override
    public void showDetail(Character data) {
        if (navigator != null) {
            navigator.showCharacterDetail(data);
        }
    }

    @Override
    public void showList(List<Character> data) {
        if (navigator != null) {
            navigator.hideLoading();
        }
        adapter.updateList(data);
    }
}
