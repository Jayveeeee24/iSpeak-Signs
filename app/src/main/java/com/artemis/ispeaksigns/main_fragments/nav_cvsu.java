package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemis.ispeaksigns.R;

public class nav_cvsu extends Fragment {
    View view;
    Context context;
    TextView cvsuMission;
    TextView cvsuVision;
    TextView cvsuText;
    TextView cvsuLabel;
    String type_selected;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cvsu, container, false);
        cvsuMission = view.findViewById(R.id.cvsu_mission);
        cvsuVision = view.findViewById(R.id.cvsu_vision);
        cvsuMission.setTypeface(Typeface.DEFAULT_BOLD);
        cvsuVision.setTypeface(Typeface.DEFAULT);
        cvsuText = view.findViewById(R.id.cvsu_text);
        cvsuLabel = view.findViewById(R.id.cvsu_label);
        type_selected = "mission";

        InitializeOnClick();
        return view;
    }

    public void InitializeOnClick()
    {
        cvsuMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvsuMission.setTypeface(Typeface.DEFAULT_BOLD);
                cvsuMission.setBackgroundResource(R.drawable.highlight_text);
                cvsuMission.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                cvsuVision.setTypeface(Typeface.DEFAULT);
                cvsuVision.setBackgroundResource(android.R.color.transparent);
                cvsuVision.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                type_selected = "mission";
                cvsuText.setText(getResources().getString(R.string.cvsu_text_mission));
                cvsuLabel.setText(getResources().getString(R.string.cvsu_label_mission));
            }
        });

        cvsuVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvsuVision.setTypeface(Typeface.DEFAULT_BOLD);
                cvsuVision.setBackgroundResource(R.drawable.highlight_text);
                cvsuVision.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                cvsuMission.setTypeface(Typeface.DEFAULT);
                cvsuMission.setBackgroundResource(android.R.color.transparent);
                cvsuMission.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                type_selected = "vision";
                cvsuLabel.setText(getResources().getString(R.string.cvsu_label_vision));
                cvsuText.setText(getResources().getString(R.string.cvsu_text_vision));
            }
        });
    }
}