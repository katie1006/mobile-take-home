package com.katie.shla;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.katie.shla.charlist.CharListFragment;
import com.katie.shla.episode.EpisodeFragment;
import com.katie.shla.utils.Injector;
import com.katie.shla.utils.NavigationProvider;


public class MainActivity extends FragmentActivity implements NavigationProvider {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewGroup root;
    private Group loader;
    private View fragmentPH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.init(this);

        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
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
        // animate loading spinner in
        setLoadingAnimation(loader.getReferencedIds());
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
    public void hideLoading() {
        // animate loading spinner out
        setLoadingAnimation(loader.getReferencedIds());
        loader.setVisibility(View.GONE);

        fragmentPH.setVisibility(View.VISIBLE);
    }

    private void setLoadingAnimation(int[] targetIds) {
        Transition transition = new Fade();
        transition.setDuration(500);
        for (int target : targetIds) {
            transition.addTarget(target);
        }
        TransitionManager.beginDelayedTransition(root, transition);
    }
}
