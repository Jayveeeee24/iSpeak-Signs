package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.Recognize;
import com.artemis.ispeaksigns.list_adapters.CategoryItem;
import com.artemis.ispeaksigns.list_adapters.LearnRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class nav_home extends Fragment  {

    Context context;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();

        //Definitions
        ImageView image_favorite = view.findViewById(R.id.image_favorite);
        TextView txt_home_fsl_name = view.findViewById(R.id.txt_home_fsl_name);
        String txt_home_fsl_name_string = txt_home_fsl_name.toString();

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

        CardView card_fsl_resource = view.findViewById(R.id.card_fsl_resource);
        card_fsl_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_fsl_wotd);
//                NavOptions.Builder navBuilder = new NavOptions.Builder();
//                navBuilder.setEnterAnim(android.R.anim.slide_in_left)
//                        .setExitAnim(android.R.anim.slide_out_right)
//                        .setPopEnterAnim(android.R.anim.slide_in_left)
//                        .setPopExitAnim(android.R.anim.slide_out_right);
//                NavHostFragment.findNavController(nav_home.this).navigate(R.id.action_nav_home_to_nav_fsl_wotd, null, navBuilder.build());
            }
        });

        RecyclerViewClass();
        return view;
    }

    private void RecyclerViewClass()
    {
        RecyclerView learnRecView = view.findViewById(R.id.learnRecycler);
        ArrayList<CategoryItem> categoryItems = new ArrayList<>();

        String imageUrl = "ic_alpabeto";
        String bgColor = "dark_cyan";
        int image = getResources().getIdentifier(imageUrl, "drawable", context.getPackageName());
        int color = getResources().getIdentifier(bgColor, "color", context.getPackageName());
        categoryItems.add(new CategoryItem("Alpabeto", color, 40, image));
        categoryItems.add(new CategoryItem("Alpabeto", color, 40, image));
        categoryItems.add(new CategoryItem("Alpabeto", color, 40, image));
        categoryItems.add(new CategoryItem("Alpabeto", color, 40, image));
        categoryItems.add(new CategoryItem("Alpabeto", color, 40, image));

        LearnRecyclerAdapter adapter = new LearnRecyclerAdapter();
        adapter.setCategoryItems(categoryItems);

        learnRecView.setAdapter(adapter);
        learnRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

    }

}