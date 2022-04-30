package com.artemis.ispeaksigns.sub_fragments;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_learn_list.LearnListWordCategoryItem;
import com.artemis.ispeaksigns.adapter_list_learn_list.LearnListWordRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class learn_word_list extends Fragment {

    private Context context;
    private View view;
    DBHelper DB;
    FunctionHelper functionHelper;

    ImageView categoryImage;
    TextView categoryProgressLabel;
    TextView categoryProgressText;
    ProgressBar categoryProgressBar;
    RecyclerView learnListRecycler;
    RelativeLayout categoryImageParent;

    String kategorya = "";

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
        categoryImage = view.findViewById(R.id.categoryImage);
        categoryProgressLabel = view.findViewById(R.id.txtProgressCardLabel);
        categoryProgressText = view.findViewById(R.id.txtProgressPercent);
        categoryProgressBar = view.findViewById(R.id.progressHorizontalLearn);
        categoryImageParent = view.findViewById(R.id.categoryImageParent);

        DB = new DBHelper(context);
        functionHelper = new FunctionHelper();

        setProgressSetup();
        InitializeRecycler();
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        int progressBar = 0;
        int tempProgress = 0;
        int totalItems = 0;

        Cursor learnWordListCursor = DB.getCategory(kategorya, "SingleCategory");

        if (learnWordListCursor.getCount() == 0){
            Toast.makeText(context, "No database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (learnWordListCursor.moveToNext()){
                totalItems = learnWordListCursor.getInt(1);
                tempProgress = learnWordListCursor.getInt(3);
                progressBar = tempProgress * 100/totalItems;
            }
        }

        categoryProgressText.setText(getResources().getString(R.string.learn_list_progress, Integer.toString(tempProgress), Integer.toString(totalItems)));
        categoryProgressLabel.setText(functionHelper.getCategoryProgressDescription(progressBar, getActivity()));
        categoryProgressBar.setProgress(progressBar);
    }

    public void setProgressSetup()
    {
        int progressBar = 0;
        int tempProgress = 0;
        int totalItems = 0;
        String tempImageName;
        int imageName = 0;
        String tempColor;
        int color = 0;

        if (getArguments() != null) {
            kategorya = getArguments().getString("Kategorya");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(kategorya);
        }

        Cursor learnWordListCursor = DB.getCategory(kategorya, "SingleCategory");

        if (learnWordListCursor.getCount() == 0){
            Toast.makeText(context, "No database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (learnWordListCursor.moveToNext()){
                tempColor = learnWordListCursor.getString(0);
                totalItems = learnWordListCursor.getInt(1);
                tempImageName = learnWordListCursor.getString(2);
                tempProgress = learnWordListCursor.getInt(3);

                color = getResources().getIdentifier(tempColor, "color", context.getPackageName());
                imageName = getResources().getIdentifier(tempImageName, "drawable", context.getPackageName());
                progressBar = tempProgress * 100/totalItems;
            }
        }
        categoryImage.setImageResource(imageName);
        categoryImageParent.setBackgroundResource(color);
        categoryProgressText.setText(getResources().getString(R.string.learn_list_progress, Integer.toString(tempProgress), Integer.toString(totalItems)));
        categoryProgressLabel.setText(functionHelper.getCategoryProgressDescription(progressBar, getActivity()));
        categoryProgressBar.setProgress(progressBar);

    }

    private void InitializeRecycler(){
        ArrayList<LearnListWordCategoryItem> learnListWordCategoryItems = new ArrayList<>();
        Cursor wordListCursor = DB.getItem(kategorya, "ItemList");
        String[] itemName = new String[wordListCursor.getCount()];
        int[] isLearned = new int[wordListCursor.getCount()];

        if (wordListCursor.getCount() == 0){
            itemName = new String[]{
                    "Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo"
            };

            isLearned = new int[]{
                    1, 0, 0, 1, 0, 0, 0
            };
        }else{
            int i = 0;
            while (wordListCursor.moveToNext()){
                itemName[i] = wordListCursor.getString(0);
                isLearned[i] = wordListCursor.getInt(1);
                i++;
            }
        }

        for (int i = 0; i<itemName.length; i++) {
            learnListWordCategoryItems.add(new LearnListWordCategoryItem(itemName[i], isLearned[i]));
        }

        LearnListWordRecyclerAdapter adapter = new LearnListWordRecyclerAdapter();
        adapter.setLearnListWordCategoryItems(learnListWordCategoryItems);

        learnListRecycler.setAdapter(adapter);
        learnListRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }


}