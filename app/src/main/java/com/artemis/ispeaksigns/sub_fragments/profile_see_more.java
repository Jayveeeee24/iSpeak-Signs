package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressItem;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressListAdapter;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileSeeMoreItem;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileSeeMoreListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class profile_see_more extends Fragment {

    View view;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_see_more, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitializeRecycler();
    }

    private void InitializeRecycler(){
        ArrayList<ProfileSeeMoreItem> profileSeeMoreItems = new ArrayList<>();

        RecyclerView profileSeeMoreRecycler = view.findViewById(R.id.profile_see_more_recycler);
        profileSeeMoreRecycler.setNestedScrollingEnabled(false);

        //TODO change this into database dependent resources
        String[] imageUrls = new String[]
                {"ic_alpabeto", "ic_kasarian", "ic_hugis", "ic_araw_ng_linggo", "ic_miyembro_ng_pamilya",
                        "ic_alpabeto", "ic_kasarian", "ic_hugis", "ic_araw_ng_linggo", "ic_miyembro_ng_pamilya",
                        "ic_alpabeto", "ic_kasarian", "ic_hugis", "ic_araw_ng_linggo", "ic_miyembro_ng_pamilya",};
        String [] bgColors = new String[]
                {"golden_puppy", "japanese_indigo", "outrageous_orange", "apple", "plump_purple",
                        "golden_puppy", "japanese_indigo", "outrageous_orange", "apple", "plump_purple",
                        "golden_puppy", "japanese_indigo", "outrageous_orange", "apple", "plump_purple",};

        int[] images = new int[imageUrls.length];
        for (int i = 0; i<imageUrls.length; i++) {
            images[i] = getResources().getIdentifier(imageUrls[i], "drawable", context.getPackageName());
        }

        int[] colors = new int[bgColors.length];
        for (int i = 0; i<bgColors.length; i++) {
            colors[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
        }

        int[] categoryProgress = new int[] {45, 36, 80, 100, 0,
                45, 36, 80, 100, 0,
                45, 36, 80, 100, 0,
        };
        String[] categoryName = new String[]
                {
                        "Alpabeto", "Kasarian", "Hugis", "Araw ng Linggo", "Miyembro ng Pamilya",
                        "Alpabeto", "Kasarian", "Hugis", "Araw ng Linggo", "Miyembro ng Pamilya",
                        "Alpabeto", "Kasarian", "Hugis", "Araw ng Linggo", "Miyembro ng Pamilya",
                };

        int[] categoryPercent = new int[] {12, 67, 8, 90, 0,
                12, 67, 8, 90, 0,
                12, 67, 8, 90, 0,};

        String[] categoryPercentConverted = new String[categoryPercent.length];

        for (int i = 0; i<categoryPercent.length; i++)
        {
            categoryPercentConverted[i] = categoryPercent[i] + "%";
        }

        String[] categoryType = new String[]{
                "Salita", "Parirala", "Salita", "Salita", "Parirala",
                "Salita", "Parirala", "Salita", "Salita", "Parirala",
                "Salita", "Parirala", "Salita", "Salita", "Parirala",
        };

        for (int i = 0; i<categoryName.length; i++)
        {
            profileSeeMoreItems.add(new ProfileSeeMoreItem(colors[i], images[i], categoryName[i],
                    categoryPercentConverted[i], categoryProgress[i], categoryType[i]));
        }

        ProfileSeeMoreListAdapter adapter = new ProfileSeeMoreListAdapter();
        adapter.setProfileSeeMoreItems(profileSeeMoreItems);

        profileSeeMoreRecycler.setAdapter(adapter);
        profileSeeMoreRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


    }
}