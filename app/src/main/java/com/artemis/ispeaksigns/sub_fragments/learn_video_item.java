package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.VideoView;

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_video_item extends Fragment {

    View view;
    Context context;
    VideoView learnVideoView;
    MediaController mediaController;
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

        mediaController = new MediaController(context);
        learnVideoView = view.findViewById(R.id.learn_video_item_videoview);

        String word;

        if (getArguments() != null)
        {
            word = getArguments().getString("learn_video_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(word);
        }

        loadVideoView();

    }

    public void loadVideoView(){
        mediaController.setAnchorView(learnVideoView);
        Uri localUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_demo);
        learnVideoView.setVideoURI(localUri);
        learnVideoView.setMediaController(mediaController);

        learnVideoView.start();
    }
}