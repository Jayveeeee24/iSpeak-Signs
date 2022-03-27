package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_favorite.FavoriteCategoryItem;
import com.artemis.ispeaksigns.adapter_list_favorite.FavoriteRecyclerAdapter;

import java.util.ArrayList;

public class nav_favorites extends Fragment {

    View view;
    Context context;
    FavoriteRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        InitializeRecycler();
        return view;
    }

    private void InitializeRecycler() {
        RecyclerView favoriteRecView = view.findViewById(R.id.favoriteRecycler);
        ArrayList<FavoriteCategoryItem> favoriteCategoryItems = new ArrayList<>();

        String[] categoryName = new String[]{
                        "Parke", "Magandang Umaga", "Lunes", "Tulong!", "Tuwa"
                };

        String[] categoryType = new String[]{
                        "Salita", "Parirala", "Salita", "Parirala", "Salita"
        };

        for (int i = 0; i < categoryName.length; i++) {
            favoriteCategoryItems.add(new FavoriteCategoryItem(categoryName[i], categoryType[i]));
        }

        adapter = new FavoriteRecyclerAdapter();
        adapter.setFavoriteCategoryItems(favoriteCategoryItems);

        favoriteRecView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (favoriteCategoryItems.isEmpty())
        {
                TextView favoriteNoItem = view.findViewById(R.id.favorite_no_item);
                favoriteNoItem.setVisibility(View.VISIBLE);
                favoriteRecView.setVisibility(View.INVISIBLE);
        }
        favoriteRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


    }

}
