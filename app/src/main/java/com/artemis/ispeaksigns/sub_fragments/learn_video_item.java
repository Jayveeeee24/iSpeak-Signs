package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.VideoActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_video_item extends Fragment {

    View view;
    Context context;
    MediaPlayer mediaAudioWord;
    FunctionHelper functionHelper;
    int audio;

    ImageView videoThumbnail;
    ImageView playVideo;
    TextView videoItemName;
    CardView videoViewLayout;
    TextView videoItemCategory;

    String audioName = "";
    String videoPath = "";
    String videoName;
    String videoCategory = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_video_item, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
        {
            videoName = getArguments().getString("learn_video_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(videoName);
        }

        functionHelper = new FunctionHelper();

        videoThumbnail = view.findViewById(R.id.video_thumbnail);
        playVideo = view.findViewById(R.id.play_video);
        videoItemName = view.findViewById(R.id.learn_video_item_name);
        videoViewLayout = view.findViewById(R.id.videoViewLayout);
        videoItemCategory = view.findViewById(R.id.video_item_category);

        InitializeDesign();
        InitializeOnClick();
    }

    private void InitializeDesign(){
        videoPath = "video_demo";
        int videoNameConverted = getResources().getIdentifier(videoPath, "raw", context.getPackageName());
        Uri localUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + videoNameConverted);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(Objects.requireNonNull(getActivity()).getApplicationContext(), localUri);
        Bitmap thumb = retriever.getFrameAtTime(10, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
        videoThumbnail.setImageBitmap(thumb);

        audioName = "audio_demo";
        audio = getResources().getIdentifier(audioName, "raw", context.getPackageName());
        mediaAudioWord = MediaPlayer.create(context, audio);
        videoCategory = "Pagbati";

        videoItemName.setText(videoName);
        videoItemCategory.setText(getResources().getString(R.string.video_item_category_desc, videoCategory));
    }

    private void InitializeOnClick(){
        final ImageView learnVideoItemFavorite = (ImageView) view.findViewById(R.id.learn_video_item_favorite);
        final ImageView learnVideoPlayAudio = (ImageView) view.findViewById(R.id.learn_video_play_audio);

        learnVideoItemFavorite.setOnClickListener(new View.OnClickListener() {
            int isFavorite = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                if (isFavorite == 0) {
                    learnVideoItemFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                } else if (isFavorite == 1) {
                    learnVideoItemFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                }
            }
        });

        learnVideoPlayAudio.setOnClickListener(new View.OnClickListener() {
            int isPlayed = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                if (functionHelper.checkFocusGain(Objects.requireNonNull(getActivity()), mediaAudioWord, null)){
                    if (isPlayed == 0){
                        ImageViewCompat.setImageTintList(learnVideoPlayAudio , ColorStateList.valueOf(getResources().getColor(R.color.outrageous_orange, null)));
                        mediaAudioWord.start();
                        isPlayed = 1;
                        mediaAudioWord.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ImageViewCompat.setImageTintList(learnVideoPlayAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                                mediaAudioWord.stop();
                                mediaAudioWord = MediaPlayer.create(context, audio);
                                isPlayed = 0;
                            }
                        });
                    }else if (isPlayed == 1){
                        ImageViewCompat.setImageTintList(learnVideoPlayAudio, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary, null)));
                        mediaAudioWord.stop();
                        mediaAudioWord = MediaPlayer.create(context, audio);
                        isPlayed = 0;
                    }
                }
            }
        });

        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                startActivity(new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), VideoActivity.class).putExtra("ItemName", videoName));
            }
        });

        videoViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo.performClick();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaAudioWord.stop();
    }

}