package com.artemis.ispeaksigns.adapter_list_profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProfileSeeMoreListAdapter extends RecyclerView.Adapter<ProfileSeeMoreListAdapter.ViewHolder> {

    ArrayList<ProfileSeeMoreItem> profileSeeMoreItems = new ArrayList<>();
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
        holder.progressItemParent.setBackgroundResource(profileSeeMoreItems.get(position).getImageBg());
        holder.profileProgressItemImage.setImageResource(profileSeeMoreItems.get(position).getImageResource());
        holder.profileProgressItemName.setText(profileSeeMoreItems.get(position).getItemName());
        holder.profileProgressItemPercent.setText(profileSeeMoreItems.get(position).getItemPercent());
        holder.profileItemProgress.setProgress(profileSeeMoreItems.get(position).getItemProgress());

        holder.cardProfileProgressParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO make this an actual command to go to the item
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileSeeMoreItems.size();
    }

    public void setProfileSeeMoreItems(ArrayList<ProfileSeeMoreItem> profileSeeMoreItems) {
        this.profileSeeMoreItems = profileSeeMoreItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardProfileProgressParent;
        RelativeLayout progressItemParent;
        ImageView profileProgressItemImage;
        TextView profileProgressItemName;
        TextView profileProgressItemPercent;
        ProgressBar profileItemProgress;
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
