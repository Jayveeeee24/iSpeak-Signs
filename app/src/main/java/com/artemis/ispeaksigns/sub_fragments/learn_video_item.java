package com.artemis.ispeaksigns.sub_fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.FunctionHelper;
import com.artemis.ispeaksigns.MainActivity;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.VideoActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class learn_video_item extends Fragment {

    View view;
    Context context;
    DBHelper DB;
    FunctionHelper functionHelper;

    ImageView videoThumbnail;
    ImageView playVideo;
    TextView videoItemName;
    CardView videoViewLayout;
    TextView videoItemCategory;
    ImageView heartItem;

    int isFavorite = 0;
    String videoPath = "";
    String videoName;
    String itemType = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn_video_item, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DB = new DBHelper(context);
        functionHelper = new FunctionHelper();

        if (getArguments() != null)
        {
            videoName = getArguments().getString("learn_video_item");
            ((MainActivity) Objects.requireNonNull(getActivity())).collapseToolbar
                    .setTitle(videoName);
        }

        videoThumbnail = view.findViewById(R.id.video_thumbnail);
        playVideo = view.findViewById(R.id.play_video);
        videoItemName = view.findViewById(R.id.learn_video_item_name);
        videoViewLayout = view.findViewById(R.id.videoViewLayout);
        videoItemCategory = view.findViewById(R.id.video_item_category);
        heartItem = view.findViewById(R.id.learn_video_item_favorite);

        InitializeDesign();
        InitializeOnClick();
    }

    private void InitializeDesign(){
        videoPath = "video_demo";
        int videoNameConverted = getResources().getIdentifier(videoPath, "raw", context.getPackageName());
        Uri localUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + videoNameConverted);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(Objects.requireNonNull(getActivity()).getApplicationContext(), localUri);
        Bitmap thumb = retriever.getFrameAtTime(10, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
        videoThumbnail.setImageBitmap(thumb);

        int isLearned = 0;
        String itemCategory = "";
        Cursor learnVideoItemCursor = DB.getItem(videoName, "");
        if (learnVideoItemCursor.getCount() != 0){
            while (learnVideoItemCursor.moveToNext()){
                itemCategory = learnVideoItemCursor.getString(1);
                itemType = learnVideoItemCursor.getString(2);
                isLearned = learnVideoItemCursor.getInt(3);
            }
        }

        if (isLearned == 0){
            functionHelper.updateisLearnedProgress(context, itemCategory, videoName);
        }

        Cursor learnWordFavoriteCursor = DB.getItem(videoName, "getItemHeart");
        if (learnWordFavoriteCursor.getCount() == 0){
            heartItem.setImageResource(R.drawable.ic_favorites_outline);
            isFavorite = 0;
        }else{
            heartItem.setImageResource(R.drawable.ic_menu_favorites);
            isFavorite = 1;
        }

        videoItemName.setText(videoName);
        videoItemCategory.setText(getResources().getString(R.string.video_item_category_desc, itemCategory));
    }

    private void InitializeOnClick(){
        final ImageView learnVideoItemFavorite = (ImageView) view.findViewById(R.id.learn_video_item_favorite);

        learnVideoItemFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                if (isFavorite == 0) {
                    learnVideoItemFavorite.setImageResource(R.drawable.ic_menu_favorites);
                    isFavorite = 1;
                    functionHelper.updateFavorite(context, videoName, itemType, "Add");
                } else if (isFavorite == 1) {
                    learnVideoItemFavorite.setImageResource(R.drawable.ic_favorites_outline);
                    isFavorite = 0;
                    functionHelper.updateFavorite(context, videoName, null, "Remove");
                }
            }
        });

        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_button_clicked));
                startActivity(new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), VideoActivity.class).putExtra("ItemName", videoName));
            }
        });

        videoViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo.performClick();
            }
        });
    }

}