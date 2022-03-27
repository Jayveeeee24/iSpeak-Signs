package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_learn_word.LearnListWordCategoryItem;
import com.artemis.ispeaksigns.adapter_list_learn_word.LearnListWordRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class learn_word_list extends Fragment {

    private Context context;
    private View view;
    RecyclerView learnListRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_word_list, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        learnListRecycler = view.findViewById(R.id.learn_list_recycler);
        learnListRecycler.setNestedScrollingEnabled(false);

        InitializeRecycler();
        setProgressSetup();
    }

    private void InitializeRecycler(){
        ArrayList<LearnListWordCategoryItem> learnListWordCategoryItems = new ArrayList<>();

        String[] itemName = new String[]{
            "Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo"
        };
        int[] isLearned = new int[]{
                1, 0, 0, 1, 0, 0, 0
        };

        for (int i = 0; i<itemName.length; i++) {
            learnListWordCategoryItems.add(new LearnListWordCategoryItem(itemName[i], isLearned[i]));
        }

        LearnListWordRecyclerAdapter adapter = new LearnListWordRecyclerAdapter();
        adapter.setLearnListWordCategoryItems(learnListWordCategoryItems);

        learnListRecycler.setAdapter(adapter);
        learnListRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    public void setProgressSetup()
    {
        String kategorya = "";
        if (getArguments() != null) {
            kategorya = getArguments().getString("Kategorya");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(kategorya);
        }

        FunctionHelper functionHelper = new FunctionHelper();
//        String str = kategorya.toLowerCase();
//        String[] arrStr = str.split(" ");
//        String imageName = "ic";
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String a : arrStr)
//        {
//            if (arrStr.length == 1) {
//                str = "_" + str;
//                break;
//            }
//            str = stringBuilder.append("_").append(a).toString();
//        }
//        imageName =imageName + str ;

        ImageView categoryImage = view.findViewById(R.id.categoryImage);
        categoryImage.setImageResource(getResources().getIdentifier(functionHelper.getImageLogo(kategorya), "drawable", context.getPackageName()));

    }
}