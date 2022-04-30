package com.artemis.ispeaksigns;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

public class WalkthroughSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String languageCode;

    public WalkthroughSliderAdapter(Context context, String languageCode){
        this.context = context;
        this.languageCode = languageCode;
    }

    public int[] images = {
            R.drawable.ic_walkthrough_learn,
            R.drawable.ic_walkthrough_video,
            R.drawable.ic_walkthrough_favorites,
            R.drawable.ic_walkthrough_progress,
            R.drawable.ic_walkthrough_recognition
    };

    public String[] title_english = new String[]{
            "Discover and Learn FSL words",
            "Watch FSL video phrases",
            "Save favorite words or phrases",
            "Track your progress in each category",
            "Recognize words with FSL Recognition"
    };
    public String[] title_filipino = new String[]{
            "Tumuklas ng mga FSL na salita",
            "Manood ng FSL ekspresyong parirala",
            "i-Save ang paboritong salita o parirala",
            "Subaybayan ang progreso sa bawat kategorya",
            "Kumilala gamit ang FSL Rekognisyon"
    };

    public String[] descriptions_english = new String[]{
            "You can learn words and their equivalent FSL demonstration in the right way.",
            "You can watch how to sign the most common expressions with video phrase demonstration.",
            "You can save and review words and phrases for an easy access anytime and anywhere!",
            "Track all your progress on each category in percentage to plan your learning!",
            "Recognize some words using the FSL Recognition feature with Finger Spelling to aid your FSL learning journey!"
    };

    public String[] descriptions_filipino = new String[]{
            "Maaaring matutunan at makita kung paano ang tamang paraan ng bawat FSL na salita.",
            "Maaaring manood kung paano gawin ang mga pinakakaraniwang ekspresyong parirala.",
            "Maaaring i-save ang mga paboritong salita o parirala upang madali ang pag-access kahit kailan at kahit saan!",
            "Maaaring subaybayan ang iyong progreso sa bawat kategorya at piliin ang kung ano ang iyong pagbubutihin",
            "Maaaring kilalanin ang senyas gamit ang iyong kamera sa pamamagitan ng FSL Rekognisyon"
    };

    @Override
    public int getCount() {
        return title_english.length;
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
        View view = layoutInflater.inflate(R.layout.walkthrough_slide_layout, container, false);

        ImageView sliderImage = view.findViewById(R.id.slider_image);
        TextView sliderTitle = view.findViewById(R.id.slider_title);
        TextView sliderDescription = view.findViewById(R.id.slider_description);

        sliderImage.setImageResource(images[position]);

        if (languageCode.equals("tl")){
            sliderTitle.setText(title_filipino[position]);
            sliderDescription.setText(descriptions_filipino[position]);
        }else if (languageCode.equals("en")){
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
