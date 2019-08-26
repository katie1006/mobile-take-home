package com.katie.shla.charlist;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.katie.shla.R;
import com.katie.shla.data.models.Character;
import com.katie.shla.utils.list.ListViewHolder;

public class CharacterViewHolder extends ListViewHolder<Character> {

    private final View detail;
    private final View basic;
    
    private final TextView status;
    private final TextView species;
    private final TextView gender;
    private final TextView origin;
    private final TextView lastLocation;

    private final ImageView avatar;
    private String avatarUrl;

    private final CharacterBasicInfoViewHolder basicVHFront;
    private final CharacterBasicInfoViewHolder basicVHBack;

    public CharacterViewHolder(@NonNull View root) {
        super(root);
        detail = root.findViewById(R.id.detail_wrapper);
        basic = root.findViewById(R.id.basic_wrapper);
        
        status = root.findViewById(R.id.status);
        species = root.findViewById(R.id.species);
        gender = root.findViewById(R.id.gender);
        origin = root.findViewById(R.id.origin);
        lastLocation = root.findViewById(R.id.location);

        avatar = root.findViewById(R.id.avatar);

        basicVHFront = new CharacterBasicInfoViewHolder(basic);
        basicVHBack = new CharacterBasicInfoViewHolder(detail);
    }

    @Override
    public void bind(Character data) {
        basicVHFront.bind(data);
        basicVHBack.bind(data);

        avatarUrl = data.imageUrl;

        status.setText(data.status.name());
        species.setText(data.species);
        gender.setText(data.gender);
        origin.setText(data.origin);
        lastLocation.setText(data.location);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo: load avatar
                flip();
            }
        });
    }

    private void flip() {
        final AnimatorSet outAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.card_flip_out);
        final AnimatorSet inAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.card_flip_in);

        Transition transition = new ChangeBounds();
        // same value as the in/out animation
        transition.setDuration(250);
        transition.addTarget(itemView);
        TransitionManager.beginDelayedTransition(((ViewGroup) itemView.getParent()), transition);
        if (detail.getVisibility() == View.GONE) {
            // detail is not visible, flip to back
            detail.setVisibility(View.VISIBLE);
            inAnimation.setTarget(detail);
            detail.post(new Runnable() {
                @Override
                public void run() {
                    inAnimation.start();
                }
            });

            outAnimation.setTarget(basic);
            outAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    basic.setVisibility(View.GONE);
                }
            });
            outAnimation.start();
        } else {
            // flip to front
            basic.setVisibility(View.VISIBLE);
            inAnimation.setTarget(basic);
            basic.post(new Runnable() {
                @Override
                public void run() {
                    inAnimation.start();
                }
            });

            outAnimation.setTarget(detail);
            outAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    detail.setVisibility(View.GONE);
                }
            });
            outAnimation.start();
        }
    }
}
