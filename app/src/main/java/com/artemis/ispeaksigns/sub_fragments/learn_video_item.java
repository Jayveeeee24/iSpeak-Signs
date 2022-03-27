package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_video_item extends Fragment {

    View view;
    Context context;
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

        String word;

        if (getArguments() != null)
        {
            word = getArguments().getString("learn_video_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(word);
        }


    }
}