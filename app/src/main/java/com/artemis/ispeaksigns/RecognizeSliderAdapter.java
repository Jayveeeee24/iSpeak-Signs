package com.artemis.ispeaksigns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

public class RecognizeSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String languageCode;

    public RecognizeSliderAdapter(Context context, String languageCode){
        this.context = context;
        this.languageCode = languageCode;
    }

    public int[] images = {
            R.drawable.ic_walkthroug_recognition1,
            R.drawable.ic_walkthrough_recognition2
    };

    public String[] title_filipino = {
            "Kilalanin ang senyas ng FSL",
            "Bumuo ng FSL na salita"
    };

    public String[] title_english = {
        "Recognize FSL hand signs",
        "Form an FSL word"
    };

    public String[] descriptions_filipino = {
            "Ilapat ang iyong kamay sa tapat ng kamera at alamin kung anong letrang senyas ng FSL ang iyong ipinapakita",
            "Maaaring bumuo ng salita sa pamamagitan ng FingerSpelling gamit ang pagdagdag at pagbabawas ng letra"
    };

    public String[] descriptions_english = {
            "Place your hand on the front of a camera at recognize which letter in the FSL alphabet is being signed",
            "You can create a word by FingerSpelling by Adding and Removing a letter"
    };

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recognize_slide_layout, container, false);

        ImageView sliderImage = view.findViewById(R.id.recognize_slider_image);
        TextView sliderTitle = view.findViewById(R.id.recognize_slider_title);
        TextView sliderDescription = view.findViewById(R.id.recognize_slider_description);

        sliderImage.setImageResource(images[position]);

        if (languageCode.equals("Filipino")){
            sliderTitle.setText(title_filipino[position]);
            sliderDescription.setText(descriptions_filipino[position]);
        }else if (languageCode.equals("English")){
            sliderTitle.setText(title_english[position]);
            sliderDescription.setText(descriptions_english[position]);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
