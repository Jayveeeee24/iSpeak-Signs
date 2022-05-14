package com.artemis.ispeaksigns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class VideoActivity extends AppCompatActivity {

    DBHelper DB;
    ExoPlayer exoPlayer;
    FunctionHelper functionHelper;
    PlayerView playerView;

    ProgressBar progressBar;
    ImageView bt_fullscreen;
    TextView exoItemName;
    ImageView videoItemFavorite;
    ImageView btnBack;
    YouTubePlayerView youTubePlayerView;
    LinearLayout youtube_layout;
    RelativeLayout exo_layout;

    boolean isFullScreen = false;
    float speed = 1.0f;
    String videoName = "";
    int isFavorite = 0;
    String itemType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        DB = new DBHelper(this);
        functionHelper = new FunctionHelper();
        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        exoItemName = findViewById(R.id.exo_item_name);
        videoItemFavorite = findViewById(R.id.video_item_favorite);
        btnBack = findViewById(R.id.btn_back);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youtube_layout = findViewById(R.id.youtube_player_layout);
        exo_layout = findViewById(R.id.exo_player_layout);

        InitializeDesign();
        setUpOnClick();
    }

    private void InitializeDesign(){

        Intent intent = getIntent();
        videoName = intent.getStringExtra("ItemName");

        exoItemName.setText(videoName);
        if (videoName.equals("CvSU Misyon") || videoName.equals("CvSU Bisyon")){
            videoItemFavorite.setEnabled(false);
            videoItemFavorite.setVisibility(View.INVISIBLE);

            playExo();
        }else if (videoName.contains("@")){
            videoName = videoName.substring(1);
            String videoId = "FZ9L7AejW9Q";
            youtube_layout.setVisibility(View.VISIBLE);
            exo_layout.setVisibility(View.INVISIBLE);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });

            youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                @Override
                public void onYouTubePlayerEnterFullScreen() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }

                @SuppressLint("SourceLockedOrientationActivity")
                @Override
                public void onYouTubePlayerExitFullScreen() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            });
        }else {
            videoItemFavorite.setEnabled(true);
            videoItemFavorite.setVisibility(View.VISIBLE);
            int isLearned = 0;
            String itemCategory = "";
            Cursor learnVideoItemCursor = DB.getItem(videoName, "");
            if (learnVideoItemCursor.getCount() != 0) {
                while (learnVideoItemCursor.moveToNext()) {
                    itemCategory = learnVideoItemCursor.getString(1);
                    itemType = learnVideoItemCursor.getString(2);
                    isLearned = learnVideoItemCursor.getInt(3);
                }
            }

            if (isLearned == 0) {
                functionHelper.updateisLearnedProgress(this, itemCategory, videoName);
            }

            Cursor learnWordFavoriteCursor = DB.getItem(videoName, "getItemHeart");
            if (learnWordFavoriteCursor.getCount() == 0) {
                videoItemFavorite.setImageResource(R.drawable.ic_favorites_outline);
                isFavorite = 0;
            } else {
                videoItemFavorite.setImageResource(R.drawable.ic_menu_favorites);
                isFavorite = 1;
            }

            playExo();
        }

    }

    private void playExo(){
        exoPlayer = new ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build();

        playerView.setPlayer(exoPlayer);
        playerView.setKeepScreenOn(true);
        PlaybackParameters param = new PlaybackParameters(speed);
        exoPlayer.setPlaybackParameters(param);
        //track state
        exoPlayer.addListener(new Player.Listener()
        {
            @Override
            public void onPlaybackStateChanged(int playbackState)
            {
                if (playbackState == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                }else if (playbackState == Player.STATE_READY) {
                    //then if streamed is loaded we hide the progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        int videoNameConverted = getResources().getIdentifier("video_demo", "raw", getPackageName());
        Uri videoUrl = Uri.parse("android.resource://" + getPackageName() + "/" + videoNameConverted);
        MediaItem media = MediaItem.fromUri(videoUrl);
        exoPlayer.setMediaItem(media);
        if (functionHelper.checkFocusGain(this, null ,exoPlayer)){
            exoPlayer.prepare();
            exoPlayer.play();
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void setUpOnClick(){
        final TextView playbackSpeed = findViewById(R.id.playback_speed);
        playbackSpeed.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this, R.anim.image_button_clicked));
                if (count == 0){// normal speed to 0.50
                    speed = 0.50f;
                    count += 1;
                    playbackSpeed.setText("0.50x");
                }else if (count == 1){// 0.50 to 0.75
                    speed = 0.75f;
                    count += 1;
                    playbackSpeed.setText("0.75x");
                }else if (count ==2){// 0.75 back to normal speed
                    speed = 1.0f;
                    count = 0;
                    playbackSpeed.setText("1.0x");
                }
                PlaybackParameters param = new PlaybackParameters(speed);
                exoPlayer.setPlaybackParameters(param);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this, R.anim.image_button_clicked));
                onBackPressed();
            }
        });

        bt_fullscreen.setOnClickListener(view ->
        {
            view.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this, R.anim.image_button_clicked));
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

        final ImageView videoFavorite = (ImageView) findViewById(R.id.video_item_favorite);
        videoFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this, R.anim.image_button_clicked));
                if (isFavorite == 0) {
                    videoFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                    functionHelper.updateFavorite(VideoActivity.this, videoName, itemType, "Add");
                } else if (isFavorite == 1) {
                    videoFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                    functionHelper.updateFavorite(VideoActivity.this, videoName, null, "Remove");
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {

        if (youtube_layout.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }
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
        if (exo_layout.getVisibility() == View.VISIBLE){
            exoPlayer.stop();
        }else{
            youTubePlayerView.release();
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (exo_layout.getVisibility() == View.VISIBLE){
            exoPlayer.release();
        }else{
            youTubePlayerView.release();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (exo_layout.getVisibility() == View.VISIBLE){
            exoPlayer.pause();
        }else{
            youTubePlayerView.release();
        }
    }

}