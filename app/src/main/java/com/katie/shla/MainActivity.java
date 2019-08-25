package com.katie.shla;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.katie.shla.chardetail.CharDetailFragment;
import com.katie.shla.charlist.CharListFragment;
import com.katie.shla.data.models.Character;
import com.katie.shla.episode.EpisodeFragment;
import com.katie.shla.utils.Injector;
import com.katie.shla.utils.NavigationProvider;


public class MainActivity extends FragmentActivity implements NavigationProvider {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View loader;
    private View fragmentPH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.init(this);

        setContentView(R.layout.activity_main);

        loader = findViewById(R.id.loader);
        fragmentPH = findViewById(R.id.fragment_placeholder);

        EpisodeFragment episodeFragment = new EpisodeFragment();
        episodeFragment.setNavigationProvider(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_placeholder, episodeFragment, EpisodeFragment.TAG)
                .commit();
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
        fragmentPH.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showCharacterList(String[] urls) {
        CharListFragment charListFrag = new CharListFragment();
        charListFrag.setNavigationProvider(this);
        Bundle bundle = new Bundle();
        bundle.putStringArray(CharListFragment.ARG_CHAR_URLS, urls);
        charListFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_placeholder, charListFrag, CharListFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showCharacterDetail(Character charData) {
        CharDetailFragment charDetailFrag = new CharDetailFragment();
        charDetailFrag.setNavigationProvider(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CharDetailFragment.ARG_CHARACTER_DATA, charData);
        charDetailFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_placeholder, charDetailFrag, CharDetailFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
        fragmentPH.setVisibility(View.VISIBLE);
    }
}
