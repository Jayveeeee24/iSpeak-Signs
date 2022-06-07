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

public class HowToSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String languageCode;

    public HowToSliderAdapter(Context context, String languageCode) {
        this.context = context;
        this.languageCode = languageCode;
    }

    public int[] images = new int[]{
            R.drawable.how_to_1,
            R.drawable.how_to_2,
            R.drawable.how_to_3,
            R.drawable.how_to_4,
            R.drawable.how_to_5
    };

    public String[] title_english = new String[]{
            "Identify what FSL sign is displayed and choose which is the correct answer",
            "Be mindful of your time!, each level has certain time limits",
            "Each game has three (3) lives which acts as a shield when you choose the wrong answer",
            "Each hand signs guessed correctly will give score, rise through the high scores!",
            "Guess all the hand signs correctly and earn a badge that symbolizes that you're an \"FSL Learner\""
    };

    public String[] title_filipino = new String[]{
            "Alamin ang FSL senyas na ipinapakita sa imahe at piliin ang tamang sagot",
            "Palaging alamin ang natitirang oras dahil bawat level ay may limitadong oras",
            "Bawat laro ay mayroong tatlo (3) na buhay na magsisilbing kasangga sa tuwing ikaw ay nakapili ng maling sagot",
            "Bawat tamang senyas na iyong mahuhulaan ay magbibigay ng puntos, abutin ang pinakamataas na puntos!",
            "Hulaan ang lahat ng senyas nang tama at makakuha ng palatandaang sumisimbulo na ikaw ay isang \"FSL Learner\""
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
        View view = layoutInflater.inflate(R.layout.how_to_slide_layout, container, false);

        ImageView sliderImage = view.findViewById(R.id.how_to_slider_image);
        TextView sliderTitle = view.findViewById(R.id.how_to_slider_title);

        sliderImage.setImageResource(images[position]);

        if (languageCode.equals("Filipino")){
            sliderTitle.setText(title_filipino[position]);
        }else if (languageCode.equals("English")){
            sliderTitle.setText(title_english[position]);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((RelativeLayout)object);
    }

}
