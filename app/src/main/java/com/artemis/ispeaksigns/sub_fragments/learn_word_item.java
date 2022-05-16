package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.VideoActivity;
import com.artemis.ispeaksigns.WordImageSliderAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_word_item extends Fragment {

    View view;
    Context context;
    DBHelper DB;
    MediaPlayer mediaAudioWord;
    FunctionHelper functionHelper;
    int audio;

    TextView learnWordItemName;
    TextView partsOfSpeech;
    ImageView heartItem;
    TextView howTo;
    ImageView learnWordPlayVideo;

    int isFavorite;
    String itemType = "";
    int imagesNo = 0;

    String word;

    private TextView[] dots;
    private int currentPage;
    private ViewPager wordImageViewPager;
    private LinearLayout wordImageLinear;

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

        SetUpDesign();
        InitializeOnClick();
    }

    private void SetUpDesign(){
        functionHelper = new FunctionHelper();
        ScrollView learnWordItemParent = view.findViewById(R.id.learn_word_item_parent);
        learnWordItemParent.setNestedScrollingEnabled(false);
        String audioName = "audio_demo";
        audio = getResources().getIdentifier(audioName, "raw", context.getPackageName());
        mediaAudioWord = MediaPlayer.create(context, audio);
        learnWordItemName = view.findViewById(R.id.learn_word_item_name);
        partsOfSpeech = view.findViewById(R.id.parts_of_speech);
        heartItem = view.findViewById(R.id.learn_word_item_favorite);
        howTo = view.findViewById(R.id.learn_word_how_description);

        if (getArguments() != null)
        {
            word = getArguments().getString("learn_word_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(word);
        }

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
            }
        }


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
            currentPage = position;
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
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                try {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("ItemName", "@" + word);
                    context.startActivity(intent);
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
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
        mediaAudioWord.stop();
    }
}