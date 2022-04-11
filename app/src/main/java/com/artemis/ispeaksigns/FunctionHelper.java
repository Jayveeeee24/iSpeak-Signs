package com.artemis.ispeaksigns;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.artemis.ispeaksigns.R;
import com.google.android.exoplayer2.ExoPlayer;

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

}
