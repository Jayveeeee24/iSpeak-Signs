package com.artemis.ispeaksigns.main_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;


import java.util.Objects;

public class nav_fsl_wotd extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fsl_wotd, container, false);

        if (getArguments() != null)
        {
            String word = getArguments().getString("fsl_wotd");
        }
        return view;
    }
}

//
//            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
//                    .setTitle(word + " - " + getResources().getString(R.string.fsl_label));