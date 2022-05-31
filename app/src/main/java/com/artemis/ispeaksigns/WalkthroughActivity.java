package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WalkthroughActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ViewPager walkthroughViewPager;
    private LinearLayout walkthroughLinear;
    FunctionHelper functionHelper;
    private Button btnBack, btnNext;
    private TextView[] dots;
    private int currentPage;

    DBHelper DB;
    String selectedAvatar = "";
    String languageCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DB = new DBHelper(this);
        setContentView(R.layout.activity_walkthrough);

        btnBack = findViewById(R.id.back);
        btnNext = findViewById(R.id.next);

        final Dialog changeUserDetails = new Dialog(this);
        changeUserDetails.setContentView(R.layout.popup_change_user_details);
        changeUserDetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        changeUserDetails.setCanceledOnTouchOutside(false);
        changeUserDetails.setCancelable(false);
        final Spinner selectLanguageSpinner = (Spinner) changeUserDetails.findViewById(R.id.languageSpinner);
        final Button saveUserButton = (Button) changeUserDetails.findViewById(R.id.save_new_user_details);
        final EditText editTextUserName = (EditText) changeUserDetails.findViewById(R.id.edit_newUserName);
        final CardView avatar1, avatar2, avatar3, avatar4;
        avatar1 = (CardView) changeUserDetails.findViewById(R.id.card_avatar1);
        avatar2 = (CardView) changeUserDetails.findViewById(R.id.card_avatar2);
        avatar3 = (CardView) changeUserDetails.findViewById(R.id.card_avatar3);
        avatar4 = (CardView) changeUserDetails.findViewById(R.id.card_avatar4);

        List<String> languages = new ArrayList<>();
        languages.add("Filipino");
        languages.add("English");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(WalkthroughActivity.this, android.R.layout.simple_spinner_item, languages);
        selectLanguageSpinner.setAdapter(dataAdapter);
        selectLanguageSpinner.setOnItemSelectedListener(WalkthroughActivity.this);
        selectedAvatar = "avatar1";
        avatar1.setCardElevation(20);

        changeUserDetails.show();

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAvatar = "avatar1";
                avatar1.setCardElevation(20);
                avatar2.setCardElevation(0);
                avatar3.setCardElevation(0);
                avatar4.setCardElevation(0);
            }
        });
        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAvatar = "avatar2";
                avatar1.setCardElevation(0);
                avatar2.setCardElevation(20);
                avatar3.setCardElevation(0);
                avatar4.setCardElevation(0);
            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAvatar = "avatar3";
                avatar1.setCardElevation(0);
                avatar2.setCardElevation(0);
                avatar3.setCardElevation(20);
                avatar4.setCardElevation(0);
            }
        });
        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAvatar = "avatar4";
                avatar1.setCardElevation(0);
                avatar2.setCardElevation(0);
                avatar3.setCardElevation(0);
                avatar4.setCardElevation(20);
            }
        });

        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUserName.getText().toString().equals("")){
                    editTextUserName.setText("Juan");
                }
                boolean checkInsertNewUser = DB.UpdateMultipleData(null, new String[]{editTextUserName.getText().toString(), languageCode, selectedAvatar}, "NewUser");
                if (checkInsertNewUser){
                    Log.i("Walkthrough Activity", "Insert New User Success!");
                }else{
                    Log.i("WalkthroughActivity", "Insert New User Failed.");
                }
                changeUserDetails.dismiss();


                functionHelper = new FunctionHelper();
                functionHelper.setAppLocale(WalkthroughActivity.this);

                walkthroughViewPager = findViewById(R.id.viewpager_walkthrough);
                walkthroughLinear = findViewById(R.id.linear_walkthrough);
                final WalkthroughSliderAdapter walkthroughSliderAdapter = (new WalkthroughSliderAdapter(WalkthroughActivity.this, languageCode));

                walkthroughViewPager.setAdapter(walkthroughSliderAdapter);

                addDotsIndicator(0);

                walkthroughViewPager.addOnPageChangeListener(viewListener);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage == 5){
                    startActivity(new Intent(WalkthroughActivity.this, MainActivity.class));
                    finish();
                }else{
                    walkthroughViewPager.setCurrentItem(currentPage + 1);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walkthroughViewPager.setCurrentItem(currentPage - 1);
            }
        });

    }

    private void addDotsIndicator(int position){
        dots = new TextView[6];
        walkthroughLinear.removeAllViews();

        for (int i = 0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray_text_color, null));

            walkthroughLinear.addView(dots[i]);
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
                btnBack.setEnabled(false);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.INVISIBLE);

                btnNext.setText(getResources().getString(R.string.susunod));
                btnBack.setText("");
            }else if(position == dots.length-1){
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText(getResources().getString(R.string.tapos));
                btnBack.setText(getResources().getString(R.string.ibalik));
            } else{
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText(getResources().getString(R.string.susunod));
                btnBack.setText(getResources().getString(R.string.ibalik));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals("Filipino")){
            languageCode = "tl";
        }else if (parent.getItemAtPosition(position).toString().equals("English")){
            languageCode = "en";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        languageCode = "tl";
    }
}