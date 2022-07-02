package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.VideoActivity;

import java.util.Objects;

public class nav_cvsu extends Fragment {
    View view;
    Context context;

    ImageView cvsuVideoThumbnail;
    ImageView cvsuPlayVideo;
    CardView cvsuVideoViewLayout;
    LinearLayout fslGadLayout;
    RelativeLayout cvsuLayout;

    TextView cvsuMission;
    TextView cvsuVision;
    TextView cvsuFslGad;
    TextView cvsuText;
    TextView cvsuLabel;

    String videoPath = "";
    String videoName = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cvsu, container, false);
        context = container.getContext();

        cvsuMission = view.findViewById(R.id.cvsu_mission);
        cvsuVision = view.findViewById(R.id.cvsu_vision);
        cvsuFslGad = view.findViewById(R.id.cvsu_fsl_gad);
        cvsuFslGad.setTypeface(Typeface.DEFAULT_BOLD);
        cvsuMission.setTypeface(Typeface.DEFAULT);
        cvsuVision.setTypeface(Typeface.DEFAULT);
        cvsuText = view.findViewById(R.id.cvsu_text);
        cvsuLabel = view.findViewById(R.id.cvsu_label);
        cvsuVideoThumbnail = view.findViewById(R.id.cvsu_video_thumbnail);
        cvsuPlayVideo = view.findViewById(R.id.cvsu_play_video);
        cvsuVideoViewLayout = view.findViewById(R.id.cvsu_videoViewLayout);
        fslGadLayout = view.findViewById(R.id.fsl_gad_layout);
        cvsuLayout = view.findViewById(R.id.cvsu_layout);

        fslGadLayout.setVisibility(View.VISIBLE);
        cvsuLayout.setVisibility(View.INVISIBLE);
        videoName = "CvSU_Misyon";

        InitializeDesign();
        InitializeOnClick();
        return view;
    }

    private void InitializeDesign(){
        videoPath = videoName.toLowerCase();
        int videoNameConverted = getResources().getIdentifier(videoPath, "raw", context.getPackageName());
        Uri localUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + videoNameConverted);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(Objects.requireNonNull(getActivity()).getApplicationContext(), localUri);
        Bitmap thumb = retriever.getFrameAtTime(10, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
        cvsuVideoThumbnail.setImageBitmap(thumb);
    }

    public void InitializeOnClick()
    {
        cvsuFslGad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fslGadLayout.setVisibility(View.VISIBLE);
                cvsuLayout.setVisibility(View.INVISIBLE);

                cvsuFslGad.setTypeface(Typeface.DEFAULT_BOLD);
                cvsuFslGad.setBackgroundResource(R.drawable.highlight_text);
                cvsuFslGad.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                cvsuMission.setTypeface(Typeface.DEFAULT);
                cvsuMission.setBackgroundResource(android.R.color.transparent);
                cvsuMission.setTextColor(getResources().getColor(R.color.gray_text_color, null));

                cvsuVision.setTypeface(Typeface.DEFAULT);
                cvsuVision.setBackgroundResource(android.R.color.transparent);
                cvsuVision.setTextColor(getResources().getColor(R.color.gray_text_color, null));
            }
        });

        cvsuPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                startActivity(new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), VideoActivity.class).putExtra("ItemName", videoName));
            }
        });

        cvsuVideoViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvsuPlayVideo.performClick();
            }
        });

        cvsuMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fslGadLayout.setVisibility(View.INVISIBLE);
                cvsuLayout.setVisibility(View.VISIBLE);

                cvsuFslGad.setTypeface(Typeface.DEFAULT);
                cvsuFslGad.setBackgroundResource(android.R.color.transparent);
                cvsuFslGad.setTextColor(getResources().getColor(R.color.gray_text_color, null));

                cvsuMission.setTypeface(Typeface.DEFAULT_BOLD);
                cvsuMission.setBackgroundResource(R.drawable.highlight_text);
                cvsuMission.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                cvsuVision.setTypeface(Typeface.DEFAULT);
                cvsuVision.setBackgroundResource(android.R.color.transparent);
                cvsuVision.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                videoName = "CvSU_Misyon";
                cvsuText.setText(getResources().getString(R.string.cvsu_text_mission));
                cvsuLabel.setText(getResources().getString(R.string.cvsu_label_mission));
                InitializeDesign();
            }
        });

        cvsuVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fslGadLayout.setVisibility(View.INVISIBLE);
                cvsuLayout.setVisibility(View.VISIBLE);

                cvsuFslGad.setTypeface(Typeface.DEFAULT);
                cvsuFslGad.setBackgroundResource(android.R.color.transparent);
                cvsuFslGad.setTextColor(getResources().getColor(R.color.gray_text_color, null));

                cvsuVision.setTypeface(Typeface.DEFAULT_BOLD);
                cvsuVision.setBackgroundResource(R.drawable.highlight_text);
                cvsuVision.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                cvsuMission.setTypeface(Typeface.DEFAULT);
                cvsuMission.setBackgroundResource(android.R.color.transparent);
                cvsuMission.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                videoName = "CvSU_Bisyon";
                cvsuLabel.setText(getResources().getString(R.string.cvsu_label_vision));
                cvsuText.setText(getResources().getString(R.string.cvsu_text_vision));
                InitializeDesign();
            }
        });
    }
}