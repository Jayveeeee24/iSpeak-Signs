package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.DBHelper;
import com.artemis.ispeaksigns.R;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressItem;
import com.artemis.ispeaksigns.adapter_list_profile.ProfileProgressListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class nav_profile extends Fragment {

    View view;
    Context context;
    TextView editUserTextView;
    RecyclerView profileRecycler;
    CardView profileSeeMore;
    DBHelper DB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();
        DB = new DBHelper(context);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileRecycler = view.findViewById(R.id.profile_recycler);
        profileRecycler.setNestedScrollingEnabled(false);
        InitializeRecycler();
        InitializeOnClick();
    }

    private void InitializeRecycler()
    {
        ArrayList<ProfileProgressItem> profileProgressItems = new ArrayList<>();
        profileRecycler.setNestedScrollingEnabled(false);

        Cursor profileSeeMoreCursor = DB.getAllCategory("haha", "Profile");

        String[] categoryName = new String[profileSeeMoreCursor.getCount()];
        String[] imageUrls = new String[categoryName.length];
        String [] bgColors = new String[categoryName.length];
        int[] images = new int[imageUrls.length];
        int[] colors = new int[bgColors.length];
        String[] categoryTotal = new String[categoryName.length];
        int[] categoryProgress = new int[categoryName.length];
        int[] categoryPercent = new int[categoryProgress.length];
        String[] categoryType = new String[categoryName.length];
        String[] categoryPercentConverted = new String[categoryPercent.length];

//        FunctionHelper functionHelper = new FunctionHelper();
//        for (int i = 0; i<categoryName.length; i++){
//            imageUrls[i] = functionHelper.getImageLogo(categoryName[i]);
//        }
        if (profileSeeMoreCursor.getCount() == 0){
            Toast.makeText(context, "Ang database ay walang laman, Pakiulit na lamang", Toast.LENGTH_SHORT).show();
            return;
        }else{
            int i = 0;
            while(profileSeeMoreCursor.moveToNext()){
                categoryName[i] = profileSeeMoreCursor.getString(0);
                bgColors[i] = profileSeeMoreCursor.getString(1);
                categoryTotal[i] = profileSeeMoreCursor.getString(2);
                categoryType[i] = profileSeeMoreCursor.getString(3);
                imageUrls[i] = profileSeeMoreCursor.getString(4);
                categoryProgress[i] = profileSeeMoreCursor.getInt(5);
                i++;
            }
        }

        for (int i = 0; i<categoryName.length; i++){
            categoryPercent[i] = categoryProgress[i] * 100/Integer.parseInt(categoryTotal[i]);
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
        profileSeeMore = view.findViewById(R.id.profile_see_more);
        final ImageView editUserButton = (ImageView) view.findViewById(R.id.edit_user_name);
        final EditText editTextUser = view.findViewById(R.id.edit_text_user_name);
        editUserTextView = view.findViewById(R.id.user_name);
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
                    editUserTextView.setVisibility(View.INVISIBLE);
                    editTextUser.setVisibility(View.VISIBLE);
                    isEdit = 1;
                } else if (isEdit == 1) {
                    editUserButton.setImageResource(R.drawable.ic_edit);
                    if (!editTextUser.getText().toString().equals(editUserTextView.getText().toString())) {
                        editUserTextView.setText(editTextUser.getText().toString());
                        Toast.makeText(context, "Ang username ay napalitan na bilang " + editTextUser.getText().toString(), Toast.LENGTH_SHORT).show();
//                      TODO provide a real action when the user edit the text (database operations)
                    }
                    editTextUser.setVisibility(View.INVISIBLE);
                    editUserTextView.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    isEdit = 0;
                }
            }
        });

        profileSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_profile_to_profile_see_more);
            }
        });

    }

}