package com.artemis.ispeaksigns.adapter_list_learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LearnVideoRecyclerAdapter extends RecyclerView.Adapter<LearnVideoRecyclerAdapter.ViewHolder>{

    private ArrayList<LearnVideoCategoryItem> learnVideoCategoryItems = new ArrayList<>();
    private View view;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_learn, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.learnItemParent.setBackgroundResource(learnVideoCategoryItems.get(position).getLearnItemParentBg());
        holder.gotoParent.setBackgroundResource(learnVideoCategoryItems.get(position).getGotoParentBg());
        holder.categoryName.setText(learnVideoCategoryItems.get(position).getCategoryName());
        holder.categoryType.setText(learnVideoCategoryItems.get(position).getCategoryType());
        holder.learnItemCount.setText(learnVideoCategoryItems.get(position).getItemCount());
        holder.learnItemCount.setTextColor(view.getResources().getColor(learnVideoCategoryItems.get(position).getLearnItemParentBg(), null));

        holder.learnCardItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("video_list", learnVideoCategoryItems.get(position).getCategoryName());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                Navigation.findNavController(view).navigate(R.id.action_nav_learn_to_learn_category_video, bundle, navBuilder.build());

            }
        });
    }

    @Override
    public int getItemCount() {
        return learnVideoCategoryItems.size();
    }

    public void setLearnVideoCategoryItems(ArrayList<LearnVideoCategoryItem> learnVideoCategoryItems) {
        this.learnVideoCategoryItems = learnVideoCategoryItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout learnItemParent;
        private TextView categoryName;
        private TextView categoryType;
        private TextView learnItemCount;
        private RelativeLayout gotoParent;
        private CardView learnCardItemParent;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            learnItemParent = itemView.findViewById(R.id.learn_item_parent);
            categoryName = itemView.findViewById(R.id.learn_category_name);
            categoryType = itemView.findViewById(R.id.learn_category_type);
            learnItemCount = itemView.findViewById(R.id.learnItemCount);
            gotoParent = itemView.findViewById(R.id.go_to_parent);
            learnCardItemParent = itemView.findViewById(R.id.card_learn_item_parent);
        }
    }
}
