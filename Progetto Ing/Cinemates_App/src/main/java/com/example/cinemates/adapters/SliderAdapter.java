package com.example.cinemates.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cinemates.classes.Slide;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    private ArrayList<Slide> slides;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
