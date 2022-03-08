package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.os.Bundle;

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

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.home_list_adapters.LearnCategoryItem;
import com.artemis.ispeaksigns.home_list_adapters.LearnRecyclerAdapter;

import java.util.ArrayList;

public class nav_home extends Fragment  {

    Context context;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();

        InitializeLearnRecyclerView();
        InitializeClickListener();
        return view;
    }

    private void InitializeLearnRecyclerView()
    {
        RecyclerView learnRecView = view.findViewById(R.id.learnRecycler);
        ArrayList<LearnCategoryItem> learnCategoryItems = new ArrayList<>();

        //TODO change this into database dependent resources
       String[] imageUrls = new String[]
               {"ic_alpabeto", "ic_kasarian", "ic_hugis", "ic_araw_ng_linggo", "ic_miyembro_ng_pamilya"};
       String [] bgColors = new String[]
               {"golden_puppy", "japanese_indigo", "outrageous_orange", "apple", "plump_purple"};

       int[] images = new int[]
               {
                       getResources().getIdentifier(imageUrls[0], "drawable", context.getPackageName()),
                       getResources().getIdentifier(imageUrls[1], "drawable", context.getPackageName()),
                       getResources().getIdentifier(imageUrls[2], "drawable", context.getPackageName()),
                       getResources().getIdentifier(imageUrls[3], "drawable", context.getPackageName()),
                       getResources().getIdentifier(imageUrls[4], "drawable", context.getPackageName()),
               };

       int[] colors = new int[]
               {
                       getResources().getIdentifier(bgColors[0], "color", context.getPackageName()),
                       getResources().getIdentifier(bgColors[1], "color", context.getPackageName()),
                       getResources().getIdentifier(bgColors[2], "color", context.getPackageName()),
                       getResources().getIdentifier(bgColors[3], "color", context.getPackageName()),
                       getResources().getIdentifier(bgColors[4], "color", context.getPackageName()),
               };

       int[] categoryProgress = new int[] {45, 36, 80, 100, 0};
       String[] categoryName = new String[]
               {
                       "Alpabeto", "Kasarian", "Hugis", "Araw ng Linggo", "Miyembro ng Pamilya"
               };

       for (int i =0; i<5; i++)
       {
           learnCategoryItems.add(new LearnCategoryItem(categoryName[i], colors[i], categoryProgress[i], images[i]));
       }

        LearnRecyclerAdapter adapter = new LearnRecyclerAdapter();
        adapter.setCategoryItems(learnCategoryItems);

        learnRecView.setAdapter(adapter);
        learnRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

    }

    private void InitializeClickListener()
    {
        //Definitions
        ImageView image_favorite = view.findViewById(R.id.image_favorite);
        CardView card_fsl_resource = view.findViewById(R.id.card_fsl_resource);
        TextView home_learn_see_more = view.findViewById(R.id.homeWordSeeMore);
        TextView txt_home_fsl_name = view.findViewById(R.id.txt_home_fsl_name);

        image_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_clicked));
//                image_favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_favorites));
//                image_favorite.setColorFilter(ContextCompat.getColor(context, R.color.white));
//                Toast.makeText(context, txt_home_fsl_name_string + " ay nadagdag na sa iyong paborito!", Toast.LENGTH_SHORT).show();

//                Drawable drawable=image_favorite.getDrawable();
//                Drawable drawable1=context.getDrawable(R.drawable.ic_favorites_outline);
//
//                if(drawable.equals(drawable1)){
//                    Toast.makeText(context, "Taena", Toast.LENGTH_SHORT).show();
//                    image_favorite.setImageResource(R.drawable.ic_menu_favorites);
//                }

//                if (drawable == drawable1)
//                {
//                }else if (drawable.equals(drawable2))
//                {
//                    image_favorite.setImageResource(R.drawable.ic_favorites_outline);
//                    image_favorite.setColorFilter(ContextCompat.getColor(context, R.color.white));
//                    Toast.makeText(context, txt_home_fsl_name.toString() + " ay naalis na sa iyong paborito!", Toast.LENGTH_SHORT).show();
//                }
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

                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(android.R.anim.slide_in_left)
                        .setExitAnim(android.R.anim.slide_out_right)
                        .setPopEnterAnim(android.R.anim.slide_in_left)
                        .setPopExitAnim(android.R.anim.slide_out_right);
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_learn, null, navBuilder.build());
            }
        });
    }
}