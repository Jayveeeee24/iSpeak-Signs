package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
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
    Dialog gamePauseGlobal;
    boolean isMainMenu = false;

    String[] choices;
    String[] pastAnswers;
    String answer = "";
    int imagesNo = 0;
    int isLearned = 0;
    String stringHowTo = "";//TODO remind lang about sa HOW TO
    String itemCategory= "";

    String category = "";
    String[] allCategories;
    String[] allWords;
    int currentLevel = 0;
    int life = 3;
    int score = 0;

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
                isLearned = learnWordItemCursor.getInt(3);
                imagesNo = learnWordItemCursor.getInt(5);
                stringHowTo = learnWordItemCursor.getString(7);
            }
        }

        if (isLearned == 0){
            functionHelper.updateisLearnedProgress(GameScreenActivity.this, itemCategory, answer);
        }

        final WordImageSliderAdapter wordImageSliderAdapter = (new WordImageSliderAdapter(GameScreenActivity.this, answer.toLowerCase(), imagesNo));
        battleViewPager.setAdapter(wordImageSliderAdapter);
        if (imagesNo != 1){
            addDotsIndicator(0);
        }

        battleViewPager.addOnPageChangeListener(viewListener);
    }

    private void addDotsIndicator(int position){
        TextView[] dots = new TextView[imagesNo];
        linearBattleItemLayout.removeAllViews();

        if (imagesNo!=1){
            for (int i = 0; i< dots.length; i++){
                dots[i] = new TextView(GameScreenActivity.this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.colorPrimary, null));

                linearBattleItemLayout.addView(dots[i]);
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

    private void SetUpDesign(){
        LinearLayout gameScreenLayout = findViewById(R.id.game_screen_layout);
        gameScreenLayout.setNestedScrollingEnabled(false);
        AnimationDrawable animationDrawable = (AnimationDrawable) gameScreenLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

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
        gamePauseGlobal = gamePause;
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
                isMainMenu = true;
                onBackPressed();
            }
        });

        setUpGame();
    }

    private int getRandom(int tempMax){
        int min = 0;
        return (int)(Math.random()*(tempMax -min+1)+min);
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
        currentLevel = currentLevel + 1;
        categoryLabel.setText(getResources().getString(R.string.game_screen_category_label, category));
        levelLabel.setText(getResources().getString(R.string.level_label, Integer.toString(currentLevel)));
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
        //UNTIL HERE, EVERYTHING WORKS EXCEPT FOR THE PAST ANSWERS PART

        Cursor getPastAnswer = DB.getPastAnswer();
        pastAnswers = new String[] {"", "", "", "", "", "", "", "", "", ""};

        boolean isPassed = false;
        int lastIndex = 0;

        if (getPastAnswer.getCount() == 0){
            boolean insertNewPastAnswer = DB.InsertPastAnswer(answer);
            if (insertNewPastAnswer){
                Log.i("BAGONG INSERT", "Insert New Past answer success");
            }else{
                Log.i("BAGONG INSERT", "Insert New Past answer failed");
            }
            pastAnswers[0] = answer;

        }else{
            int i = 0;
            while (getPastAnswer.moveToNext()){
                pastAnswers[i] = getPastAnswer.getString(0);
                i++;
            }

            Log.i("PRESENT_ANSWER", answer);
            for (int j = 0; j < pastAnswers.length; j++){
                if (pastAnswers[j].equals(answer) && !pastAnswers[j].equals("")){
                    SetUpDesign();
                }else{
                    isPassed = true;
                    if (pastAnswers[j].equals("")){
                        lastIndex = j;
                        break;
                    }
                }
                Log.i("PAST_ANSWER", pastAnswers[j]);
            }
        }

        if (pastAnswers[9].equals("") && isPassed){
            boolean insertNewPastAnswer = DB.InsertPastAnswer(answer);
            if (insertNewPastAnswer){
                Log.i("LUMANG INSERT", "Insert New Past answer success");
            }else{
                Log.i("LUMANG INSERT", "Insert New Past answer failed");
            }
            pastAnswers[lastIndex] = answer;
        }else if (!pastAnswers[9].equals("")){
            Log.i("PAST_ANSWER_FULL", "POTA PUNO NA");
        }

        setUpImage();

        //WILL DELETE LATER
        for (String pastAnswer : pastAnswers) {
            Log.i("NEW_PAST_ANSWER", pastAnswer);
        }

        for (String choice : choices) {
            Log.i("OLD_CHOICE", choice);
        }

        //LEGEND:
        //the current answer is (answer) variable
        //the current choices are in (choices) array
        //all of the past answers are in pastAnswers array
        shuffleChoices(choices);

        for (String choice : choices) {
            Log.i("NEW_CHOICE", choice);
        }

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

        choice1_button.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
            checkValidate(choice1_label);
        });

        choice2_button.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
            checkValidate(choice2_label);
        });

        choice3_button.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
            checkValidate(choice3_label);
        });

        choice4_button.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(GameScreenActivity.this, R.anim.image_button_clicked));
            checkValidate(choice4_label);
        });
    }

    private void checkValidate(TextView textView){
        if (textView.getText().toString().toLowerCase().equals(answer.toLowerCase())){
            Toast.makeText(this, "TAMAAAAA", Toast.LENGTH_SHORT).show();
            setUpResultPopUp(true);
        }else{
            Toast.makeText(this, "MALIIIII", Toast.LENGTH_SHORT).show();
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
        countDownTimer.cancel();
        int[] timePerLevel = new int[]{60, 55, 50, 45, 40, 35, 35, 30, 30, 25};

        if (isCorrect){
            //IF THE ANSWER IS CORRECT

            gameResultLayout.setBackgroundResource(R.drawable.mini_game_bg3);
            levelCompleteLabel.setTextColor(getResources().getColor(R.color.golden_puppy, null));
            levelCompleteSubLabel.setText("");
            score = score + functionHelper.getTimeScore(currentLevel, counter2, timePerLevel)[1];
            miniGameScore.setText(String.valueOf(score));
            if (currentLevel < 10){
                //IF CURRENT LEVEL IS LESS THAN 10 BUT THE ANSWER IS CORRECT

                levelCompleteLabel.setText("Congrats! You are correct");

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

                levelCompleteLabel.setText("Congrats! You finished the game");
                nextLevelLabel.setText("Main Menu");

                //GET THE HIGH SCORE
                if (functionHelper.getHighScore(this) < score){
                    levelCompleteLabel.setText("New HIGH SCORE!");
                    boolean updateScore = DB.updateScore(score);
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
        else{
            //IF THE ANSWER IS WRONG

            gameResultLayout.setBackgroundResource(R.drawable.mini_game_bg1);
            levelCompleteLabel.setTextColor(getResources().getColor(R.color.white, null));
            levelCompleteSubLabel.setText("The correct answer is " + answer);
            life = life - 1;
            miniGameScore.setText(String.valueOf(score));
            if (life <= 0){
                //IF LIFE IS 0 or less and ANSWER IS WRONG

                levelCompleteLabel.setText("You Lost! No Lives Left");
                nextLevelLabel.setText("Main Menu");

                if (functionHelper.getHighScore(this) < score){
                    levelCompleteLabel.setText("New HIGH SCORE!");
                    boolean updateScore = DB.updateScore(score);
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

                if (currentLevel < 10){
                    levelCompleteLabel.setText("You are wrong!" + "\nLives left: " + life);
                    nextLevelLabel.setText("Level " + (currentLevel + 1));
                    counter2 = timePerLevel[currentLevel];
                    counter = counter2 * 1000;

                    nextLevelButton.setOnClickListener(view -> {
                        gameResultDialog.dismiss();
                        SetUpDesign();
                    });

                }else{
                    levelCompleteLabel.setText("You finished the game");
                    nextLevelLabel.setText("Main Menu");

                    if (functionHelper.getHighScore(this) < score){
                        levelCompleteLabel.setText("New HIGH SCORE!");
                        boolean updateScore = DB.updateScore(score);
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

        if (isMainMenu){
            super.onBackPressed();
            super.onBackPressed();
        }

        if (!gamePauseGlobal.isShowing() && !isMainMenu){
            gamePauseGlobal.show();
            countDownTimer.cancel();
        }
    }
}