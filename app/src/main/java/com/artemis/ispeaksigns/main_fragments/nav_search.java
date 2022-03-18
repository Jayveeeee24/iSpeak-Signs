package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.R;


public class nav_search extends Fragment {

    View view;
    Context context;
    TextView searchCategory;
    TextView searchWord;
    TextView searchPhrase;
    SearchView searchView;
    String search_type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        context = container.getContext();
        searchView = (SearchView) view.findViewById(R.id.search);
        searchCategory = view.findViewById(R.id.search_category);
        searchWord = view.findViewById(R.id.search_word);
        searchPhrase = view.findViewById(R.id.search_phrase);

        searchView.requestFocus();
        search_type = "category";
        InitializeOnClick();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(context, searchView.getQuery() + " haha", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        
        
        return view;
    }

    private void InitializeOnClick()
    {

        searchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCategory.setTextColor(getResources().getColor(R.color.colorSecondary, null));
                searchWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchPhrase.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchCategory.setBackgroundResource(R.drawable.highlight_text);
                searchView.setQueryHint("Humanap ng Kategorya");
                searchWord.setBackgroundResource(android.R.color.transparent);
                searchPhrase.setBackgroundResource(android.R.color.transparent);
                search_type = "category";
            }
        });

        searchWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));
                searchCategory.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchPhrase.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchWord.setBackgroundResource(R.drawable.highlight_text);
                searchView.setQueryHint("Humanap ng FSL na Salita");
                searchCategory.setBackgroundResource(android.R.color.transparent);
                searchPhrase.setBackgroundResource(android.R.color.transparent);
                search_type = "word";
            }
        });

        searchPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPhrase.setTextColor(getResources().getColor(R.color.colorSecondary, null));
                searchWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchCategory.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchPhrase.setBackgroundResource(R.drawable.highlight_text);
                searchView.setQueryHint("Humanap ng FSL na Parirala");
                searchCategory.setBackgroundResource(android.R.color.transparent);
                searchWord.setBackgroundResource(android.R.color.transparent);
                search_type = "phrase";
            }
        });
    }

}