package com.artemis.ispeaksigns.adapter_list_learn_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LearnListWordRecyclerAdapter extends RecyclerView.Adapter<LearnListWordRecyclerAdapter.ViewHolder>{

    ArrayList<LearnListWordCategoryItem> learnListWordCategoryItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_word_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.learnListWordName.setText(learnListWordCategoryItems.get(position).getItemName());
        if (learnListWordCategoryItems.get(position).getIsLearned() == 1){
            holder.isLearned.setVisibility(View.VISIBLE);
        }else{
            holder.isLearned.setVisibility(View.INVISIBLE);
        }

        holder.cardlearnListParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("learn_word_item", learnListWordCategoryItems.get(position).getItemName());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                try {
                    Navigation.findNavController(view).navigate(R.id.action_learn_category_word_to_learn_word_item, bundle, navBuilder.build());
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return learnListWordCategoryItems.size();
    }

    public void setLearnListWordCategoryItems(ArrayList<LearnListWordCategoryItem> learnListWordCategoryItems) {
        this.learnListWordCategoryItems = learnListWordCategoryItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardlearnListParent;
        TextView learnListWordName;
        ImageView isLearned;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cardlearnListParent = itemView.findViewById(R.id.card_learn_list_parent);
            learnListWordName = itemView.findViewById(R.id.learn_list_word_name);
            isLearned = itemView.findViewById(R.id.is_Learned);
        }
    }
}
