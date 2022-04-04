package com.artemis.ispeaksigns.sub_fragments;

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

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_word_item extends Fragment {

    View view;
    Context context;
    MediaPlayer mediaAudioWord;
    int audio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_word_item, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String word;
        ScrollView learnWordItemParent = view.findViewById(R.id.learn_word_item_parent);
        learnWordItemParent.setNestedScrollingEnabled(false);
        String audioName = "audio_demo";
        audio = getResources().getIdentifier(audioName, "raw", context.getPackageName());
        mediaAudioWord = MediaPlayer.create(context, audio);


        if (getArguments() != null)
        {
            word = getArguments().getString("learn_word_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                .setTitle(word);
        }

        InitializeOnClick();
    }

    private void InitializeOnClick(){
        final ImageView learnWordItemFavorite = (ImageView) view.findViewById(R.id.learn_word_item_favorite);
        final ImageView playAudio = (ImageView) view.findViewById(R.id.learn_word_play_audio);

        learnWordItemFavorite.setOnClickListener(new View.OnClickListener() {
            int isFavorite = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                if (isFavorite == 0) {
                    learnWordItemFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                } else if (isFavorite == 1) {
                    learnWordItemFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                }
            }
        });

        playAudio.setOnClickListener(new View.OnClickListener() {
            int isPlayed = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                if (isPlayed == 0){
                    ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.outrageous_orange, null)));
                    mediaAudioWord.start();
                    isPlayed = 1;
                    mediaAudioWord.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                            mediaAudioWord.stop();
                            mediaAudioWord = MediaPlayer.create(context, audio);
                            isPlayed = 0;
                        }
                    });
                }else if (isPlayed == 1){
                    ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                    mediaAudioWord.stop();
                    mediaAudioWord = MediaPlayer.create(context, audio);
                    isPlayed = 0;
                }
            }
        });

    }
}