package com.artemis.ispeaksigns.main_fragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressItem;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class nav_profile extends Fragment {

    View view;
    Context context;

    RecyclerView profileRecycler;
    TextView wordDiscovered;
    TextView phraseDiscovered;
    TextView favoriteCount;
    CardView profileSeeMoreCard;
    ImageView userImage;
    String userName;
    EditText editTextUserName;
    TextView userNameText;
    TextView currentStreak;
    TextView longestStreak;

    CardView userImageParent;
    CardView userImageEdit;

    String avatarName = "";
    String selectedAvatar = "";
    int avatarImage;
    DBHelper DB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileRecycler = view.findViewById(R.id.profile_recycler);
        profileRecycler.setNestedScrollingEnabled(false);
        wordDiscovered = view.findViewById(R.id.word_discorvered);
        phraseDiscovered = view.findViewById(R.id.phrase_discovered);
        favoriteCount = view.findViewById(R.id.favorite_discovered);
        userImage = view.findViewById(R.id.user_image);
        userNameText = view.findViewById(R.id.user_name);
        editTextUserName = view.findViewById(R.id.edit_text_user_name);
        currentStreak = view.findViewById(R.id.current_streak);
        longestStreak = view.findViewById(R.id.longest_streak);
        userImageParent = view.findViewById(R.id.user_image_parent);
        userImageEdit = view.findViewById(R.id.user_image_edit);
        DB = new DBHelper(context);

        InitializeDesign();
        InitializeRecycler();
        InitializeOnClick();
    }

    private void InitializeDesign(){

        int wordDiscoveredCount = 0;
        int phraseDiscoveredCount = 0;
        int favoriteCountNo = 0;
        int currentStreakCount = 0;
        int longestStreakCount = 0;
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
        }
        Cursor updateStreakCard = DB.getUserData("", "StreakAvatarCard");

        if (updateStreakCard.getCount() == 0){
            Toast.makeText(context, "Ang database ay walang laman, Pakiulit na lamang", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while(updateStreakCard.moveToNext()){
                currentStreakCount = updateStreakCard.getInt(0);
                longestStreakCount = updateStreakCard.getInt(1);
                avatarName = updateStreakCard.getString(2);
            }
        }

        userNameText.setText(userName);
        editTextUserName.setText(userName);

        Cursor wordDiscoveredCursor = DB.countItems("WordDiscovered");
        Cursor phraseDiscoveredCursor = DB.countItems("PhraseDiscovered");
        Cursor favoriteCountCursor = DB.countItems("FavoriteCount");

        if (wordDiscoveredCursor.getCount() != 0){
            while (wordDiscoveredCursor.moveToNext()){
                wordDiscoveredCount = wordDiscoveredCursor.getInt(0);
            }
        }
        if (phraseDiscoveredCursor.getCount() != 0){
            while (phraseDiscoveredCursor.moveToNext()){
                phraseDiscoveredCount = phraseDiscoveredCursor.getInt(0);
            }
        }
        if (favoriteCountCursor.getCount() != 0){
            while (favoriteCountCursor.moveToNext()){
                favoriteCountNo = favoriteCountCursor.getInt(0);
            }
        }

        wordDiscovered.setText(getResources().getString(R.string.word_discovered_text, Integer.toString(wordDiscoveredCount)));
        phraseDiscovered.setText(getResources().getString(R.string.phrase_discovered_label, Integer.toString(phraseDiscoveredCount)));
        favoriteCount.setText(getResources().getString(R.string.favorite_word_phrase_label, Integer.toString(favoriteCountNo)));

        longestStreak.setText(getResources().getString(R.string.longest_streak_days, Integer.toString(longestStreakCount)));
        currentStreak.setText(getResources().getString(R.string.profile_streak_count, Integer.toString(currentStreakCount)));
        avatarImage = getResources().getIdentifier(avatarName, "drawable", context.getPackageName());
        userImage.setImageResource(avatarImage);

    }

    private void InitializeRecycler()
    {
        ArrayList<ProfileProgressItem> profileProgressItems = new ArrayList<>();
        profileRecycler.setNestedScrollingEnabled(false);

        Cursor profileSeeMoreCursor = DB.getCategory("haha", "Profile");

        String[] categoryName = new String[profileSeeMoreCursor.getCount()];
        String[] imageUrls = new String[categoryName.length];
        String [] bgColors = new String[categoryName.length];
        int[] images = new int[imageUrls.length];
        int[] colors = new int[bgColors.length];
        int[] categoryTotal = new int[categoryName.length];
        int[] categoryProgress = new int[categoryName.length];
        int[] categoryPercent = new int[categoryProgress.length];
        String[] categoryType = new String[categoryName.length];
        String[] categoryPercentConverted = new String[categoryPercent.length];

        if (profileSeeMoreCursor.getCount() == 0){
            Toast.makeText(context, "No database found!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            int i = 0;
            while(profileSeeMoreCursor.moveToNext()){
                categoryName[i] = profileSeeMoreCursor.getString(0);
                bgColors[i] = profileSeeMoreCursor.getString(1);
                categoryTotal[i] = profileSeeMoreCursor.getInt(2);
                categoryType[i] = profileSeeMoreCursor.getString(3);
                imageUrls[i] = profileSeeMoreCursor.getString(4);
                categoryProgress[i] = profileSeeMoreCursor.getInt(5);
                i++;
            }
        }

        for (int i = 0; i<categoryName.length; i++){
            categoryPercent[i] = categoryProgress[i] * 100/ categoryTotal[i];
            images[i] = getResources().getIdentifier(imageUrls[i], "drawable", context.getPackageName());
            colors[i] = getResources().getIdentifier(bgColors[i], "color", context.getPackageName());
            categoryPercentConverted[i] = categoryPercent[i] + "%";

        }

        for (int i = 0; i<categoryName.length; i++)
        {
            profileProgressItems.add(new ProfileProgressItem(colors[i], images[i], categoryName[i],
                    categoryPercentConverted[i], categoryPercent[i], categoryType[i]));
        }

        ProfileProgressListAdapter adapter = new ProfileProgressListAdapter();
        adapter.setProfileProgressItems(profileProgressItems);

        profileRecycler.setAdapter(adapter);
        profileRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


    }

    private void InitializeOnClick()
    {
        profileSeeMoreCard = view.findViewById(R.id.profile_see_more_label);
        final ImageView editUserButton = (ImageView) view.findViewById(R.id.edit_user_name);
        final EditText editTextUser = view.findViewById(R.id.edit_text_user_name);
        editTextUser.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (keyCode == EditorInfo.IME_ACTION_DONE)){
                    editUserButton.performClick();
                    return true;
                }
                return false;
            }
        });

        editUserButton.setOnClickListener(new View.OnClickListener() {
            int isEdit = 0;
            @Override
            public void onClick(View view) {
                if (isEdit == 0) {
                    editUserButton.setImageResource(R.drawable.ic_save);
                    userNameText.setVisibility(View.INVISIBLE);
                    editTextUser.setVisibility(View.VISIBLE);
                    isEdit = 1;
                } else if (isEdit == 1) {
                    editUserButton.setImageResource(R.drawable.ic_edit);
                    if (!editTextUser.getText().toString().equals(userNameText.getText().toString()) && !editTextUser.getText().toString().equals("")) {
                        userNameText.setText(editTextUser.getText().toString());
                        boolean checkUpdate = DB.updateSingleData(editTextUser.getText().toString(), 0, "UserName");
                        if (checkUpdate){
                            Toast.makeText(context, "Ang username ay napalitan na bilang " + editTextUser.getText().toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Hindi mapalitan ang iyong username, itry nalang muli", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (editTextUser.getText().toString().equals("")){
                        Toast.makeText(context, "Ang bagong username ay invalid!", Toast.LENGTH_SHORT).show();
                        userNameText.setText(userName);
                        editTextUser.setText(userName);
                    }
                    editTextUser.setVisibility(View.INVISIBLE);
                    userNameText.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    isEdit = 0;
                }
            }
        });

        profileSeeMoreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_profile_to_profile_see_more);
            }
        });

        final Dialog changeUserImage = new Dialog(context);
        changeUserImage.setContentView(R.layout.popup_change_avatar);
        changeUserImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        changeUserImage.setCanceledOnTouchOutside(true);
        changeUserImage.setCancelable(true);
        final Button saveImageButton = (Button) changeUserImage.findViewById(R.id.save_new_user_image);
        final CardView avatar1, avatar2, avatar3, avatar4;
        avatar1 = (CardView) changeUserImage.findViewById(R.id.card_avatar1);
        avatar2 = (CardView) changeUserImage.findViewById(R.id.card_avatar2);
        avatar3 = (CardView) changeUserImage.findViewById(R.id.card_avatar3);
        avatar4 = (CardView) changeUserImage.findViewById(R.id.card_avatar4);

        userImageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAvatar = "avatar1";
                avatar1.setCardElevation(20);

                changeUserImage.show();

                avatar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedAvatar = "avatar1";
                        avatar1.setCardElevation(20);
                        avatar2.setCardElevation(0);
                        avatar3.setCardElevation(0);
                        avatar4.setCardElevation(0);
                    }
                });
                avatar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedAvatar = "avatar2";
                        avatar1.setCardElevation(0);
                        avatar2.setCardElevation(20);
                        avatar3.setCardElevation(0);
                        avatar4.setCardElevation(0);
                    }
                });

                avatar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedAvatar = "avatar3";
                        avatar1.setCardElevation(0);
                        avatar2.setCardElevation(0);
                        avatar3.setCardElevation(20);
                        avatar4.setCardElevation(0);
                    }
                });
                avatar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedAvatar = "avatar4";
                        avatar1.setCardElevation(0);
                        avatar2.setCardElevation(0);
                        avatar3.setCardElevation(0);
                        avatar4.setCardElevation(20);
                    }
                });

                saveImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!avatarName.equals(selectedAvatar)){
                            boolean checkUpdateUserAvatar = DB.updateSingleData(selectedAvatar, 0, "Avatar");
                            if (checkUpdateUserAvatar){
                                Log.i("Nav Profile", "Insert New User Success!");
                            }else{
                                Log.i("Nav Profile", "Insert New User Failed.");
                            }
                            changeUserImage.dismiss();
                            avatarImage = getResources().getIdentifier(selectedAvatar, "drawable", context.getPackageName());
                            userImage.setImageResource(avatarImage);
                            avatarName = selectedAvatar;
                        }else{
                            changeUserImage.dismiss();
                        }
                    }
                });
            }
        });

        userImageEdit.setOnClickListener(view -> userImageParent.performClick());
    }

}