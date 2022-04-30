package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

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

    @Override
    @SuppressWarnings("DEPRECATION")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DB = new DBHelper(this);

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
                    startActivity(new Intent(SplashActivity.this, WalkthroughActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, 2200);
        
    }


}