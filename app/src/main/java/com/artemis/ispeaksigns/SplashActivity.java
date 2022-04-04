package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    DBHelper DB;
    String userName = "";
    int isOldUser;
    String oldDate;
    @Override
    @SuppressWarnings("DEPRECATION")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


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
                DB = new DBHelper(SplashActivity.this);

                Date currentTime = Calendar.getInstance().getTime();
                String tempCurrentDate = currentTime.toString();
                String currentDate = tempCurrentDate.substring(0, 10) + tempCurrentDate.substring(29, 34);

                Cursor userDataCursor = DB.getUserData("", "Splash");

                if (userDataCursor.getCount()==0){
                    Toast.makeText(SplashActivity.this, "No value on database found!", Toast.LENGTH_SHORT).show();
                }else{
                    while (userDataCursor.moveToNext()){
                        userName = userDataCursor.getString(0);
                        isOldUser = userDataCursor.getInt(1);
                        oldDate = userDataCursor.getString(2);
                    }

                }

                if (!currentDate.equals(oldDate)){//checking if the database date is up to date to date today
                    boolean checkDate = DB.insertSingleData(currentDate);
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
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class).putExtra("userName", userName));
                finish();
            }
        }, 2200);
        
    }
}