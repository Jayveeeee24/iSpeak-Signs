package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static {
        if (OpenCVLoader.initDebug()){
            Log.i("SplashActivity", "OpenCVLoader Success");
        }else{
            Log.i("SplashActivity", "OpenCVLoader Failed");
        }
    }

    DBHelper DB;
    String oldDate;
    int isOldUser;
    String selectedAvatar = "";
    String languageCode = "";

    @Override
    @SuppressWarnings("DEPRECATION")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DB = new DBHelper(this);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                Date currentTime = Calendar.getInstance().getTime();
                String tempCurrentDate = currentTime.toString();
                String currentDate = tempCurrentDate.substring(0, 10) + tempCurrentDate.substring(29, 34);
                Cursor getDateCursor = DB.getUserData("", "Splash");

                if (getDateCursor.getCount()==0){
                    Toast.makeText(SplashActivity.this, "No value on database found!", Toast.LENGTH_SHORT).show();
                }else{
                    while (getDateCursor.moveToNext()){
                        oldDate = getDateCursor.getString(0);
                        isOldUser = getDateCursor.getInt(1);
                    }
                }


                if (!currentDate.equals(oldDate)){//checking if the database date is up to date to date today
                    boolean checkDate = DB.updateSingleData(currentDate, 0, "currentDate");
                    if (checkDate)
                    {//check if the date is inserted
                        Log.i("ETO OLD DATE", oldDate);
                        Log.i("ETO NEW DATE", currentDate);
                        Log.i("DATE", "UPDATE SUCCESS");
                    }else{//date insert gone wrong
                        Log.i("DATE", "UPDATE GONE WRONG");
                    }
                }else {
                    Log.i("DATE", "UPDATED NA TALAGA LODS");
                }

                if (isOldUser == 0){
                    Log.i("DI SYA LUMANG USER", "DI TALAGA");
                    List<String> languages = new ArrayList<String>();
                    languages.add("Filipino");
                    languages.add("English");

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SplashActivity.this, android.R.layout.simple_spinner_item, languages);
                    selectLanguageSpinner.setAdapter(dataAdapter);
                    selectLanguageSpinner.setOnItemSelectedListener(SplashActivity.this);
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
                                Log.i("Main Activity", "Insert New User Success!");
                            }else{
                                Log.i("Main Activity", "Insert New User Failed.");
                            }
                            changeUserDetails.dismiss();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, 2200);
        
    }

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