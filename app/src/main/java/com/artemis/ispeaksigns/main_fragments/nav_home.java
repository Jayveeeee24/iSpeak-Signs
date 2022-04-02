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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_home.HomeCategoryItem;
import com.artemis.ispeaksigns.adapter_list_home.HomeRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class nav_home extends Fragment  {

    Context context;
    View view;
    RecyclerView learnRecView;
    RecyclerView learnVideoRecView;
    DBHelper DB;
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
        DB = new DBHelper(context);

        InitializeRecyclerView();
        InitializeClickListener();
    }

    private void InitializeRecyclerView()
    {
        ArrayList<HomeCategoryItem> homeCategoryItems = new ArrayList<>();
        Cursor homeLearnCursor = DB.getAllCategory("Salita", "By5");
        String[] categoryName = new String[homeLearnCursor.getCount()];
        String[] imageUrls = new String[categoryName.length];
        int[] categoryProgress = new int[categoryName.length];
        int[] categoryPercent = new int[categoryName.length];
        String[] totalItems = new String[categoryName.length];
        String [] bgColors = new String[categoryName.length];
        int[] images = new int[imageUrls.length];
        int[] colors = new int[bgColors.length];
        if (homeLearnCursor.getCount() == 0){
            categoryName = new String[]{
                            "Alpabeto", "Kasarian", "Hugis", "Araw ng Linggo", "Miyembro ng Pamilya"
                    };

            imageUrls = new String[]{
                    "ic_alpabeto", "ic_kasarian", "ic_hugis", "ic_araw_ng_linggo", "ic_miyembro_ng_pamilya"
            };
            categoryProgress = new int[] {45, 36, 80, 100, 0};
            bgColors = new String[]
                    {"golden_puppy", "japanese_indigo", "outrageous_orange", "apple", "plump_purple"};
        }else{
            int i = 0;
            while (homeLearnCursor.moveToNext()){
                categoryName[i] = homeLearnCursor.getString(0);
                bgColors[i] = homeLearnCursor.getString(1);
                totalItems[i] = homeLearnCursor.getString(2);
                imageUrls[i] = homeLearnCursor.getString(4);
                categoryProgress[i] = homeLearnCursor.getInt(5);
                i++;
            }
        }


//       FunctionHelper functionHelper = new FunctionHelper();
//       for (int i = 0; i<categoryName.length; i++){
//           imageUrls[i] = functionHelper.getImageLogo(categoryName[i]);
//       }

        for (int i = 0; i<categoryName.length; i++)
        {
            categoryPercent[i] = categoryProgress[i] * 100 / Integer.parseInt(totalItems[i]);
            images[i] = getResources().getIdentifier(imageUrls[i], "drawable", context.getPackageName());
            colors[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
        }

       for (int i =0; i<categoryName.length; i++)
       {
           homeCategoryItems.add(new HomeCategoryItem(categoryName[i], colors[i], categoryPercent[i], images[i]));
       }

        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter();
        adapter.setCategoryItems(homeCategoryItems);

        learnRecView.setAdapter(adapter);
        learnRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

    }

    private void InitializeClickListener()
    {
        //Definitions
        final ImageView image_favorite = (ImageView) view.findViewById(R.id.image_favorite);
        CardView card_fsl_resource = view.findViewById(R.id.card_fsl_resource);
        TextView home_learn_see_more = view.findViewById(R.id.homeWordSeeMore);
        TextView txt_home_fsl_name = view.findViewById(R.id.txt_home_fsl_name);
        TextView txtHomeSeeMore = view.findViewById(R.id.homeVideoSeeMore);

        image_favorite.setOnClickListener(new View.OnClickListener() {
            int isFavorite = 0;
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
                    if (isFavorite == 0) {
                        image_favorite.setImageResource(R.drawable.ic_menu_favorites);
                        isFavorite = 1;
                    } else if (isFavorite == 1) {
                        image_favorite.setImageResource(R.drawable.ic_favorites_outline);
                        isFavorite = 0;
                    }
            }
        });

        card_fsl_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fsl_wotd", txt_home_fsl_name.getText().toString());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_fsl_wotd, bundle, navBuilder.build());
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