package com.artemis.ispeaksigns.home_list_adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>{

    private ArrayList<HomeCategoryItem> homeCategoryItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtCategoryName.setText(homeCategoryItems.get(position).getCategoryName());
        holder.progressCategory.setProgress(homeCategoryItems.get(position).getProgress());
        holder.imageCategory.setImageResource(homeCategoryItems.get(position).getImageUrl());
        holder.imageBg.setBackgroundResource(homeCategoryItems.get(position).getBgColor());

        holder.categoryParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change this to an actual method to do when an item is clicked
                Bundle bundle = new Bundle();
                bundle.putString("category", homeCategoryItems.get(position).getCategoryName());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_learn_category_word, bundle, navBuilder.build());
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCategoryItems.size();
    }

    public void setCategoryItems(ArrayList<HomeCategoryItem> homeCategoryItems) {
        this.homeCategoryItems = homeCategoryItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCategoryName;
        CardView categoryParent;
        RelativeLayout imageBg;
        ProgressBar progressCategory;
        ImageView imageCategory;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            categoryParent = itemView.findViewById(R.id.categoryParent);
            imageBg = itemView.findViewById(R.id.imageBg);
            progressCategory = itemView.findViewById(R.id.progressCategory);
            imageCategory = itemView.findViewById(R.id.imageCategory);
        }
    }
}