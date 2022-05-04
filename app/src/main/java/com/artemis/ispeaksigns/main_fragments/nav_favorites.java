package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_favorite.FavoriteCategoryItem;
import com.artemis.ispeaksigns.adapter_list_favorite.FavoriteRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class nav_favorites extends Fragment {

    View view;
    Context context;
    FavoriteRecyclerAdapter adapter;
    DBHelper DB;
    ArrayList<FavoriteCategoryItem> favoriteCategoryItems;
    RecyclerView favoriteRecView;
    TextView favoriteNoItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DB = new DBHelper(context);

        favoriteNoItem = view.findViewById(R.id.favorite_no_item);
        InitializeRecycler();
    }

    @Override
    public void onStart() {
        super.onStart();

        InitializeRecycler();
    }

    private void InitializeRecycler() {
        favoriteRecView = (RecyclerView) view.findViewById(R.id.favoriteRecycler);
        favoriteCategoryItems = new ArrayList<>();
        Cursor getAllFavoritesCursor = DB.getItem("", "Favorite");

        String[] itemName = new String[getAllFavoritesCursor.getCount()];
        String[] itemType = new String[getAllFavoritesCursor.getCount()];

        if (getAllFavoritesCursor.getCount() != 0){
            int j = 0;
            while (getAllFavoritesCursor.moveToNext()){
                itemName[j] = getAllFavoritesCursor.getString(0);
                itemType[j] = getAllFavoritesCursor.getString(1);
                j++;
            }

            for (int i = 0; i < itemName.length; i++) {
                favoriteCategoryItems.add(new FavoriteCategoryItem(itemName[i], itemType[i]));
            }

            adapter = new FavoriteRecyclerAdapter(context, favoriteNoItem);
            adapter.setFavoriteCategoryItems(favoriteCategoryItems);
            favoriteRecView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            favoriteRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        }

        if (favoriteCategoryItems.isEmpty())
        {
            TextView favoriteNoItem = view.findViewById(R.id.favorite_no_item);
            favoriteNoItem.setVisibility(View.VISIBLE);
            favoriteRecView.setVisibility(View.INVISIBLE);
        }
    }

}
