package com.artemis.ispeaksigns.adapter_list_learn_word;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LearnListWordRecyclerAdapter extends RecyclerView.Adapter<LearnListWordRecyclerAdapter.ViewHolder>{

    ArrayList<LearnListWordCategoryItem> learnListWordCategoryItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_word_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
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
                //TODO CHANGE THIS INTO AN ACTUAL COMMAND TO AN ITEM LIST
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
