package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.R;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class nav_fsl_wotd extends Fragment {
    View view;
    Context context;
    MediaPlayer mediaFslAudio;
    FunctionHelper functionHelper;
    int audio;
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

        functionHelper = new FunctionHelper();
        String word;
        ScrollView fslWotdParent = view.findViewById(R.id.fsl_wotd_parent);
        fslWotdParent.setNestedScrollingEnabled(false);
        String audioName = "audio_demo";
        audio = getResources().getIdentifier(audioName, "raw", context.getPackageName());
        mediaFslAudio = MediaPlayer.create(context, audio);

        if (getArguments() != null)
        {
            word = getArguments().getString("fsl_wotd");
        }

        InitializeOnClick();


    }

    private void InitializeOnClick(){
        final ImageView fslFavorite = (ImageView) view.findViewById(R.id.fsl_favorite);
        final ImageView playAudio = (ImageView) view.findViewById(R.id.play_audio);

        fslFavorite.setOnClickListener(new View.OnClickListener() {
            int isFavorite = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                if (isFavorite == 0) {
                    fslFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                } else if (isFavorite == 1) {
                    fslFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                }
            }
        });


        playAudio.setOnClickListener(new View.OnClickListener() {
            int isPlayed = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                if (functionHelper.checkFocusGain(Objects.requireNonNull(getActivity()), mediaFslAudio, null)){
                    if (isPlayed == 0){
                        ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.outrageous_orange, null)));
                        mediaFslAudio.start();
                        isPlayed = 1;
                        mediaFslAudio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                                mediaFslAudio.stop();
                                mediaFslAudio = MediaPlayer.create(context, audio);
                                isPlayed = 0;
                            }
                        });
                    }else if (isPlayed == 1){
                        ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                        mediaFslAudio.stop();
                        mediaFslAudio = MediaPlayer.create(context, audio);
                        isPlayed = 0;
                    }
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaFslAudio.stop();
    }
}

//              Setting a title from a fragment
//            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
//                    .setTitle(word + " - " + getResources().getString(R.string.fsl_label));