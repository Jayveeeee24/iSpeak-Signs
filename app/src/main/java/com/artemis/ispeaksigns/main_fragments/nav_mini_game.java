package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

public class nav_mini_game extends Fragment {

    private View view;
    Context context;
    DBHelper DB;

    TextView miniGameTotalWords, miniGameBestScore, miniGameWarning;
    ImageView isMiniGameLocked;
    CardView miniGamePlayGame;

    int wordsLearned = 0;

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

        RelativeLayout miniGameLayout = view.findViewById(R.id.mini_game_layout);
        miniGameLayout.setNestedScrollingEnabled(false);
        AnimationDrawable animationDrawable = (AnimationDrawable) miniGameLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(3500);
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

        miniGameTotalWords.setText(getResources().getString(R.string.mini_game_all_learned_words, Integer.toString(wordsLearned)));
        miniGamePlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wordsLearned < 1){//TODO CHANGE BACK TO 50
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
    }
}