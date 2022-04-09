package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoActivity extends AppCompatActivity {

    ExoPlayer exoPlayer;
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView bt_fullscreen;
    ImageView bt_lockscreen;
    TextView exoItemName;
    boolean isFullScreen = false;
    boolean isLock = false;

    String videoName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        bt_lockscreen = findViewById(R.id.exo_lock);
        exoItemName = findViewById(R.id.exo_item_name);

        InitializeDesign();
    }
    private void InitializeDesign(){
        Intent intent = getIntent();
        videoName = intent.getStringExtra("ItemName");

        exoItemName.setText(videoName);

        bt_fullscreen.setOnClickListener(view ->
        {
            if (!isFullScreen)
            {
                bt_fullscreen.setImageDrawable(
                        ContextCompat
                                .getDrawable(getApplicationContext(), R.drawable.ic_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else
            {
                bt_fullscreen.setImageDrawable(ContextCompat
                        .getDrawable(getApplicationContext(), R.drawable.ic_fullscreen));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            isFullScreen = !isFullScreen;
        });
        bt_lockscreen.setOnClickListener(view ->
        {
            if (!isLock)
            {
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_lock));
            } else
            {
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_outline_lock_open));
            }
            isLock = !isLock;
            lockScreen(isLock);
        });
        exoPlayer = new ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build();
        playerView.setPlayer(exoPlayer);
        //screen alway active
        playerView.setKeepScreenOn(true);
        //track state
        exoPlayer.addListener(new Player.Listener()
        {
            @Override
            public void onPlaybackStateChanged(int playbackState)
            {
                //when data video fetch stream from internet
                if (playbackState == Player.STATE_BUFFERING)
                {
                    progressBar.setVisibility(View.VISIBLE);

                } else if (playbackState == Player.STATE_READY) {
                    //then if streamed is loaded we hide the progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        videoName = "video_demo";
        int videoNameConverted = getResources().getIdentifier(videoName, "raw", getPackageName());
        Uri videoUrl = Uri.parse("android.resource://" + getPackageName() + "/" + videoNameConverted);
        MediaItem media = MediaItem.fromUri(videoUrl);
        exoPlayer.setMediaItem(media);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    private void lockScreen(boolean lock)
    {
        LinearLayout sec_mid = findViewById(R.id.sec_controlvid1);
        LinearLayout sec_bottom = findViewById(R.id.sec_controlvid2);
        if(lock)
        {
            sec_mid.setVisibility(View.INVISIBLE);
            sec_bottom.setVisibility(View.INVISIBLE);
        }
        else
        {
            sec_mid.setVisibility(View.VISIBLE);
            sec_bottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed()
    {
        //on lock screen back press button not work
        if(isLock) return;

        //if user is in landscape mode we turn to portriat mode first then we can exit the app.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            bt_fullscreen.performClick();
        }
        else super.onBackPressed();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        exoPlayer.stop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        exoPlayer.pause();
    }

}