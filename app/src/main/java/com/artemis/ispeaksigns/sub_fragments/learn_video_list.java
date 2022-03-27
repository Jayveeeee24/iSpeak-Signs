package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class learn_video_list extends Fragment {

    View view;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_video_list, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String kategorya = "";
        if (getArguments() != null) {
            kategorya = getArguments().getString("Kategorya");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(kategorya);
        }

        FunctionHelper functionHelper = new FunctionHelper();
//        String str = kategorya.toLowerCase();
//        String[] arrStr = str.split("-");
//        String imageName = "ic";
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String a : arrStr)
//        {
//            if (arrStr.length == 1) {
//                str = "_" + str;
//                break;
//            }
//            str = stringBuilder.append("_").append(a).toString();
//        }
//        imageName =imageName + str ;

        ImageView categoryVideoImage = view.findViewById(R.id.categoryVideoImage);
        categoryVideoImage.setImageResource(getResources().getIdentifier(functionHelper.getImageLogo(kategorya), "drawable", context.getPackageName()));

    }
}