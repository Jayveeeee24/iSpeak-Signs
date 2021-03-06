package com.artemis.ispeaksigns.adapter_list_profile;

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

public class ProfileProgressListAdapter extends RecyclerView.Adapter<ProfileProgressListAdapter.ViewHolder> {

    private ArrayList<ProfileProgressItem> profileProgressItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_profile, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.progressItemParent.setBackgroundResource(profileProgressItems.get(position).getImageBg());
        holder.profileProgressItemImage.setImageResource(profileProgressItems.get(position).getImageResource());
        holder.profileProgressItemName.setText(profileProgressItems.get(position).getItemName());
        holder.profileProgressItemPercent.setText(profileProgressItems.get(position).getItemPercent());
        holder.profileItemProgress.setProgress(profileProgressItems.get(position).getItemProgress());

        holder.cardProfileProgressParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileProgressItems.get(position).getItemType().equals("Salita")){
                    Bundle bundle = new Bundle();
                    bundle.putString("Kategorya", profileProgressItems.get(position).getItemName());
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                            .setExitAnim(R.anim.nav_default_exit_anim)
                            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                            .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                    Navigation.findNavController(view).navigate(R.id.learn_category_word, bundle, navBuilder.build());
                }else if (profileProgressItems.get(position).getItemType().equals("Parirala")){
                    Bundle bundle = new Bundle();
                    bundle.putString("Kategorya", profileProgressItems.get(position).getItemName());
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                            .setExitAnim(R.anim.nav_default_exit_anim)
                            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                            .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                    Navigation.findNavController(view).navigate(R.id.learn_category_video, bundle, navBuilder.build());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileProgressItems.size();
    }

    public void setProfileProgressItems(ArrayList<ProfileProgressItem> profileProgressItems) {
        this.profileProgressItems = profileProgressItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardProfileProgressParent;
        private RelativeLayout progressItemParent;
        private ImageView profileProgressItemImage;
        private TextView profileProgressItemName;
        private TextView profileProgressItemPercent;
        private ProgressBar profileItemProgress;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cardProfileProgressParent = itemView.findViewById(R.id.cardProfileProgressParent);
            progressItemParent = itemView.findViewById(R.id.progress_item_parent);
            profileProgressItemImage = itemView.findViewById(R.id.profile_progress_item_image);
            profileProgressItemName = itemView.findViewById(R.id.profile_progress_item_name);
            profileProgressItemPercent = itemView.findViewById(R.id.profile_progress_item_percent);
            profileItemProgress = itemView.findViewById(R.id.profile_item_progress);

        }
    }
}
