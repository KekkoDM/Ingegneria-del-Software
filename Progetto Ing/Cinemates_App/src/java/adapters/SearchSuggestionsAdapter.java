package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.activities.MediaDescriptorActivity;
import com.example.cinemates.classes.Media;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchSuggestionsAdapter extends RecyclerView.Adapter<SearchSuggestionsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Media> listSearchSuggestions;

    public SearchSuggestionsAdapter(Context context, ArrayList<Media> listSearchSuggestions) {
        this.context = context;
        this.listSearchSuggestions = listSearchSuggestions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.suggestion_cardview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Media currentSearchSuggestion =  listSearchSuggestions.get(position);
        holder.setCardView(currentSearchSuggestion);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaDescriptorActivity.class);
                intent.putExtra("Film",listSearchSuggestions.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSearchSuggestions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView coverSuggest;
        public TextView titleSuggest;

        public MyViewHolder(View itemView) {
            super(itemView);
            coverSuggest = itemView.findViewById(R.id.coverSearch);
            titleSuggest = itemView.findViewById(R.id.titleSearch);
        }

        public void setCardView(Media currentSearchSuggestion) {
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+ currentSearchSuggestion.getBackdrop()).into(coverSuggest);
            titleSuggest.setText(currentSearchSuggestion.getTitle());
        }
    }
}