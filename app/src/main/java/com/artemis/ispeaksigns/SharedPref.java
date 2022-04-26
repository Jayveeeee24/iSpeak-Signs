package com.artemis.ispeaksigns;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences mySharedPref;
    public SharedPref(Context context)
    {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }
    public void setNightModeState(Boolean state)
    {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }
    public Boolean loadNightModeState ()
    {
        return mySharedPref.getBoolean("NightMode", false);
    }
}
