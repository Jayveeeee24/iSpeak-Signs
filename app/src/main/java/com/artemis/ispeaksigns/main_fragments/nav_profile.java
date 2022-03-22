package com.artemis.ispeaksigns.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.artemis.ispeaksigns.R;

public class nav_profile extends Fragment {

    View view;
    Context context;
    TextView editUserTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();

        InitializeOnClick();

        return view;
    }

    private void InitializeOnClick()
    {
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
                    if (!editTextUser.getText().toString().equals(editUserTextView.getText().toString()))
                    {
                        editUserTextView.setText(editTextUser.getText().toString());
                        Toast.makeText(context, "Ang Username ay napalitan na bilang " + editTextUser.getText().toString(), Toast.LENGTH_SHORT).show();

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


    }

}