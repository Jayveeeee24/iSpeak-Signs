package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MiniGameHowto extends AppCompatActivity {

    FunctionHelper functionHelper;
    private ViewPager viewpagerHowTo;
    private LinearLayout linearHowTo;
    private Button nextHowTo, backHowTo;
    private TextView[] dots;
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game_howto);

        functionHelper = new FunctionHelper();
        viewpagerHowTo = findViewById(R.id.viewpager_how_to);
        linearHowTo = findViewById(R.id.linear_how_to);
        nextHowTo = findViewById(R.id.next_how_to);
        backHowTo = findViewById(R.id.back_how_to);

        functionHelper.setAppLocale(this);
        final HowToSliderAdapter howToSliderAdapter = (new HowToSliderAdapter(MiniGameHowto.this, getResources().getString(R.string.selected_language)));

        viewpagerHowTo.setAdapter(howToSliderAdapter);
        addDotsIndicator(0);
        viewpagerHowTo.addOnPageChangeListener(viewListener);

        nextHowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage == 4){
                    onBackPressed();
                }else{
                    viewpagerHowTo.setCurrentItem(currentPage + 1);
                }
            }
        });

        backHowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpagerHowTo.setCurrentItem(currentPage - 1);
            }
        });
    }


    private void addDotsIndicator(int position){
        dots = new TextView[5];
        linearHowTo.removeAllViews();

        for (int i = 0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray_text_color, null));

            linearHowTo.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.white, null));
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

            if (position == 0){
                backHowTo.setEnabled(false);
                nextHowTo.setEnabled(true);
                backHowTo.setVisibility(View.INVISIBLE);

                nextHowTo.setText(getResources().getString(R.string.susunod));
                backHowTo.setText("");
            }else if(position == dots.length-1){
                backHowTo.setEnabled(true);
                nextHowTo.setEnabled(true);
                backHowTo.setVisibility(View.VISIBLE);

                nextHowTo.setText(getResources().getString(R.string.tapos));
                backHowTo.setText(getResources().getString(R.string.ibalik));
            } else{
                backHowTo.setEnabled(true);
                nextHowTo.setEnabled(true);
                backHowTo.setVisibility(View.VISIBLE);

                nextHowTo.setText(getResources().getString(R.string.susunod));
                backHowTo.setText(getResources().getString(R.string.ibalik));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}