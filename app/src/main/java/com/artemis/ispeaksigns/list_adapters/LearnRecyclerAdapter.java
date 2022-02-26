package com.artemis.ispeaksigns.list_adapters;

import android.content.Context;
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
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LearnRecyclerAdapter extends RecyclerView.Adapter<LearnRecyclerAdapter.ViewHolder>{

    private ArrayList<CategoryItem> categoryItems = new ArrayList<>();
    public LearnRecyclerAdapter() {
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
    holder.txtCategoryName.setText(categoryItems.get(position).getCategoryName());
    holder.categoryParent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext()
                    , categoryItems.get(position).getCategoryName() + " has been selected"
                    , Toast.LENGTH_SHORT).show();
        }
    });
    holder.progressCategory.setProgress(categoryItems.get(position).getProgress());
    holder.imageCategory.setImageResource(categoryItems.get(position).getImageUrl());
    holder.imageBg.setBackgroundResource(categoryItems.get(position).getBgColor());


    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public void setCategoryItems(ArrayList<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
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
