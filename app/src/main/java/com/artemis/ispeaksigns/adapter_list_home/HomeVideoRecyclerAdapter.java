package com.artemis.ispeaksigns.adapter_list_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

public class HomeVideoRecyclerAdapter extends RecyclerView.Adapter<HomeVideoRecyclerAdapter.ViewHolder>{

    private ArrayList<HomeVideoCategoryItem> homeVideoCategoryItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_category_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.itemName.setText(homeVideoCategoryItems.get(position).getItemName());
        holder.itemImage.setImageResource(homeVideoCategoryItems.get(position).getItemImage());
        holder.cardParent.setBackgroundResource(homeVideoCategoryItems.get(position).getBgColor());
        holder.videoCategoryParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Kategorya", homeVideoCategoryItems.get(position).getItemName());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                try {
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_learn_category_video, bundle, navBuilder.build());
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeVideoCategoryItems.size();
    }

    public void setHomeVideoCategoryItems(ArrayList<HomeVideoCategoryItem> homeVideoCategoryItems) {
        this.homeVideoCategoryItems = homeVideoCategoryItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;
        RelativeLayout cardParent;
        CardView videoCategoryParent;
        ImageView itemImage;
        public ViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.video_item_name);
            itemImage = itemView.findViewById(R.id.video_item_image);
            cardParent = itemView.findViewById(R.id.cardParent);
            videoCategoryParent = itemView.findViewById(R.id.videoCategoryParent);
        }
    }
}
