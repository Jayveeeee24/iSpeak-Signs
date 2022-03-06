package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;

import java.util.Objects;

public class learn_word_list extends Fragment {

    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_word_list, container, false);
        context = container.getContext();

        setProgressSetup();
        return view;
    }

    public void setProgressSetup()
    {
        String word = "";
        if (getArguments() != null) {
            word = getArguments().getString("category");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(getResources().getString(R.string.learn_word_title_set) + " " + word);
        }

        String str = word.toLowerCase();
        String[] arrStr = str.split(" ");
        String imageName = "ic";
        String converted = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (String a : arrStr)
        {
            if (arrStr.length == 1) {
                str = "_" + str;
                break;
            }
            str = stringBuilder.append("_").append(a).toString();
        }
        imageName =imageName + str ;

        ImageView categoryImage = view.findViewById(R.id.categoryImage);
        categoryImage.setImageResource(getResources().getIdentifier(imageName, "drawable", context.getPackageName()));

    }
}