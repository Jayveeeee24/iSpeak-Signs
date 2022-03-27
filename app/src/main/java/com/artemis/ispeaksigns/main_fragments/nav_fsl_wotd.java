package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class nav_fsl_wotd extends Fragment {
    View view;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fsl_wotd, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String word;
        ScrollView fslWotdParent = view.findViewById(R.id.fsl_wotd_parent);
        fslWotdParent.setNestedScrollingEnabled(false);

        if (getArguments() != null)
        {
            word = getArguments().getString("fsl_wotd");
        }
        final ImageView fslFavorite = (ImageView) view.findViewById(R.id.fsl_favorite);
        fslFavorite.setOnClickListener(new View.OnClickListener() {
            int isFavorite = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                if (isFavorite == 0) {
                    fslFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                } else if (isFavorite == 1) {
                    fslFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                }
            }
        });
    }
}

//              Setting a title from a fragment
//            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
//                    .setTitle(word + " - " + getResources().getString(R.string.fsl_label));