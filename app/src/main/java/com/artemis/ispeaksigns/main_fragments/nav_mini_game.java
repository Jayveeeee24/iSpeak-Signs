package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.MiniGameHowto;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

public class nav_mini_game extends Fragment {

    private View view;
    Context context;
    DBHelper DB;

    TextView miniGameTotalWords, miniGameBestScore, miniGameWarning, bestScoreLabel;
    ImageView isMiniGameLocked;
    CardView miniGamePlayGame, bestScoreCard, miniGameHowTo;

    int wordsLearned = 0;
    int bestScore = 0;
    int levelReached = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mini_game, container, false);
        context = container.getContext();
        DB = new DBHelper(context);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        miniGameTotalWords = view.findViewById(R.id.mini_game_total_words);
        miniGameBestScore = view.findViewById(R.id.mini_text_best_score);
        miniGameWarning = view.findViewById(R.id.mini_game_warning);
        isMiniGameLocked = view.findViewById(R.id.is_mini_game_locked);
        miniGamePlayGame = view.findViewById(R.id.mini_game_play_game);
        bestScoreCard = view.findViewById(R.id.bestScoreCard);
        bestScoreLabel = view.findViewById(R.id.best_score_label);
        miniGameHowTo = view.findViewById(R.id.mini_game_how_to);

        RelativeLayout miniGameLayout = view.findViewById(R.id.mini_game_layout);
        miniGameLayout.setNestedScrollingEnabled(false);
        AnimationDrawable animationDrawable = (AnimationDrawable) miniGameLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        InitializeDesign();
    }

    private void InitializeDesign(){
        Cursor wordDiscoveredCursor = DB.countItems("WordDiscovered");

        if (wordDiscoveredCursor.getCount() == 0){
            Toast.makeText(context, "No database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (wordDiscoveredCursor.moveToNext()){
                wordsLearned = wordDiscoveredCursor.getInt(0);
            }
        }

        Cursor getHighScoreCursor = DB.getHighScore();

        if (getHighScoreCursor.getCount() != 0){
            while (getHighScoreCursor.moveToNext()){
                bestScore = getHighScoreCursor.getInt(1);
                levelReached = getHighScoreCursor.getInt(2);
            }
        }

        miniGameBestScore.setText(String.valueOf(bestScore));
        bestScoreLabel.setText(getResources().getString(R.string.mini_game_score_label));
        bestScoreCard.setOnClickListener(new View.OnClickListener() {
            int isFront = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                if (isFront == 0){
                    miniGameBestScore.setText(String.valueOf(levelReached));
                    bestScoreLabel.setText(getResources().getString(R.string.mini_game_level_reach_label));
                    isFront = 1;
                }else if (isFront == 1){
                    miniGameBestScore.setText(String.valueOf(bestScore));
                    bestScoreLabel.setText(getResources().getString(R.string.mini_game_score_label));
                    isFront = 0;
                }
            }
        });

        miniGameTotalWords.setText(getResources().getString(R.string.mini_game_all_learned_words, Integer.toString(wordsLearned)));
        miniGamePlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wordsLearned < 129){
                    isMiniGameLocked.setVisibility(View.VISIBLE);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            miniGameWarning.setVisibility(View.VISIBLE);
                            miniGameWarning.setText(getResources().getString(R.string.mini_game_warning, Integer.toString(wordsLearned)));
                        }
                    },500);
                }else{
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    navBuilder.setEnterAnim(android.R.anim.fade_in)
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .setPopExitAnim(android.R.anim.fade_out);
                    try {
                        Navigation.findNavController(view).navigate(R.id.gameScreenActivity, null, navBuilder.build());
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }
                }
                miniGameWarning.setVisibility(View.INVISIBLE);
            }
        });

        miniGameHowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, MiniGameHowto.class);
                    context.startActivity(intent);
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        InitializeDesign();
    }
}