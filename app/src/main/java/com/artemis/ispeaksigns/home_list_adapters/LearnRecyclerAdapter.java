package com.artemis.ispeaksigns.home_list_adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.main_fragments.nav_home;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LearnRecyclerAdapter extends RecyclerView.Adapter<LearnRecyclerAdapter.ViewHolder>{

    private ArrayList<LearnCategoryItem> learnCategoryItems = new ArrayList<>();
    public LearnRecyclerAdapter() {
    }

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
        holder.txtCategoryName.setText(learnCategoryItems.get(position).getCategoryName());
        holder.progressCategory.setProgress(learnCategoryItems.get(position).getProgress());
        holder.imageCategory.setImageResource(learnCategoryItems.get(position).getImageUrl());
        holder.imageBg.setBackgroundResource(learnCategoryItems.get(position).getBgColor());

        holder.categoryParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change this to an actual method to do  when an item is clicked
                Bundle bundle = new Bundle();
                bundle.putString("category", learnCategoryItems.get(position).getCategoryName());
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
        return learnCategoryItems.size();
    }

    public void setCategoryItems(ArrayList<LearnCategoryItem> learnCategoryItems) {
        this.learnCategoryItems = learnCategoryItems;
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
