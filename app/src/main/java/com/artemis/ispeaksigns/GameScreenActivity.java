package com.artemis.ispeaksigns;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {

    DBHelper DB;
    FunctionHelper functionHelper = new FunctionHelper();

    ViewPager battleViewPager;
    LinearLayout linearBattleItemLayout;
    ScrollView gameScreenScrollView;

    GridLayout choicesLayout;
    CardView miniGamePause;

    TextView timeLabel;
    TextView categoryLabel;
    TextView levelLabel;
    TextView lifeLabel;

    CountDownTimer countDownTimer;

    public int counter = 60000;
    private int counter2 = 60;
    boolean isMainMenu = false;

    String[] choices;
    String[] pastAnswers = new String[] {"", "", "", "", "", "", "", "", "", ""};
    String answer = "";
    int imagesNo = 0;
    String itemCategory= "";

    String category = "";
    String[] allCategories;
    String[] allWords;
    int currentLevel = 0;
    int life = 3;
    int score = 0;
    int correctAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        functionHelper.setAppLocale(this);

        setContentView(R.layout.activity_game_screen);
        DB = new DBHelper(this);

        timeLabel = findViewById(R.id.time_label);

        battleViewPager = findViewById(R.id.viewpager_battle_item);
        linearBattleItemLayout = findViewById(R.id.linear_battle_item);
        gameScreenScrollView = findViewById(R.id.game_screen_scrollview);
        gameScreenScrollView.setNestedScrollingEnabled(false);

        choicesLayout = findViewById(R.id.choices_layout);
        miniGamePause = findViewById(R.id.mini_game_pause);

        categoryLabel = findViewById(R.id.category_label);
        levelLabel = findViewById(R.id.level_label);
        lifeLabel = findViewById(R.id.life_label);

        SetUpDesign();
    }

    private void setUpImage(){
        Cursor learnWordItemCursor = DB.getItem(answer, "");
        if (learnWordItemCursor.getCount() != 0){
            while(learnWordItemCursor.moveToNext()){
                itemCategory = learnWordItemCursor.getString(1);
                imagesNo = learnWordItemCursor.getInt(5);
            }
        }

        final WordImageSliderAdapter wordImageSliderAdapter = (new WordImageSliderAdapter(GameScreenActivity.this, answer.toLowerCase(), imagesNo));
        battleViewPager.setAdapter(wordImageSliderAdapter);
        addDotsIndicator(0);

        battleViewPager.addOnPageChangeListener(viewListener);
    }

    private void addDotsIndicator(int position){
        TextView[] dots = new TextView[imagesNo];
        linearBattleItemLayout.removeAllViews();

        for (int i = 0; i< dots.length; i++){
                dots[i] = new TextView(GameScreenActivity.this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.colorPrimary, null));

                linearBattleItemLayout.addView(dots[i]);
        }

        if (dots.length > 0){
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

    private void SetUpDesign(){

        countDownTimer = new CountDownTimer(counter,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter2-=1;
                counter-=1000;
                timeLabel.setText(getResources().getString(R.string.time_label, String.valueOf(counter2)));

                if (counter2 <= 0){
                    onFinish();
                }else if (counter2 < 10){
                    timeLabel.setTextColor(getResources().getColor(R.color.flame, null));
                }else{
                    timeLabel.setTextColor(getResources().getColor(R.color.white, null));
                }
            }
            @Override
            public void onFinish() {
                timeLabel.setText(getResources().getString(R.string.time_label, "60"));
                timeLabel.setTextColor(getResources().getColor(R.color.white, null));
                counter2 = 60;
                counter = 60000;
                checkValidate(timeLabel);
            }
        }.start();

        final Dialog gamePause = new Dialog(GameScreenActivity.this);
        gamePause.setContentView(R.layout.popup_game_pause);
        gamePause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        gamePause.setCanceledOnTouchOutside(false);
        gamePause.setCancelable(false);
        final CardView miniGameResume = gamePause.findViewById(R.id.mini_game_resume);
        final CardView miniGameMainMenu = gamePause.findViewById(R.id.mini_game_main_menu);

        miniGamePause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause.show();
                countDownTimer.cancel();
            }
        });

        miniGameResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause.dismiss();
                countDownTimer.start();
            }
        });

        miniGameMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isMainMenu = true;
                gamePause.dismiss();
                onBackPressed();
            }
        });

        setUpGame();
    }

    private int getRandom(int tempMax){
        int min = 0;
        return (int)(Math.random()*(tempMax - min+1)+min);
    }

    private String getItem(int tempMax, String[] itemArray){

        int i = getRandom(tempMax);
        if (i == 0){
            return itemArray[0];
        }else{
            return itemArray[i-1];
        }
    }

    private void getCategory(){
        Cursor getAllCategory = DB.getCategory("", "getWordCategory");
        allCategories = new String[getAllCategory.getCount()];

        if (getAllCategory.getCount() == 0){
            Toast.makeText(this, "No database found!", Toast.LENGTH_SHORT).show();
            isMainMenu = true;
            onBackPressed();
        }else{
            int i = 0;
            while (getAllCategory.moveToNext()){
                allCategories[i] = getAllCategory.getString(0);
                Log.i("ALL_CATEGORIES", allCategories[i]);
                i++;
            }
            category = getItem(getAllCategory.getCount(), allCategories);
            Log.i("PRESENT_CATEGORY", category);
        }
    }

    private void setUpGame(){

        getCategory();
        Cursor getAllWordsByCategory = DB.getItem(category, "getItemByCategory");
        allWords = new String[getAllWordsByCategory.getCount()];
        choices = new String[]{"", "", "", ""};
        categoryLabel.setText(getResources().getString(R.string.game_screen_category_label, category));
        lifeLabel.setText(getResources().getString(R.string.life_label, Integer.toString(life)));

        if (getAllWordsByCategory.getCount() == 0){
            Toast.makeText(this, "No database found!", Toast.LENGTH_SHORT).show();
            isMainMenu = true;
            onBackPressed();
        }else {
            int i = 0;
            while (getAllWordsByCategory.moveToNext()){
                allWords[i] = getAllWordsByCategory.getString(0);
                Log.i("ALL_WORDS", allWords[i]);
                i++;
            }

        }
        shuffleChoices(allWords);
        for (int i = 0; i < 4; i++){
            choices[i] = allWords[i];
        }
        answer = getItem(choices.length, choices);

        Log.i("PRESENT_ANSWER", answer);
        for (int j = 0; j < pastAnswers.length; j++){//THIS CHECKS IF ALL PAST ANSWERS ARE NOT THE SAME WITH THE CURRENT ANSWER
            if (pastAnswers[j].toLowerCase().equals(answer.toLowerCase())){
                Log.i("PAST_ANSWER", "ULIT ULI");
                SetUpDesign();
                break;
            }
        }

        currentLevel = currentLevel + 1;
        levelLabel.setText(getResources().getString(R.string.level_label, Integer.toString(currentLevel)));

        Log.i("CURRENT INDEX", String.valueOf(currentLevel-1));
        Log.i("Current LEVEL", String.valueOf(currentLevel));
        pastAnswers[(currentLevel-1)] = answer;

        //WILL DELETE LATER
        for (int i = 0; i < pastAnswers.length; i++){
            Log.i("NEW_PAST_ANSWERS", pastAnswers[i]);
        }

        setUpImage();

        //LEGEND:
        //the current answer is (answer) variable
        //the current choices are in (choices) array
        //all of the past answers are in pastAnswers array
        shuffleChoices(choices);
        setChoices(choices);
    }

    private void setChoices(String[] choices){
        TextView choice1_label, choice2_label, choice3_label, choice4_label;
        CardView choice1_button, choice2_button, choice3_button, choice4_button;

        choice1_label = findViewById(R.id.choice1_label);
        choice2_label = findViewById(R.id.choice2_label);
        choice3_label = findViewById(R.id.choice3_label);
        choice4_label = findViewById(R.id.choice4_label);

        choice1_button = findViewById(R.id.choice1_button);
        choice2_button = findViewById(R.id.choice2_button);
        choice3_button = findViewById(R.id.choice3_button);
        choice4_button = findViewById(R.id.choice4_button);

        choice1_label.setText(choices[0]);
        choice2_label.setText(choices[1]);
        choice3_label.setText(choices[2]);
        choice4_label.setText(choices[3]);

        choice1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
                checkValidate(choice1_label);
            }
        });

        choice2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
                checkValidate(choice2_label);
            }
        });

        choice3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
                checkValidate(choice3_label);
            }
        });

        choice4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
                checkValidate(choice4_label);
            }
        });
    }

    private void checkValidate(TextView textView){
        if (textView.getText().toString().toLowerCase().equals(answer.toLowerCase())){
            setUpResultPopUp(true);
        }else{
            setUpResultPopUp(false);
        }
    }

    private void setUpResultPopUp(boolean isCorrect){
        final Dialog gameResultDialog = new Dialog(this);
        gameResultDialog.setContentView(R.layout.popup_game_results);
        gameResultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        gameResultDialog.setCanceledOnTouchOutside(false);
        gameResultDialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(gameResultDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        final TextView resultTotalWords = gameResultDialog.findViewById(R.id.result_total_words);
        final TextView levelCompleteLabel = gameResultDialog.findViewById(R.id.level_complete_label);
        final TextView miniGameScore = gameResultDialog.findViewById(R.id.mini_game_score);
        final TextView levelOverviewLabel = gameResultDialog.findViewById(R.id.level_overview_label);
        final ProgressBar progressGameLabel = gameResultDialog.findViewById(R.id.progressGameLevel);
        final CardView nextLevelButton = gameResultDialog.findViewById(R.id.next_level_button);
        final TextView nextLevelLabel = gameResultDialog.findViewById(R.id.next_level_label);
        final LinearLayout gameResultLayout = gameResultDialog.findViewById(R.id.game_result_layout);
        final TextView levelCompleteSubLabel = gameResultDialog.findViewById(R.id.level_complete_sub_label);
        final CardView scoreLayout = gameResultDialog.findViewById(R.id.score_layout);
        final LinearLayout badgeLayout = gameResultDialog.findViewById(R.id.badge_layout);

        Cursor wordDiscoveredCursor = DB.countItems("WordDiscovered");
        int wordsLearned = 0;

        if (wordDiscoveredCursor.getCount() == 0){
            Toast.makeText(this, "No database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (wordDiscoveredCursor.moveToNext()){
                wordsLearned = wordDiscoveredCursor.getInt(0);
            }
        }

        gameResultDialog.show();
        gameResultDialog.getWindow().setAttributes(lp);
        resultTotalWords.setText(getResources().getString(R.string.mini_game_all_learned_words, Integer.toString(wordsLearned)));
        levelOverviewLabel.setText(getResources().getString(R.string.level_label, Integer.toString(currentLevel)));
        progressGameLabel.setProgress(currentLevel);
        int[] timePerLevel = new int[]{60, 55, 50, 45, 40, 35, 35, 30, 30, 25};

        if (isCorrect){
            //IF THE ANSWER IS CORRECT

            gameResultLayout.setBackgroundResource(R.drawable.mini_game_bg3);
            levelCompleteLabel.setTextColor(getResources().getColor(R.color.congrats_color, null));
            levelCompleteSubLabel.setText("");
            score = score + functionHelper.getTimeScore(currentLevel, counter2, timePerLevel);
            miniGameScore.setText(String.valueOf(score));
            correctAnswer = correctAnswer + 1;
            if (currentLevel < 10){
                //IF CURRENT LEVEL IS LESS THAN 10 BUT THE ANSWER IS CORRECT

                levelCompleteLabel.setText(R.string.congrats_label);

                countDownTimer.cancel();
                counter2 = timePerLevel[currentLevel];
                counter = counter2 * 1000;

                nextLevelLabel.setText("Level " + (currentLevel + 1));

                nextLevelButton.setOnClickListener(view -> {
                    gameResultDialog.dismiss();
                    SetUpDesign();
                });

            }else{
                //IF THE CURRENT LEVEL IS 10 AND THE ANSWER IS CORRECT,
                //THUS THE GAME IS FINISHED

                levelCompleteLabel.setText(R.string.congrats_finished_label);
                nextLevelLabel.setText(getResources().getString(R.string.main_menu));
                levelOverviewLabel.setText(getResources().getString(R.string.level_label, Integer.toString(currentLevel)));
                countDownTimer.cancel();

                //GET THE HIGH SCORE
                if (functionHelper.getHighScore(this) < score){
                    levelCompleteLabel.setText(R.string.high_score_achieved_label);
                    boolean updateScore = DB.updateScore(score, currentLevel);
                    if (updateScore){
                        Log.i("UPDATE HIGH SCORE", "SUCCESS");
                    }else{
                        Log.i("UPDATE HIGH SCORE", "Failed");
                    }
                }
                int isBadge;
                if (correctAnswer == 10){
                    levelCompleteLabel.setText(R.string.perfect_answer_label);
                    if (functionHelper.isBadgeVisible(GameScreenActivity.this) == 0){
                        scoreLayout.setVisibility(View.GONE);
                        badgeLayout.setVisibility(View.VISIBLE);

                        isBadge = 1;

                        boolean updateIsBadge = DB.updateBadge(isBadge);
                        if (updateIsBadge){
                            Log.i("UPDATE BADGE", "SUCCESS");
                        }else{
                            Log.i("UPDATE BADGE", "FAILED");
                        }
                    }
                }

                nextLevelButton.setOnClickListener(view -> {
                    gameResultDialog.dismiss();
                    isMainMenu = true;
                    onBackPressed();
                });
            }
        }
        else{
            //IF THE ANSWER IS WRONG

            gameResultLayout.setBackgroundResource(R.drawable.mini_game_bg1);
            levelCompleteLabel.setTextColor(getResources().getColor(R.color.wrong_color, null));
            levelCompleteSubLabel.setText(getResources().getString(R.string.correct_answer_label, answer));

            life = life - 1;
            miniGameScore.setText(String.valueOf(score));
            if (life <= 0){
                //IF LIFE IS 0 or less and ANSWER IS WRONG

                levelCompleteLabel.setText(getResources().getString(R.string.game_over_label));
                nextLevelLabel.setText(getResources().getString(R.string.main_menu));
                countDownTimer.cancel();

                if (functionHelper.getHighScore(this) < score){
                    levelCompleteLabel.setText(getResources().getString(R.string.high_score_achieved_label));
                    levelCompleteSubLabel.setText(getResources().getString(R.string.game_over_label));
                    boolean updateScore = DB.updateScore(score, currentLevel);
                    if (updateScore){
                        Log.i("UPDATE HIGH SCORE", "SUCCESS");
                    }else{
                        Log.i("UPDATE HIGH SCORE", "Failed");
                    }
                }
                nextLevelButton.setOnClickListener(view -> {
                    gameResultDialog.dismiss();
                    isMainMenu = true;
                    onBackPressed();
                });
            }else{
                //IF PLAYER HAS LIFE BUT ANSWER IS WRONG

                if (currentLevel < 10){//if current level is less than 10 or if the game is not finished
                    levelCompleteLabel.setText(getResources().getString(R.string.answer_wrong_label, String.valueOf(life)));
                    nextLevelLabel.setText("Level " + (currentLevel + 1));
                    countDownTimer.cancel();
                    counter2 = timePerLevel[currentLevel];
                    counter = counter2 * 1000;

                    nextLevelButton.setOnClickListener(view -> {
                        gameResultDialog.dismiss();
                        SetUpDesign();
                    });

                }else{

                    levelCompleteLabel.setText(getResources().getString(R.string.congrats_finished_label));
                    nextLevelLabel.setText(getResources().getString(R.string.main_menu));
                    countDownTimer.cancel();

                    if (functionHelper.getHighScore(this) < score){
                        levelCompleteLabel.setText(getResources().getString(R.string.high_score_achieved_label));
                        boolean updateScore = DB.updateScore(score, currentLevel);
                        if (updateScore){
                            Log.i("UPDATE HIGH SCORE", "SUCCESS");
                        }else{
                            Log.i("UPDATE HIGH SCORE", "Failed");
                        }
                    }
                    nextLevelButton.setOnClickListener(view -> {
                        gameResultDialog.dismiss();
                        isMainMenu = true;
                        onBackPressed();
                    });
                }
            }
        }
    }

    private void shuffleChoices(String[] ar){
        Random rnd = new Random();
        for (int i = ar.length-1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (isMainMenu){
            super.onBackPressed();
        }

    }
}