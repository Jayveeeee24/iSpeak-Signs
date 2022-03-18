package com.artemis.ispeaksigns.favorite_list_adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.main_fragments.nav_favorites;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    private ArrayList<FavoriteCategoryItem> favoriteCategoryItems = new ArrayList<>();


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtFavoriteItem.setText(favoriteCategoryItems.get(position).getCategoryName());

        holder.cardFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO make this an actual click listener into the word or phrase item
            }
        });
        holder.favoriteRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteCategoryItems.remove(holder.getAdapterPosition());
                String name = holder.txtFavoriteItem.getText().toString();
                Toast.makeText(view.getContext(), name + " ayy nabura mo na gago ang hirap bago ko nagawa ampota", Toast.LENGTH_SHORT).show();
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), favoriteCategoryItems.size());


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
