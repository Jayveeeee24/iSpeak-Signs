package com.artemis.ispeaksigns.adapter_list_favorite;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    private ArrayList<FavoriteCategoryItem> favoriteCategoryItems = new ArrayList<>();

    Context context;
    FunctionHelper functionHelper;
    TextView favoriteNoItem;

    public FavoriteRecyclerAdapter(Context context, TextView favoriteNoItem){
        this.context = context;
        functionHelper = new FunctionHelper();
        this.favoriteNoItem = favoriteNoItem;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtFavoriteItem.setText(favoriteCategoryItems.get(position).getItemName());

        holder.cardFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoriteCategoryItems.get(position).getItemType().equals("Salita")){
                    Bundle bundle = new Bundle();
                    bundle.putString("learn_word_item", favoriteCategoryItems.get(position).getItemName());
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                            .setExitAnim(R.anim.nav_default_exit_anim)
                            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                            .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                    Navigation.findNavController(view).navigate(R.id.action_nav_favorites_to_learn_word_item, bundle, navBuilder.build());
                }else if (favoriteCategoryItems.get(position).getItemType().equals("Parirala")){
                    Bundle bundle = new Bundle();
                    bundle.putString("learn_video_item", favoriteCategoryItems.get(position).getItemName());
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                            .setExitAnim(R.anim.nav_default_exit_anim)
                            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                            .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                    Navigation.findNavController(view).navigate(R.id.action_nav_favorites_to_learn_video_item, bundle, navBuilder.build());
                }
            }
        });
        holder.favoriteRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteCategoryItems.remove(holder.getAbsoluteAdapterPosition());
                String name = holder.txtFavoriteItem.getText().toString();
                notifyDataSetChanged();
                functionHelper.updateFavorite(context, name, null, "Remove");

                if (getItemCount() == 0){
                    favoriteNoItem.setVisibility(View.VISIBLE);
                }else{
                    favoriteNoItem.setVisibility(View.INVISIBLE);
                }
                Log.i("REMOVE FAVORITE", Integer.toString(holder.getAbsoluteAdapterPosition()));
                Log.i("REMOVE FAVORITE SIZE", Integer.toString(favoriteCategoryItems.size()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteCategoryItems.size();
    }

    public void setFavoriteCategoryItems(ArrayList<FavoriteCategoryItem> favoriteCategoryItems) {
        this.favoriteCategoryItems = favoriteCategoryItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtFavoriteItem;
        CardView cardFavorite;
        ImageView favoriteRemove;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cardFavorite = itemView.findViewById(R.id.card_favorite_item);
            txtFavoriteItem = itemView.findViewById(R.id.txt_favorite_item);
            favoriteRemove = itemView.findViewById(R.id.favorite_remove);
        }
    }
}
