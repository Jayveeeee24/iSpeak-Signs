package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
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
import com.artemis.ispeaksigns.adapter_list_learn_list.LearnListVideoCategoryItem;
import com.artemis.ispeaksigns.adapter_list_learn_list.LearnListVideoRecyclerAdapter;
import com.artemis.ispeaksigns.adapter_list_learn_list.LearnListWordCategoryItem;
import com.artemis.ispeaksigns.adapter_list_learn_list.LearnListWordRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class learn_video_list extends Fragment {

    View view;
    Context context;
    DBHelper DB;
    ImageView categoryVideoImage;
    TextView categoryVideoProgressLabel;
    TextView categoryVideoProgressText;
    ProgressBar categoryVideoProgressBar;
    RecyclerView learnVideoListRecycler;
    RelativeLayout categoryVideoImageParent;
    String kategorya = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_video_list, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryVideoImage = view.findViewById(R.id.categoryVideoImage);
        categoryVideoProgressLabel = view.findViewById(R.id.txtProgressVideoCardLabel);
        categoryVideoProgressText = view.findViewById(R.id.txtProgressVideoPercent);
        categoryVideoProgressBar = view.findViewById(R.id.progressVideoLearn);
        learnVideoListRecycler = view.findViewById(R.id.learn_video_list_recycler);
        learnVideoListRecycler.setNestedScrollingEnabled(false);
        categoryVideoImageParent = view.findViewById(R.id.categoryVideoImageParent);
        DB = new DBHelper(context);


        setProgressSetup();
        InitializeRecycler();
    }

    private void setProgressSetup(){
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

        Cursor learnVideoListCursor = DB.getCategory(kategorya, "SingleCategory");

        if (learnVideoListCursor.getCount() == 0){
            Toast.makeText(context, "No database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (learnVideoListCursor.moveToNext()){
                tempColor = learnVideoListCursor.getString(0);
                totalItems = learnVideoListCursor.getInt(1);
                tempImageName = learnVideoListCursor.getString(2);
                tempProgress = learnVideoListCursor.getInt(3);

                color = getResources().getIdentifier(tempColor, "color", context.getPackageName());
                imageName = getResources().getIdentifier(tempImageName, "drawable", context.getPackageName());
                progressBar = tempProgress * 100/totalItems;
            }
        }
        FunctionHelper functionHelper = new FunctionHelper();
        categoryVideoImage.setImageResource(imageName);
        categoryVideoImageParent.setBackgroundResource(color);
        categoryVideoProgressText.setText(getResources().getString(R.string.learn_list_progress, Integer.toString(tempProgress), Integer.toString(totalItems)));
        categoryVideoProgressLabel.setText(functionHelper.getCategoryProgressDescription(progressBar, getActivity()));
        categoryVideoProgressBar.setProgress(progressBar);

    }

    private void InitializeRecycler(){
        ArrayList<LearnListVideoCategoryItem> learnListVideoCategoryItems = new ArrayList<>();

        String[] itemName = new String[]{
                "Magandang Araw", "Maaari bang humiram ng telepono mo taena shem loko", "Ano ang pangalan mo",
                "Tulungan nyo ako", "Ako ay nawawala", "Nauunawaan mo ba ako",
                "Maraming salamat sa iyo"
        };
        int[] isLearned = new int[]{
                1, 1, 0, 1, 1, 0, 1
        };

        for (int i = 0; i<itemName.length; i++) {
            learnListVideoCategoryItems.add(new LearnListVideoCategoryItem(itemName[i], isLearned[i]));
        }

        LearnListVideoRecyclerAdapter adapter = new LearnListVideoRecyclerAdapter();
        adapter.setLearnListVideoCategoryItems(learnListVideoCategoryItems);

        learnVideoListRecycler.setAdapter(adapter);
        learnVideoListRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }
}