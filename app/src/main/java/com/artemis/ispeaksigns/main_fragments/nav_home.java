package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_home.HomeCategoryItem;
import com.artemis.ispeaksigns.adapter_list_home.HomeRecyclerAdapter;
import com.artemis.ispeaksigns.adapter_list_home.HomeVideoCategoryItem;
import com.artemis.ispeaksigns.adapter_list_home.HomeVideoRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class nav_home extends Fragment  {

    Context context;
    DBHelper DB;
    View view;
    FunctionHelper functionHelper;

    RecyclerView learnRecView;
    RecyclerView learnVideoRecView;
    TextView txtHomeFslName;
    ImageView imageFslFavorite;
    TextView txtHomeFslDesc;

    String wotdItem = "";
    String itemType = "";
    int isFavorite = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        learnRecView = view.findViewById(R.id.learnRecycler);
        learnRecView.setNestedScrollingEnabled(false);
        learnVideoRecView = view.findViewById(R.id.learnVideoRecycler);
        learnVideoRecView.setNestedScrollingEnabled(false);

        txtHomeFslName = view.findViewById(R.id.txt_home_fsl_name);
        imageFslFavorite = view.findViewById(R.id.image_favorite);
        txtHomeFslDesc = view.findViewById(R.id.txt_home_fsl_desc);
        DB = new DBHelper(context);
        functionHelper = new FunctionHelper();

        InitializeGetData();
        InitializeRecyclerView();
        InitializeClickListener();
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        InitializeGetData();
    }

    public void InitializeGetData(){
        //this function fetches the word of the day and its favorite status
        String[] getFavorite = functionHelper.getWordOfTheDay(context);
        wotdItem = getFavorite[0];
        itemType = getFavorite[1];
        Cursor learnWordFavoriteCursor = DB.getItem(wotdItem, "getItemHeart");

        if (learnWordFavoriteCursor.getCount() == 0){
            imageFslFavorite.setImageResource(R.drawable.ic_favorites_outline);
            isFavorite = 0;
        }else{
            imageFslFavorite.setImageResource(R.drawable.ic_menu_favorites);
            isFavorite = 1;
        }
        txtHomeFslName.setText(wotdItem);
    }

    private void InitializeRecyclerView(){
        ArrayList<HomeCategoryItem> homeCategoryItems = new ArrayList<>();
        ArrayList<HomeVideoCategoryItem> homeVideoCategoryItems = new ArrayList<>();

        //this fetches categories which its categoryProgress is not equal to categoryTotalItems
        Cursor homeLearnCursor = DB.getCategory("Salita", "By5");
        Cursor homeVideoCursor = DB.getCategory("Parirala", "By5");

        //this fetches categories without any condition (regardless of the categoryProgress if 100% or not)
        Cursor allLearnCursor = DB.getCategory("Salita", "All");
        Cursor allVideoCursor = DB.getCategory("Parirala", "All");

        //below is the implementation for word recyclerview
        String[] categoryName = new String[5];
        String[] imageUrls = new String[categoryName.length];
        int[] categoryProgress = new int[categoryName.length];
        int[] categoryPercent = new int[categoryName.length];
        int[] totalItems = new int[categoryName.length];
        String [] bgColors = new String[categoryName.length];
        int[] images = new int[imageUrls.length];
        int[] colors = new int[bgColors.length];
        
        //the following condition is the actual process of
        //fetching the categories from the database
        /*
        * SIDE NOTES: the limit for display in home in each recyclerview is as follows
        * - (5) categories for word recycler and (3) categories for phrase recycler in an array
        * SIDE NOTES: the below implementation aims to display categories in recyclerview
        * 1. with priority to the number of progress from the total items
        * ex. categories with 2/7 progress always have priority for display than category with 1/7 progress
        * 2. Categories that are already finished or learned completely must not be displayed
        * 3. Only those with progress greater than 0 but less than 100% must be displayed
        * 4. however, when the amount of categories which its progress is not 100% are lower than the limit
        * ex. array{cat1 - 80%, cat2 - 76%, cat3 - 0%} is not equal to the limit size of 5 in word recycler
        * 4.1 another function will fill the gap with categories that has 100% progress at the end of the array
        * ex. array{cat1 - 80%, cat2 - 76%, cat3 - 0%, cat4 - 100%, cat5 - 100%}
        * */
        if (homeLearnCursor.getCount() == 0){//this condition checks if there are no categories which its categoryProgress (!=) to categoryTotalItems
            
            //this fetches all categories regardless if categoryProgress != categoryTotalItems 
            if (allLearnCursor.getCount() == 0){
                Toast.makeText(context, "No value on database found!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                int j = 0;
                while (allLearnCursor.moveToNext() && j < 5){
                    categoryName[j] = allLearnCursor.getString(0);
                    bgColors[j] = allLearnCursor.getString(1);
                    totalItems[j] = allLearnCursor.getInt(2);
                    imageUrls[j] = allLearnCursor.getString(4);
                    categoryProgress[j] = allLearnCursor.getInt(5);
                    j++;
                }
            }
        }else{
            //this fetches the remaining categories that its categoryProgress != categoryTotalItems
            int i = 0;
            while (homeLearnCursor.moveToNext()){
                categoryName[i] = homeLearnCursor.getString(0);
                bgColors[i] = homeLearnCursor.getString(1);
                totalItems[i] = homeLearnCursor.getInt(2);
                imageUrls[i] = homeLearnCursor.getString(4);
                categoryProgress[i] = homeLearnCursor.getInt(5);
                i++;
            }

            //if the above cursor count is lower than the array size (5) 
            //this condition will fetch data filling the gaps in the above cursor
            //lets say the cursor returns only 4 categories, 
            //the condition will fetch 1 category 
            //regardless if its categoryProgress != categoryTotalItems 
            if (homeLearnCursor.getCount() < 5){
                if (allLearnCursor.getCount() == 0){
                    Toast.makeText(context, "No value on database found!", Toast.LENGTH_SHORT).show();
                }else{
                    int j = homeLearnCursor.getCount();
                    while (allLearnCursor.moveToNext() && j < 5){
                        categoryName[j] = allLearnCursor.getString(0);
                        bgColors[j] = allLearnCursor.getString(1);
                        totalItems[j] = allLearnCursor.getInt(2);
                        imageUrls[j] = allLearnCursor.getString(4);
                        categoryProgress[j] = allLearnCursor.getInt(5);
                        j++;
                    }
                }
            }
        }

        //this function converts the data fetched in the database
        //categoryProgress and totalItems = categoryPercentage
        //string images = resource id of images
        //string colors = resource id of colors
        for (int i = 0; i<categoryName.length; i++){
            categoryPercent[i] = categoryProgress[i] * 100 / totalItems[i];
            images[i] = getResources().getIdentifier(imageUrls[i], "drawable", context.getPackageName());
            colors[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
        }

       for (int i =0; i<categoryName.length; i++) {
           homeCategoryItems.add(new HomeCategoryItem(categoryName[i], colors[i], categoryPercent[i], images[i]));
       }

        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter();
        adapter.setCategoryItems(homeCategoryItems);
        learnRecView.setAdapter(adapter);
        learnRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        
        //below is the implementation for video recyclerview
        String[] categoryVideoName = new String[3];
        imageUrls = new String[categoryVideoName.length];
        bgColors = new String[categoryVideoName.length];
        images = new int[categoryVideoName.length];
        colors = new int[categoryVideoName.length];
        
        //this function is same in implementation as the above conditions for the words recyclerview
        if (homeVideoCursor.getCount() == 0){
            if (allVideoCursor.getCount() == 0){
                Toast.makeText(context, "No value on database found!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                int j = 0;
                while (allVideoCursor.moveToNext() && j < 3){
                    categoryVideoName[j] = allVideoCursor.getString(0);
                    bgColors[j] = allVideoCursor.getString(1);
                    imageUrls[j] = allVideoCursor.getString(2);
                    j++;
                }
            }
        }else{
            int i = 0;
            while (homeVideoCursor.moveToNext()){
                categoryVideoName[i] = homeVideoCursor.getString(0);
                bgColors[i] = homeVideoCursor.getString(1);
                imageUrls[i] = homeVideoCursor.getString(2);
                i++;
            }
            if (homeVideoCursor.getCount() < 3){
                if (allVideoCursor.getCount() == 0){
                    Toast.makeText(context, "No value on database found!", Toast.LENGTH_SHORT).show();
                }else{
                    int j = homeVideoCursor.getCount();
                    while (allVideoCursor.moveToNext() && j < 3){
                        categoryVideoName[j] = allVideoCursor.getString(0);
                        bgColors[j] = allVideoCursor.getString(1);
                        imageUrls[j] = allVideoCursor.getString(2);
                        j++;
                    }
                }
            }
        }

        for (int i = 0; i<categoryVideoName.length; i++){
            images[i] = getResources().getIdentifier(imageUrls[i], "drawable", context.getPackageName());
            colors[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
        }

        for (int i =0; i<categoryVideoName.length; i++){
            homeVideoCategoryItems.add(new HomeVideoCategoryItem(categoryVideoName[i], colors[i], images[i]));
        }

        HomeVideoRecyclerAdapter adapter1 = new HomeVideoRecyclerAdapter();
        adapter1.setHomeVideoCategoryItems(homeVideoCategoryItems);
        learnVideoRecView.setAdapter(adapter1);
        learnVideoRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    private void InitializeClickListener()
    {
        //Definitions
        final ImageView image_favorite = (ImageView) view.findViewById(R.id.image_favorite);
        CardView card_fsl_resource = view.findViewById(R.id.card_fsl_resource);
        TextView home_learn_see_more = view.findViewById(R.id.homeWordSeeMore);
        TextView txtHomeSeeMore = view.findViewById(R.id.homeVideoSeeMore);

        image_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                    if (isFavorite == 0) {
                        image_favorite.setImageResource(R.drawable.ic_menu_favorites);
                        isFavorite = 1;
                        functionHelper.updateFavorite(context, wotdItem, itemType, "Add");
                    } else if (isFavorite == 1) {
                        image_favorite.setImageResource(R.drawable.ic_favorites_outline);
                        isFavorite = 0;
                        functionHelper.updateFavorite(context, wotdItem, itemType, "Remove");
                    }
            }
        });

        card_fsl_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] getFavorite = functionHelper.getWordOfTheDay(context);
                String word = "";
                word = getFavorite[0];
                Bundle bundle = new Bundle();
                bundle.putString("learn_word_item", word);

                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                Navigation.findNavController(view).navigate(R.id.learn_word_item, bundle, navBuilder.build());
            }
        });

        home_learn_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category_type", "Salita");
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(android.R.anim.slide_in_left)
                        .setExitAnim(android.R.anim.slide_out_right)
                        .setPopEnterAnim(android.R.anim.slide_in_left)
                        .setPopExitAnim(android.R.anim.slide_out_right);
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_learn, bundle, navBuilder.build());

            }
        });

        txtHomeSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category_type", "Parirala");
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(android.R.anim.slide_in_left)
                        .setExitAnim(android.R.anim.slide_out_right)
                        .setPopEnterAnim(android.R.anim.slide_in_left)
                        .setPopExitAnim(android.R.anim.slide_out_right);
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_learn, bundle, navBuilder.build());

            }
        });
    }
}