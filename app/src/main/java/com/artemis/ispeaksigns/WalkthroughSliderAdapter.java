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
            R.drawable.ic_walkthrough_mini_game,
            R.drawable.ic_walkthrough_recognition
    };

    public String[] title_english = new String[]{
            "Discover and Learn FSL words",
            "Watch FSL video phrases",
            "Save favorite words or phrases",
            "Track your progress in each category",
            "You can play the mini game \"Guess the Sign\"",
            "Recognize signs with FSL Recognition"
    };
    public String[] title_filipino = new String[]{
            "Tumuklas ng mga FSL na salita",
            "Manood ng FSL ekspresyong parirala",
            "i-Save ang paboritong salita o parirala",
            "Subaybayan ang progreso sa bawat kategorya",
            "Maaari mong laruin ang mini game na \"Guess the Sign\"",
            "Kumilala gamit ang FSL Rekognisyon"
    };

    public String[] descriptions_english = new String[]{
            "You can learn words and their equivalent Filipino Sign Language (FSL) demonstration in the right way.",
            "You can watch how to sign the most common expressions with video phrase demonstration.",
            "You can save and review words and phrases with an easy click to the heart button for an easy access anytime and anywhere.",
            "Track all your progress on each words and phrase that you've learned in each category.",
            "The user can play the mini game \"Guess the Sign\" and earn a badge of \"FSL Learner\"",
            "Recognize some FSL letters and words using your camera with the FSL Recognition Technology"
    };

    public String[] descriptions_filipino = new String[]{
            "Maaaring matutunan at makita kung paano ang tamang paraan ng bawat Filipino Sign Language (FSL) na salita.",
            "Maaaring manood kung paano gawin ang mga pinakakaraniwang ekspresyong parirala.",
            "Maaaring i-save ang mga paboritong salita o parirala sa pamamagitan ng pagclick sa puso upang madali ang pag-access kahit kailan at kahit saan.",
            "Maaaring subaybayan ang iyong progreso sa mga salita o parirala na iyong napag-aralan sa bawat kategorya.",
            "Ang user ay maaaring laruin ang mini game na \"Guess the Sign\" at makakuha ng palatandaang ikaw ay isang \"FSL Learner\"",
            "Maaaring kilalanin ang senyas gamit ang iyong kamera sa pamamagitan ng FSL Recognition Technology"
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
