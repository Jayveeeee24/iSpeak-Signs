package com.artemis.ispeaksigns.main_fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

public class nav_about extends Fragment {

    View view;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeOnClick();
    }

    private void InitializeOnClick(){
        CardView cardJb = view.findViewById(R.id.card_jb);
        CardView cardMeann = view.findViewById(R.id.card_meann);
        CardView cardReiAnne = view.findViewById(R.id.card_reianne);
        ImageView logo1 = view.findViewById(R.id.logo1);
        ImageView logo2 = view.findViewById(R.id.logo2);
        ImageView logo3 = view.findViewById(R.id.logo3);

        cardJb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMemberPopUp(getResources().getString(R.string.member1_name));
            }
        });
        cardMeann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMemberPopUp(getResources().getString(R.string.member2_name));
            }
        });
        cardReiAnne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMemberPopUp(getResources().getString(R.string.member3_name));
            }
        });

        logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoPopUp("Gender and Development Unit");
            }
        });
        logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoPopUp(getResources().getString(R.string.app_name));
            }
        });
        logo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoPopUp("Cavite State University");
            }
        });
    }

    private void showMemberPopUp(String name){
        final Dialog memberPopup = new Dialog(context);
        memberPopup.setContentView(R.layout.popup_member_about);
        memberPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        memberPopup.setCanceledOnTouchOutside(true);
        memberPopup.setCancelable(true);
        final TextView popupName = (TextView) memberPopup.findViewById(R.id.popup_name);
        final ImageView popupImage = (ImageView) memberPopup.findViewById(R.id.popup_image);
        final TextView popupType = (TextView) memberPopup.findViewById(R.id.popup_type);
        final TextView popupAge = (TextView) memberPopup.findViewById(R.id.popup_age);
        final TextView popupGoal = (TextView) memberPopup.findViewById(R.id.popup_goal);
        final TextView popupStrength = (TextView) memberPopup.findViewById(R.id.popup_strength);

        switch (name) {
            case "John Bernard Tinio":
                popupName.setText(name);
                popupAge.setText("Age: 22");
                popupGoal.setText("Aspiring Software Engineer");
                popupStrength.setText("\"What if one day you woke up and ikaw na ang Seo Dal Mi ko?\"");
                popupType.setText("Email me at: johnbernard.tinio@cvsu.edu.ph");
                break;
            case "Marie Ann Karen De Guzman":
                popupName.setText(name);
                popupImage.setImageResource(R.drawable.meann_profile);
                popupAge.setText("Age: 21");
                popupGoal.setText("Aspiring Graphic Artist");
                popupStrength.setText("\"Everything happens for a reason\"");
                popupType.setText("Email me at: marieannkaren@gmail.com");
                break;
            case "Rei Anne P. Velasco":
                popupName.setText(name);
                popupImage.setImageResource(R.drawable.reianne_profile);
                popupAge.setText("Age: 21");
                popupGoal.setText("Aspiring World Class Chef ");
                popupStrength.setText("\"The journey of a thousand miles begins with every single step, so start walking\"");
                popupType.setText("Email me at: vreianne@gmail.com");
                break;
        }
        memberPopup.show();
    }

    private void showLogoPopUp(String name){
        final Dialog logoPopup = new Dialog(context);
        logoPopup.setContentView(R.layout.popup_logo_about);
        logoPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        logoPopup.setCanceledOnTouchOutside(true);
        logoPopup.setCancelable(true);
        final TextView logoName = (TextView) logoPopup.findViewById(R.id.logo_name);
        final ImageView logoImage = (ImageView) logoPopup.findViewById(R.id.logo_image);
        final TextView logoSubname = (TextView) logoPopup.findViewById(R.id.logo_subname);
        final TextView logoDesc = (TextView) logoPopup.findViewById(R.id.logo_desc);

        switch (name) {
            case "Gender and Development Unit":
                logoName.setText(name);
                logoImage.setImageResource(R.drawable.gad_logo2);
                logoSubname.setText("CvSU-CCC GAD");
                logoDesc.setText("\"Gender Equality is a human fight, not a female fight\"");
                break;
            case "iSpeak Signs":
                logoName.setText(name);
                logoImage.setImageResource(R.drawable.ic_ispeak_sign_logo);
                logoSubname.setText("Filipino Sign Language Learning Application");
                logoDesc.setText("\"Learning should be accessible, anywhere\"");
                break;
            case "Cavite State University":
                logoName.setText(name);
                logoImage.setImageResource(R.drawable.cvsu_logo);
                logoSubname.setText("Cavite City Campus");
                logoDesc.setText("\"Truth, Excellence, and Service\"");
                break;
        }
        logoPopup.show();
    }
}