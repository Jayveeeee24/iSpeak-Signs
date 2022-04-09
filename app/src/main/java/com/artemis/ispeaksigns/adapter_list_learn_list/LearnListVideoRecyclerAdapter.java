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

public class LearnListVideoRecyclerAdapter extends RecyclerView.Adapter<LearnListVideoRecyclerAdapter.ViewHolder> {

    ArrayList<LearnListVideoCategoryItem> learnListVideoCategoryItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_video_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.learnListVideoName.setText(learnListVideoCategoryItems.get(position).getItemName());
        if (learnListVideoCategoryItems.get(position).getIsLearned() == 1){
            holder.isVideoLearned.setVisibility(View.VISIBLE);
        }else{
            holder.isVideoLearned.setVisibility(View.INVISIBLE);
        }

        holder.cardLearnListVideoParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("learn_video_item", learnListVideoCategoryItems.get(position).getItemName());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                try {
                    Navigation.findNavController(view).navigate(R.id.action_learn_category_video_to_learn_video_item, bundle, navBuilder.build());
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return learnListVideoCategoryItems.size();
    }

    public void setLearnListVideoCategoryItems(ArrayList<LearnListVideoCategoryItem> learnListVideoCategoryItems) {
        this.learnListVideoCategoryItems = learnListVideoCategoryItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardLearnListVideoParent;
        TextView learnListVideoName;
        ImageView isVideoLearned;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cardLearnListVideoParent = itemView.findViewById(R.id.card_learn_list_video_parent);
            learnListVideoName = itemView.findViewById(R.id.learn_list_video_name);
            isVideoLearned = itemView.findViewById(R.id.is_videoLearned);
        }
    }
}
