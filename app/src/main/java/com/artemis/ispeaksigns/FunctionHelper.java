package com.artemis.ispeaksigns;

import android.content.Context;
import android.content.SharedPreferences;

import com.artemis.ispeaksigns.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FunctionHelper {

    public String getGreeting(Context context)
    {
        String greeting = "";
        int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (currentTime <=12) {
            if (currentTime == 0)
            {
                greeting = context.getString(R.string.night_label);//night
            }else {
                greeting = context.getString(R.string.morning_label);//morning
            }
        }else if (currentTime <=17) {
            greeting = context.getString(R.string.afternoon_label);//afternoon
        }
        else {
            greeting = context.getString(R.string.night_label);//night
        }

        return greeting;
    }

    public String getCategoryProgressDescription(int categoryProgress){

        if (categoryProgress<30){
            return "Simulan ng matuto!";
        }else if (categoryProgress<49){
            return "Malapit na sa kalahati!";
        }else if (categoryProgress==50){
            return "Nasa kalahati ka na!";
        }else if (categoryProgress<65){
            return "Lagpas na sa kalahati!";
        }else if (categoryProgress<99){
            return "Malapit mo na matapos!";
        }else{
            return "Natapos mo na!";
        }
    }

    public String getImageLogo(String categoryName){

        String str = categoryName.toLowerCase();
        String[] arrStr;
        if (str.contains(" ")){
            arrStr = str.split(" ");
        }else {
            arrStr = str.split("-");
        }
        String imageName = "ic";
        StringBuilder stringBuilder = new StringBuilder();
        for (String a : arrStr)
        {
            if (arrStr.length == 1) {
                str = "_" + str;
                break;
            }
            str = stringBuilder.append("_").append(a).toString();
        }
        imageName = imageName + str ;
        return imageName;
    }

    public int[] getStreak(int[] streakDays, int streakCount, String currentDay){

        String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i<streakDays.length; i++){
            if (days[i].equals(currentDay)){
                streakDays[i] = 1;
            }
        }


        return streakDays;
    }

}
