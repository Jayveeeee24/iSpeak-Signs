package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameScreenActivity extends AppCompatActivity {

    DBHelper DB;

    TextView timeLabel;
    ViewPager battleViewPager;
    LinearLayout linearBattleItemLayout;
    LinearLayout choicesLayout;
    CardView miniGamePause;
    private TextView[] dots;

    CountDownTimer countDownTimer;

    public int counter = 60000;
    private int counter2 = 60;
    int imagesNo = 0;
    Dialog gamePauseGlobal;
    boolean isMainMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        DB = new DBHelper(this);

        timeLabel = findViewById(R.id.time_label);
        battleViewPager = findViewById(R.id.viewpager_battle_item);
        linearBattleItemLayout = findViewById(R.id.linear_battle_item);
        choicesLayout = findViewById(R.id.choices_layout);
        miniGamePause = findViewById(R.id.mini_game_pause);

        SetUpDesign();
    }

    private void SetUpDesign(){
        RelativeLayout gameScreenLayout = findViewById(R.id.game_screen_layout);
        gameScreenLayout.setNestedScrollingEnabled(false);
        AnimationDrawable animationDrawable = (AnimationDrawable) gameScreenLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        countDownTimer = new CountDownTimer(counter,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLabel.setText(getResources().getString(R.string.time_label, String.valueOf(counter2)));
                counter2-=1;
                counter-=1000;
            }
            @Override
            public void onFinish() {
                timeLabel.setText(getResources().getString(R.string.time_label, "60"));
                counter2 = 60;
                counter = 60000;
            }
        }.start();

        final Dialog gamePause = new Dialog(GameScreenActivity.this);
        gamePause.setContentView(R.layout.popup_game_pause);
        gamePause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        gamePause.setCanceledOnTouchOutside(false);
        gamePause.setCancelable(false);
        gamePauseGlobal = gamePause;
        final CardView miniGameResume = gamePause.findViewById(R.id.mini_game_resume);
        final CardView miniGameMainMenu = gamePause.findViewById(R.id.mini_game_main_menu);

        miniGamePause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePauseGlobal.show();
                countDownTimer.cancel();
            }
        });

        miniGameResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePauseGlobal.dismiss();
                countDownTimer.start();
            }
        });

        miniGameMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMainMenu = true;
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (isMainMenu){
            super.onBackPressed();
            super.onBackPressed();
        }

        if (!gamePauseGlobal.isShowing()){
            gamePauseGlobal.show();
            countDownTimer.cancel();
        }


    }
}