package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
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

import org.jetbrains.annotations.NotNull;

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
        categoryVideoImageParent = view.findViewById(R.id.categoryVideoImageParent);
        DB = new DBHelper(context);


        setProgressSetup();
    }

    public void setProgressSetup(){
        String progressLabel;
        String progressText;
        int progressBar = 0;
        int tempProgress = 0;
        String tempTotalItems = "";
        int totalItems;
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
                tempTotalItems = learnVideoListCursor.getString(1);
                tempImageName = learnVideoListCursor.getString(2);
                tempProgress = learnVideoListCursor.getInt(3);

                color = getResources().getIdentifier(tempColor, "color", context.getPackageName());
                totalItems = Integer.parseInt(tempTotalItems);
                imageName = getResources().getIdentifier(tempImageName, "drawable", context.getPackageName());
                progressBar = tempProgress * 100/totalItems;
            }
        }
        FunctionHelper functionHelper = new FunctionHelper();
        categoryVideoImage.setImageResource(imageName);
        categoryVideoImageParent.setBackgroundResource(color);
        categoryVideoProgressText.setText(getResources().getString(R.string.learn_list_progress, Integer.toString(tempProgress), tempTotalItems));
        categoryVideoProgressLabel.setText(functionHelper.getCategoryProgressDescription(progressBar));
        categoryVideoProgressBar.setProgress(progressBar);


        categoryVideoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("learn_video_item", kategorya);
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                try {
                    Navigation.findNavController(view).navigate(R.id.action_learn_category_video_to_learn_video_item, bundle, navBuilder.build());
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        });
    }
}