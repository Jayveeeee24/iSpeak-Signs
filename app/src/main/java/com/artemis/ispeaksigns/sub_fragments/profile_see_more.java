package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressItem;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class profile_see_more extends Fragment {

    View view;
    Context context;
    DBHelper DB;
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
        DB = new DBHelper(context);
        InitializeRecycler();
    }

    private void InitializeRecycler(){
        ArrayList<ProfileProgressItem> profileProgressItems = new ArrayList<>();
        RecyclerView profileSeeMoreRecycler = view.findViewById(R.id.profile_see_more_recycler);
        profileSeeMoreRecycler.setNestedScrollingEnabled(false);

        Cursor profileSeeMoreCursor = DB.getCategory("", "all");

        String[] categoryName = new String[profileSeeMoreCursor.getCount()];
        String[] imageUrls = new String[categoryName.length];
        String [] bgColors = new String[categoryName.length];
        int[] images = new int[imageUrls.length];
        int[] colors = new int[bgColors.length];
        int[] categoryTotal = new int[categoryName.length];
        int[] categoryProgress = new int[categoryName.length];
        int[] categoryPercent = new int[categoryProgress.length];
        String[] categoryType = new String[categoryName.length];
        String[] categoryPercentConverted = new String[categoryPercent.length];

//        FunctionHelper functionHelper = new FunctionHelper();
//        for (int i = 0; i<categoryName.length; i++){
//            imageUrls[i] = functionHelper.getImageLogo(categoryName[i]);
//        }
        if (profileSeeMoreCursor.getCount() == 0){
            Toast.makeText(context, "Ang database ay walang laman, Pakiulit na lamang", Toast.LENGTH_SHORT).show();
            return;
        }else{
            int i = 0;
            while(profileSeeMoreCursor.moveToNext()){
                categoryName[i] = profileSeeMoreCursor.getString(0);
                bgColors[i] = profileSeeMoreCursor.getString(1);
                categoryTotal[i] = profileSeeMoreCursor.getInt(2);
                categoryType[i] = profileSeeMoreCursor.getString(3);
                imageUrls[i] = profileSeeMoreCursor.getString(4);
                categoryProgress[i] = profileSeeMoreCursor.getInt(5);
                i++;
            }
        }

        for (int i = 0; i<categoryName.length; i++){
            categoryPercent[i] = categoryProgress[i] * 100/ categoryTotal[i];
            images[i] = getResources().getIdentifier(imageUrls[i], "drawable", context.getPackageName());
            colors[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
            categoryPercentConverted[i] = categoryPercent[i] + "%";

            Log.d("Try", Integer.toString(100 * 4/7));
            Log.d("Percent", Integer.toString(categoryPercent[i]));
            Log.d("Progress", Integer.toString(categoryProgress[i]));
            Log.d("Total", Integer.toString(categoryTotal[i]));
            Log.d("Percent Converted", categoryPercentConverted[i]);
        }

        for (int i = 0; i<categoryName.length; i++)
        {
            profileProgressItems.add(new ProfileProgressItem(colors[i], images[i], categoryName[i],
                    categoryPercentConverted[i], categoryPercent[i], categoryType[i]));
        }

        ProfileProgressListAdapter adapter = new ProfileProgressListAdapter();
        adapter.setProfileProgressItems(profileProgressItems);

        profileSeeMoreRecycler.setAdapter(adapter);
        profileSeeMoreRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

    }
}