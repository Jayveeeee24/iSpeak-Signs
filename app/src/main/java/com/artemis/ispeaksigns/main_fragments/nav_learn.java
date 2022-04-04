
package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_learn.LearnVideoCategoryItem;
import com.artemis.ispeaksigns.adapter_list_learn.LearnVideoRecyclerAdapter;
import com.artemis.ispeaksigns.adapter_list_learn.LearnWordRecyclerAdapter;
import com.artemis.ispeaksigns.adapter_list_learn.LearnWordCategoryItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class nav_learn extends Fragment {

    View view;
    TextView categoryWord;
    TextView categoryPhrases;
    String category_selected;
    Context context;
    DBHelper DB;
    private RecyclerView learnRecycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryWord = view.findViewById(R.id.category_word);
        categoryPhrases = view.findViewById(R.id.category_phrases);
        learnRecycler = view.findViewById(R.id.learnWordRecycler);
        DB = new DBHelper(context);
        InitializeOnClick();
        InitializeDesign();
    }

    private void InitializeDesign()
    {
        category_selected = "Salita";
        if (getArguments() != null) {
            category_selected = getArguments().getString("category_type");
        }
        categoryWord.setTypeface(Typeface.DEFAULT_BOLD);
        categoryWord.setBackgroundResource(R.drawable.highlight_text);
        categoryWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));

        categoryPhrases.setTypeface(Typeface.DEFAULT);
        categoryPhrases.setBackgroundResource(android.R.color.transparent);
        categoryPhrases.setTextColor(getResources().getColor(R.color.gray_text_color, null));
        InitializeLearnRecyclerView(category_selected);
        if (category_selected.equals("Parirala")){
            categoryPhrases.setTypeface(Typeface.DEFAULT_BOLD);
            categoryPhrases.setBackgroundResource(R.drawable.highlight_text);
            categoryPhrases.setTextColor(getResources().getColor(R.color.colorSecondary, null));

            categoryWord.setTypeface(Typeface.DEFAULT);
            categoryWord.setBackgroundResource(android.R.color.transparent);
            categoryWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
        }
    }

    private void InitializeLearnRecyclerView(String categoryType)
    {
        Log.d("myTag", categoryType);
        Cursor learnCategoryCursor = DB.getAllCategory(categoryType, "Learn");
        String[] categoryName = new String[learnCategoryCursor.getCount()];
        String[] bgColors = new String[categoryName.length];
        String[] itemCount = new String[categoryName.length];
        String[] gotoBgColors = new String[bgColors.length];
        int[] bgColorsConverted = new int[bgColors.length];
        int[] gotoBgColorsConverted = new int[gotoBgColors.length];
        String categorytype = "";

        if (learnCategoryCursor.getCount() == 0){
            Toast.makeText(context, "Ang database ay walang laman, Pakiulit na lamang", Toast.LENGTH_SHORT).show();
            return;
        }else{
            int i = 0;
            while(learnCategoryCursor.moveToNext()){
                categoryName[i] = learnCategoryCursor.getString(0);
                bgColors[i] = learnCategoryCursor.getString(1);
                itemCount[i] = learnCategoryCursor.getString(2);
                categorytype = learnCategoryCursor.getString(3);
                i++;
            }
        }
        for (int i = 0; i < bgColors.length; i++) {
            gotoBgColors[i] = bgColors[i] + "_90";
            bgColorsConverted[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
            gotoBgColorsConverted[i] = getResources().getIdentifier(gotoBgColors[i], "color", context.getPackageName());
        }

        if (categoryType.equals("Salita")) {
            ArrayList<LearnWordCategoryItem> learnWordCategoryItems = new ArrayList<>();
            LearnWordRecyclerAdapter adapter = new LearnWordRecyclerAdapter();
            adapter.setLearnWordCategoryItems(learnWordCategoryItems);
            learnRecycler.setAdapter(adapter);

            for (int i = 0; i < categoryName.length; i++) {
                learnWordCategoryItems.add(new LearnWordCategoryItem(categoryName[i], categorytype,
                        itemCount[i] + " na " + categorytype.toLowerCase(),
                        bgColorsConverted[i], gotoBgColorsConverted[i]));
            }
        }
        else if (categoryType.equals("Parirala"))
        {
            ArrayList<LearnVideoCategoryItem> learnVideoCategoryItems = new ArrayList<>();
            LearnVideoRecyclerAdapter adapter = new LearnVideoRecyclerAdapter();
            adapter.setLearnVideoCategoryItems(learnVideoCategoryItems);
            learnRecycler.setAdapter(adapter);

            for (int i = 0; i < categoryName.length; i++) {
                learnVideoCategoryItems.add(new LearnVideoCategoryItem(categoryName[i], categorytype,
                        itemCount[i] + " na " + categorytype.toLowerCase(),
                        bgColorsConverted[i], gotoBgColorsConverted[i]));
            }
        }

    }

    private void InitializeOnClick()
    {
        categoryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_selected = "Salita";
                categoryWord.setTypeface(Typeface.DEFAULT_BOLD);
                categoryWord.setBackgroundResource(R.drawable.highlight_text);
                categoryWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                categoryPhrases.setTypeface(Typeface.DEFAULT);
                categoryPhrases.setBackgroundResource(android.R.color.transparent);
                categoryPhrases.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                InitializeLearnRecyclerView(category_selected);
            }
        });

        categoryPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_selected = "Parirala";
                categoryPhrases.setTypeface(Typeface.DEFAULT_BOLD);
                categoryPhrases.setBackgroundResource(R.drawable.highlight_text);
                categoryPhrases.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                categoryWord.setTypeface(Typeface.DEFAULT);
                categoryWord.setBackgroundResource(android.R.color.transparent);
                categoryWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                InitializeLearnRecyclerView(category_selected);
            }
        });
    }
}