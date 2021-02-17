package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.RequestQueue;
import com.example.cinemates.R;
import com.example.cinemates.classes.Slide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private ArrayList<Slide> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapter(ArrayList<Slide> sliderItems, ViewPager2 viewPager2, Context context) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.slider_item,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setSlide(sliderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover;
        private TextView title;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.movie_cover);
            title = itemView.findViewById(R.id.title_movie);
        }

        void setSlide(Slide sliderItem) {
            title.setText(sliderItem.getTitle());
            Picasso.with(context).load(sliderItem.getCover()).into(cover);
        }
    }
}
