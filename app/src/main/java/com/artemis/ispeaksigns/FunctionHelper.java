package com.artemis.ispeaksigns;

import android.content.Context;
import com.artemis.ispeaksigns.R;

import java.util.ArrayList;
import java.util.Calendar;

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
}
