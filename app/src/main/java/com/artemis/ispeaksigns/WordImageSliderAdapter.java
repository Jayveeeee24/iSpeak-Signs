package com.artemis.ispeaksigns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

public class WordImageSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String wordName;
    int imageNumber;
    ImageView sliderImage;

    public int[] imageName;
    public String[] imageNameS;

    public WordImageSliderAdapter(Context context, String wordName, int imageNumber){
        this.context = context;
        this.wordName = wordName;
        this.imageNumber = imageNumber;
        imageName =  new int[imageNumber];
        imageNameS = new String[imageNumber];

        for (int i = 0; i < imageNumber; i++){
            imageNameS[i] = wordName + i;
            imageName[i] = context.getResources().getIdentifier(imageNameS[i], "drawable", context.getPackageName());
        }
    }

    @Override
    public int getCount() {
        return imageNumber;
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
        View view = layoutInflater.inflate(R.layout.word_item_slide_layout, container, false);

        sliderImage = view.findViewById(R.id.word_item_slider_image);
        sliderImage.setImageResource(imageName[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
