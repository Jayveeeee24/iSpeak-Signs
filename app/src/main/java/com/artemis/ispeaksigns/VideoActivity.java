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
    FunctionHelper functionHelper;
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView bt_fullscreen;
    TextView exoItemName;
    boolean isFullScreen = false;

    String videoName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        functionHelper = new FunctionHelper();
        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
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

        exoPlayer = new ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build();
        playerView.setPlayer(exoPlayer);
        playerView.setKeepScreenOn(true);
        //track state
        exoPlayer.addListener(new Player.Listener()
        {
            @Override
            public void onPlaybackStateChanged(int playbackState)
            {
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
        if (functionHelper.checkFocusGain(this, null ,exoPlayer)){
            exoPlayer.prepare();
            exoPlayer.play();
        }
    }

    @Override
    public void onBackPressed()
    {

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