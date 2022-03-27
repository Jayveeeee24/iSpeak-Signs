
package com.artemis.ispeaksigns.main_fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_learn.LearnWordRecyclerAdapter;
import com.artemis.ispeaksigns.adapter_list_learn.LearnVideoCategoryItem;
import com.artemis.ispeaksigns.adapter_list_learn.LearnVideoRecyclerAdapter;
import com.artemis.ispeaksigns.adapter_list_learn.LearnWordCategoryItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class nav_learn extends Fragment {

    View view;
    TextView categoryWord;
    TextView categoryPhrases;
    String category_selected;
    Context context;
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
        InitializeOnClick();
        InitializeDesign();
    }

    private void InitializeLearnRecyclerView(String categoryType)
    {
        if (categoryType.equals("Salita")) {
            String[] bgColors = new String[]
                    {"tulip", "silver_lake_blue", "outrageous_orange", "apple", "plump_purple"};

            String[] gotoBgColors = new String[bgColors.length];
            for (int i = 0; i < bgColors.length; i++) {
                gotoBgColors[i] = bgColors[i] + "_90";
            }

            int[] bgColorsConverted = new int[bgColors.length];
            for (int i = 0; i < bgColors.length; i++) {
                bgColorsConverted[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
            }

            int[] gotoBgColorsConverted = new int[gotoBgColors.length];
            for (int i = 0; i < bgColors.length; i++) {
                gotoBgColorsConverted[i] = getResources().getIdentifier(gotoBgColors[i], "color", context.getPackageName());
            }

            int[] itemCount = new int[]{12, 7, 11, 9, 5};
            String[] categoryName = new String[]
                    {
                            "Alpabeto", "Kasarian", "Parte ng Katawan", "Araw ng Linggo", "Miyembro ng Pamilya"
                    };

            ArrayList<LearnWordCategoryItem> learnWordCategoryItems = new ArrayList<>();
            for (int i = 0; i < categoryName.length; i++) {
                learnWordCategoryItems.add(new LearnWordCategoryItem(categoryName[i], categoryType,
                        itemCount[i] + " na " + categoryType.toLowerCase(),
                        bgColorsConverted[i], gotoBgColorsConverted[i]));
            }

            LearnWordRecyclerAdapter adapter = new LearnWordRecyclerAdapter();
            adapter.setLearnWordCategoryItems(learnWordCategoryItems);

            learnRecycler.setAdapter(adapter);
        }else if (categoryType.equals("Parirala"))
        {
            String[] bgColors = new String[]
                    {"bright_navy_blue", "plump_purple", "flame"};

            String[] gotoBgColors = new String[bgColors.length];
            for (int i = 0; i < bgColors.length; i++) {
                gotoBgColors[i] = bgColors[i] + "_90";
            }

            int[] bgColorsConverted = new int[bgColors.length];
            for (int i = 0; i < bgColors.length; i++) {
                bgColorsConverted[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
            }

            int[] gotoBgColorsConverted = new int[gotoBgColors.length];
            for (int i = 0; i < bgColors.length; i++) {
                gotoBgColorsConverted[i] = getResources().getIdentifier(gotoBgColors[i], "color", context.getPackageName());
            }

            int[] itemCount = new int[]{4, 13, 8};
            String[] categoryName = new String[]
                    {
                            "Pagbati", "Pang-Emergency", "Pang-Komunikasyon"
                    };

            ArrayList<LearnVideoCategoryItem> learnVideoCategoryItems = new ArrayList<>();
            for (int i = 0; i < categoryName.length; i++) {
                learnVideoCategoryItems.add(new LearnVideoCategoryItem(categoryName[i], categoryType,
                        itemCount[i] + " na " + categoryType.toLowerCase(),
                        bgColorsConverted[i], gotoBgColorsConverted[i]));
            }

            LearnVideoRecyclerAdapter adapter = new LearnVideoRecyclerAdapter();
            adapter.setLearnVideoCategoryItems(learnVideoCategoryItems);

            learnRecycler.setAdapter(adapter);
        }
        learnRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

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

    private void InitializeOnClick()
    {
        categoryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_selected = "Salita";
                InitializeLearnRecyclerView(category_selected);
                categoryWord.setTypeface(Typeface.DEFAULT_BOLD);
                categoryWord.setBackgroundResource(R.drawable.highlight_text);
                categoryWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                categoryPhrases.setTypeface(Typeface.DEFAULT);
                categoryPhrases.setBackgroundResource(android.R.color.transparent);
                categoryPhrases.setTextColor(getResources().getColor(R.color.gray_text_color, null));
            }
        });

        categoryPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_selected = "Parirala";
                InitializeLearnRecyclerView(category_selected);
                categoryPhrases.setTypeface(Typeface.DEFAULT_BOLD);
                categoryPhrases.setBackgroundResource(R.drawable.highlight_text);
                categoryPhrases.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                categoryWord.setTypeface(Typeface.DEFAULT);
                categoryWord.setBackgroundResource(android.R.color.transparent);
                categoryWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
            }
        });
    }
}