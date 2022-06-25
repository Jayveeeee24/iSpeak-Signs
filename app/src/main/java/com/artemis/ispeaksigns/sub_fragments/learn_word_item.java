package com.artemis.ispeaksigns.sub_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.VideoActivity;
import com.artemis.ispeaksigns.WordImageSliderAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_word_item extends Fragment {

    View view;
    Context context;
    DBHelper DB;
    MediaPlayer mediaAudioWord;
    FunctionHelper functionHelper;
    int audio;

    TextView learnWordItemName, ipa;
    TextView partsOfSpeech;
    ImageView heartItem;
    TextView howTo;
    ImageView learnWordPlayVideo;

    int isFavorite;
    String itemType = "";
    int imagesNo = 0;
    String stringHowTo = "";
    String stringIpa = "";
    String videoId = "";

    String word;

    private TextView[] dots;
    private ViewPager wordImageViewPager;
    private LinearLayout wordImageLinear;
    RelativeLayout learnWordImageParent;

    YouTubePlayerView youTubePlayerView;
    LinearLayout youtube_layout;

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
        DB = new DBHelper(context);
        wordImageViewPager = view.findViewById(R.id.viewpager_word_item);
        wordImageLinear = view.findViewById(R.id.linear_word_item);
        learnWordImageParent = view.findViewById(R.id.learn_word_image_parent);


        SetUpDesign();
        InitializeOnClick();
    }

    private void SetUpDesign(){
        functionHelper = new FunctionHelper();
        ScrollView learnWordItemParent = view.findViewById(R.id.learn_word_item_parent);
        learnWordItemParent.setNestedScrollingEnabled(false);
        learnWordItemName = view.findViewById(R.id.learn_word_item_name);
        partsOfSpeech = view.findViewById(R.id.parts_of_speech);
        heartItem = view.findViewById(R.id.learn_word_item_favorite);
        howTo = view.findViewById(R.id.learn_word_how_description);
        ipa = view.findViewById(R.id.ipa);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        youtube_layout = view.findViewById(R.id.youtube_player_layout);

        if (getArguments() != null){
            word = getArguments().getString("learn_word_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(word);
        }

        audio = getResources().getIdentifier(word.toLowerCase(), "raw", context.getPackageName());
        mediaAudioWord = MediaPlayer.create(context, audio);
        int isLearned = 0;
        String itemCategory = "";
        learnWordItemName.setText(word);
        Cursor learnWordItemCursor = DB.getItem(word, "");
        if (learnWordItemCursor.getCount() != 0){
            while(learnWordItemCursor.moveToNext()){
                itemCategory = learnWordItemCursor.getString(1);
                itemType = learnWordItemCursor.getString(2);
                isLearned = learnWordItemCursor.getInt(3);
                partsOfSpeech.setText(learnWordItemCursor.getString(4));
                imagesNo = learnWordItemCursor.getInt(5);
                stringHowTo = learnWordItemCursor.getString(7);
                stringIpa = learnWordItemCursor.getString(8);
            }
        }

        howTo.setText(stringHowTo);
        ipa.setText(stringIpa);

        if (isLearned == 0){
            functionHelper.updateisLearnedProgress(context, itemCategory, word);
        }

        Cursor learnWordFavoriteCursor = DB.getItem(word, "getItemHeart");
        if (learnWordFavoriteCursor.getCount() == 0){
            heartItem.setImageResource(R.drawable.ic_favorites_outline);
            isFavorite = 0;
        }else{
            heartItem.setImageResource(R.drawable.ic_menu_favorites);
            isFavorite = 1;
        }

        final WordImageSliderAdapter wordImageSliderAdapter = (new WordImageSliderAdapter(context, word.toLowerCase(), imagesNo));
        wordImageViewPager.setAdapter(wordImageSliderAdapter);
        if (imagesNo != 1){
            addDotsIndicator(0);
        }

        wordImageViewPager.addOnPageChangeListener(viewListener);
    }

    private void addDotsIndicator(int position){
        dots = new TextView[imagesNo];
        wordImageLinear.removeAllViews();

        if (imagesNo!=1){
            for (int i = 0; i<dots.length; i++){
                dots[i] = new TextView(context);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.colorPrimary, null));

                wordImageLinear.addView(dots[i]);
            }
        }

        if (dots.length > 0 && imagesNo != 1){
            dots[position].setTextColor(getResources().getColor(R.color.colorDots, null));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void InitializeOnClick(){
        final ImageView learnWordItemFavorite = (ImageView) view.findViewById(R.id.learn_word_item_favorite);
        final ImageView playAudio = (ImageView) view.findViewById(R.id.learn_word_play_audio);
        learnWordPlayVideo = view.findViewById(R.id.learn_word_play_video);

        learnWordPlayVideo.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));

                Cursor youtubeIdCursor = DB.getItem(word, "getYoutubeId");
                if (youtubeIdCursor.getCount() == 0){
                        Toast.makeText(context, "No database found!", Toast.LENGTH_SHORT).show();
                }else{
                    while (youtubeIdCursor.moveToNext()){
                        videoId = youtubeIdCursor.getString(0);
                    }
                    if (videoId.toLowerCase().equals(word.toLowerCase())){
                        videoId = "FZ9L7AejW9Q";
                    }
                }
                if (i == 0){
                    youtube_layout.setVisibility(View.VISIBLE);
                    learnWordImageParent.setVisibility(View.INVISIBLE);

                    ImageViewCompat.setImageTintList(learnWordPlayVideo, ColorStateList.valueOf(getResources().getColor(R.color.outrageous_orange, null)));
                    youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);
                            youTubePlayer.loadVideo(videoId, 0);

                            playAudio.performClick();
                        }

                        @Override
                        public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                            super.onStateChange(youTubePlayer, state);

                            if(state.equals(PlayerConstants.PlayerState.ENDED)){
                                ImageViewCompat.setImageTintList(learnWordPlayVideo, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));

                                youTubePlayer.loadVideo(videoId,0);
                                youTubePlayer.seekTo(0);
                            }
                        }
                    });
                    i = 1;
                }else{
                    youtube_layout.setVisibility(View.INVISIBLE);
                    learnWordImageParent.setVisibility(View.VISIBLE);

                    ImageViewCompat.setImageTintList(learnWordPlayVideo, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                    i = 0;
                }
            }
        });

        learnWordItemFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                if (isFavorite == 0) {
                    learnWordItemFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                    functionHelper.updateFavorite(context, word, itemType, "Add");
                } else if (isFavorite == 1) {
                    learnWordItemFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                    functionHelper.updateFavorite(context, word, null, "Remove");
                }
            }
        });

        playAudio.setOnClickListener(new View.OnClickListener() {
            int isPlayed = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                if (functionHelper.checkFocusGain(Objects.requireNonNull(getActivity()), mediaAudioWord, null)){
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
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageView playAudio = view.findViewById(R.id.learn_word_play_audio);
        ImageViewCompat.setImageTintList(playAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
        mediaAudioWord.release();
        if (youtube_layout.getVisibility() == View.VISIBLE){
            youTubePlayerView.release();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (youtube_layout.getVisibility() == View.VISIBLE){
            youTubePlayerView.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        audio = getResources().getIdentifier(word.toLowerCase(), "raw", context.getPackageName());
        mediaAudioWord = MediaPlayer.create(context, audio);
    }

}