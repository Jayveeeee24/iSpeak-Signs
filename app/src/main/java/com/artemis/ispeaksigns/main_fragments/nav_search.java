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
import android.widget.SearchView;
import android.widget.TextView;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_search.SearchCategoryItem;
import com.artemis.ispeaksigns.adapter_list_search.SearchCategoryListAdapter;
import com.artemis.ispeaksigns.adapter_list_search.SearchVideoItem;
import com.artemis.ispeaksigns.adapter_list_search.SearchVideoListAdapter;
import com.artemis.ispeaksigns.adapter_list_search.SearchWordItem;
import com.artemis.ispeaksigns.adapter_list_search.SearchWordListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class nav_search extends Fragment {

    View view;
    Context context;
    TextView searchCategory;
    TextView searchWord;
    TextView searchPhrase;
    SearchView searchView;
    String search_type;
    RecyclerView searchRecycler;
    DBHelper DB;
    SearchCategoryListAdapter adapter = new SearchCategoryListAdapter();
    SearchWordListAdapter adapter1 = new SearchWordListAdapter();
    SearchVideoListAdapter adapter2 = new SearchVideoListAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = (SearchView) view.findViewById(R.id.search);
        searchCategory = view.findViewById(R.id.search_category);
        searchWord = view.findViewById(R.id.search_word);
        searchPhrase = view.findViewById(R.id.search_phrase);
        searchRecycler = view.findViewById(R.id.search_recycler);
        searchRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        searchRecycler.setNestedScrollingEnabled(false);
        searchView.requestFocus();
        search_type = "Kategorya";
        DB = new DBHelper(context);

        InitializeRecycler();
        InitializeOnClick();


    }

    private void InitializeRecycler()
    {
        ArrayList<SearchCategoryItem> searchCategoryItems = new ArrayList<>();
        ArrayList<SearchVideoItem> searchVideoItems = new ArrayList<>();
        ArrayList<SearchWordItem> searchWordItems = new ArrayList<>();
        if (search_type.equals("Kategorya")){
            Cursor searchCategoryCursor = DB.getCategory("", "Search");
            String[] categoryName = new String[searchCategoryCursor.getCount()];
            String[] categoryType = new String[searchCategoryCursor.getCount()];
            if (searchCategoryCursor.getCount() == 0){
                categoryName = new String[]{
                        "Kasarian", "Pang-Komunikasyon", "Damit", "Pagbati", "Pang-Emergency"
                };

                categoryType = new String[]{
                        "Salita", "Parirala", "Salita", "Parirala", "Parirala"
                };
            }else{
                int i = 0;
                while (searchCategoryCursor.moveToNext()){
                    categoryName[i] = searchCategoryCursor.getString(0);
                    categoryType[i] = searchCategoryCursor.getString(1);
                    i++;
                }
            }

            for (int i = 0; i<categoryName.length; i++) {
                searchCategoryItems.add(new SearchCategoryItem(categoryName[i], categoryType[i]));
            }

            adapter.setSearchCategoryItems(searchCategoryItems);
            searchRecycler.setAdapter(adapter);
        }
        else if (search_type.equals("Salita")) {
            Cursor searchWordCursor = DB.getItem("Salita", "SearchItem");
            String[] itemName = new String[searchWordCursor.getCount()];
            if (searchWordCursor.getCount() == 0){
                itemName = new String[]{
                        "Parke", "Lunes", "Disyembre", "Tuwa", "Aso"
                };
            }else{
                int i = 0;
                while (searchWordCursor.moveToNext()){
                    itemName[i] = searchWordCursor.getString(0);
                    i++;
                }
            }

            for (int i = 0; i<itemName.length; i++) {
                searchWordItems.add(new SearchWordItem(itemName[i]));
            }
            adapter1.setSearchWordItems(searchWordItems);
            searchRecycler.setAdapter(adapter1);
        }else if (search_type.equals("Parirala")){
            Cursor searchPhraseCursor = DB.getItem("Parirala", "SearchItem");
            String[] phraseName = new String[searchPhraseCursor.getCount()];

            if (searchPhraseCursor.getCount() == 0){
                phraseName = new String[]{
                    "Magandang Umaga", "Kamusta ka", "Tulong!", "Anong lugar ito"
                };
            }else{
                int i = 0;
                while (searchPhraseCursor.moveToNext()){
                    phraseName[i] = searchPhraseCursor.getString(0);
                    i++;
                }
            }

            for (int i = 0; i<phraseName.length; i++) {
                searchVideoItems.add(new SearchVideoItem(phraseName[i]));
            }
            adapter2.setSearchVideoItems(searchVideoItems);
            searchRecycler.setAdapter(adapter2);
        }

    }

    private void InitializeOnClick()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (search_type.equals("Kategorya")){
                    adapter.getFilter().filter(query);
                }else if (search_type.equals("Salita")){
                    adapter1.getFilter().filter(query);
                }else if (search_type.equals("Parirala")){
                    adapter2.getFilter().filter(query);
                }
                return false;

            }
        });

        searchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_type = "Kategorya";
                InitializeRecycler();
                searchCategory.setTextColor(getResources().getColor(R.color.colorSecondary, null));
                searchWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchPhrase.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchCategory.setBackgroundResource(R.drawable.highlight_text);
                searchView.setQueryHint(getString(R.string.humanap_ng_kategorya));
                searchWord.setBackgroundResource(android.R.color.transparent);
                searchPhrase.setBackgroundResource(android.R.color.transparent);
                searchView.setQuery("", false);
            }
        });

        searchWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_type = "Salita";
                InitializeRecycler();
                searchWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));
                searchCategory.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchPhrase.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchWord.setBackgroundResource(R.drawable.highlight_text);
                searchView.setQueryHint(getString(R.string.humanap_ng_fsl_na_salita));
                searchCategory.setBackgroundResource(android.R.color.transparent);
                searchPhrase.setBackgroundResource(android.R.color.transparent);
                searchView.setQuery("", false);
            }
        });

        searchPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_type = "Parirala";
                InitializeRecycler();
                searchPhrase.setTextColor(getResources().getColor(R.color.colorSecondary, null));
                searchWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchCategory.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                searchPhrase.setBackgroundResource(R.drawable.highlight_text);
                searchView.setQueryHint(getString(R.string.humanap_ng_fsl_na_parirala));
                searchCategory.setBackgroundResource(android.R.color.transparent);
                searchWord.setBackgroundResource(android.R.color.transparent);
                searchView.setQuery("", false);
            }
        });
    }

}