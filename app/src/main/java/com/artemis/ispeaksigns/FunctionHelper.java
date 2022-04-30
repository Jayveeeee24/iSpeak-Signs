package com.artemis.ispeaksigns;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.artemis.ispeaksigns.R;
import com.google.android.exoplayer2.ExoPlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public void setAppLocale (Activity activity){
        DBHelper DB = new DBHelper(activity);
        String selectedLanguage = "";

        Cursor getLocaleCursor = DB.getUserData("", "GetLocale");
        if (getLocaleCursor.getCount()==0){
            Toast.makeText(activity, "No value on database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (getLocaleCursor.moveToNext()){
                selectedLanguage = getLocaleCursor.getString(0);
            }
        }
        Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(selectedLanguage.toLowerCase()));
        resources.updateConfiguration(config, dm);
    }

    public String getCategoryProgressDescription(int categoryProgress, Activity activity){

        if (categoryProgress<30){
            return activity.getString(R.string.una);
        }else if (categoryProgress<49){
            return activity.getString(R.string.pangalawa);
        }else if (categoryProgress==50){
            return activity.getString(R.string.pangatlo);
        }else if (categoryProgress<65){
            return activity.getString(R.string.pangapat);
        }else if (categoryProgress<99){
            return activity.getString(R.string.panglima);
        }else{
            return activity.getString(R.string.huli);
        }
    }

    Activity thisActivity;
    MediaPlayer thisMediaPlayer;
    ExoPlayer thisExoPlayer;
    public boolean checkFocusGain(Activity activity, MediaPlayer mediaPlayer, ExoPlayer exoPlayer){
        AudioManager am = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);

        int result = am.requestAudioFocus(focusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        thisActivity = activity;
        thisMediaPlayer = mediaPlayer;
        thisExoPlayer = exoPlayer;

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return  true;
        }
        return false;
    }

    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (thisMediaPlayer != null){
                        AudioManager am =(AudioManager)thisActivity.getSystemService(Context.AUDIO_SERVICE);
                        switch (focusChange) {

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) :
                                // Lower the volume while ducking.
                                thisMediaPlayer.setVolume(0.2f, 0.2f);
                                break;
                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) :
                                thisMediaPlayer.pause();
                                break;

                            case (AudioManager.AUDIOFOCUS_GAIN) :
                                // Return the volume to normal and resume if paused.
                                thisMediaPlayer.setVolume(1f, 1f);
                                thisMediaPlayer.start();
                                break;
                            default: break;
                        }
                    }
                    if (thisExoPlayer != null){
                        AudioManager am =(AudioManager)thisActivity.getSystemService(Context.AUDIO_SERVICE);
                        switch (focusChange) {

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) :
                                // Lower the volume while ducking.
                                thisExoPlayer.setVolume(0.2f);
                                break;
                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) :
                                thisExoPlayer.pause();
                                break;

                            case (AudioManager.AUDIOFOCUS_GAIN) :
                                // Return the volume to normal and resume if paused.
                                thisExoPlayer.setVolume(1f);
                                thisExoPlayer.play();
                                break;
                            default: break;
                        }
                    }
                }
            };

    public void updateisLearnedProgress(Context context, String itemCategory, String word){
        DBHelper DB = new DBHelper(context);
        boolean updateIsLearned = DB.UpdateItem(word, 1, "isLearned");
        if (updateIsLearned){
            Log.i("Learn Word Item", "Update isLearned Success");
        }else{
            Log.i("Learn Word Item", "Update isLearned Failed");
        }
        int categoryProgress = 0;
        Cursor getCategoryProgress = DB.getItem(itemCategory, "getCategoryProgress");
        if (getCategoryProgress.getCount()!=0){
            while (getCategoryProgress.moveToNext()){
                categoryProgress = getCategoryProgress.getInt(0);
            }
        }
        categoryProgress += 1;

        boolean updateCategoryProgress = DB.updateSingleData(itemCategory, categoryProgress, "updateCategoryProgress");
        if (updateCategoryProgress){
            Log.i("Learn Word Item", "Update Category Progress Success");
        }else{
            Log.i("Learn Word Item", "Update Category Progress Failed");
        }
    }

    public void updateFavorite(Context context, String itemName, String itemType, String modifier){
        DBHelper DB = new DBHelper(context);
        if (modifier.equals("Add")){
            boolean insertNewFavorite = DB.newFavorite(itemName, itemType, "Add");
            if (insertNewFavorite){
                Log.i("Learn Word Item", "Insert New Favorite Success");
            }else{
                Log.i("Learn Word Item", "Insert New Favorite Failed");
            }
        }else if (modifier.equals("Remove")){
            boolean removeFavorite = DB.newFavorite(itemName, itemType, "Remove");
            if (removeFavorite){
                Log.i("Learn Word Item", "Remove Favorite Success");
            }else{
                Log.i("Learn Word Item", "Remove Favorite Failed");
            }
        }
    }

}
