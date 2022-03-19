package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.R;

public class nav_learn extends Fragment {

    View view;
    TextView categoryWord;
    TextView categoryPhrases;
    String category_selected;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn, container, false);
        context = container.getContext();
        categoryWord = (TextView) view.findViewById(R.id.category_word);
        categoryPhrases = (TextView) view.findViewById(R.id.category_phrases);

        InitializeDesign();
        InitializeOnClick();

        return view;
    }

    private void InitializeDesign()
    {

        categoryWord.setTypeface(Typeface.DEFAULT_BOLD);
        categoryPhrases.setTypeface(Typeface.DEFAULT);
        category_selected = "";
        if (getArguments() != null) {
            category_selected = getArguments().getString("category_type");
        }
        if (category_selected.equals("word")){
            categoryWord.setTypeface(Typeface.DEFAULT_BOLD);
            categoryWord.setBackgroundResource(R.drawable.highlight_text);
            categoryWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));

            categoryPhrases.setTypeface(Typeface.DEFAULT);
            categoryPhrases.setBackgroundResource(android.R.color.transparent);
            categoryPhrases.setTextColor(getResources().getColor(R.color.gray_text_color, null));
        }else if (category_selected.equals("phrase")){
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
                categoryWord.setTypeface(Typeface.DEFAULT_BOLD);
                categoryWord.setBackgroundResource(R.drawable.highlight_text);
                categoryWord.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                categoryPhrases.setTypeface(Typeface.DEFAULT);
                categoryPhrases.setBackgroundResource(android.R.color.transparent);
                categoryPhrases.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                category_selected = "word";
                Toast.makeText(context, category_selected + " ang iyong napili", Toast.LENGTH_SHORT).show();
            }
        });

        categoryPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryPhrases.setTypeface(Typeface.DEFAULT_BOLD);
                categoryPhrases.setBackgroundResource(R.drawable.highlight_text);
                categoryPhrases.setTextColor(getResources().getColor(R.color.colorSecondary, null));

                categoryWord.setTypeface(Typeface.DEFAULT);
                categoryWord.setBackgroundResource(android.R.color.transparent);
                categoryWord.setTextColor(getResources().getColor(R.color.gray_text_color, null));
                category_selected = "phrase";
                Toast.makeText(context, category_selected + " ang iyong napili", Toast.LENGTH_SHORT).show();
            }
        });
    }
}