package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.activities.MediaDescriptorActivity;
import com.example.cinemates.classes.Media;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Media> listMedia;
    private FirebaseAnalytics mFirebaseAnalytics;

    public MediaAdapter(ArrayList<Media> listMedia, Context context) {
        this.context = context;
        this.listMedia = listMedia;
    }

    public Media getItem(int position) {
        return listMedia.get(position);
    }

    public ArrayList<Media> getListMedia() {
        return listMedia;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        return new MediaAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaAdapter.MyViewHolder holder, int position) {
        holder.setFilm(listMedia.get(position));

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaDescriptorActivity.class);
                intent.putExtra("Film", listMedia.get(position));

                // [START custom_event]
                Bundle params = new Bundle();
                params.putString("id_film", listMedia.get(position).getId());
                params.putString("title", listMedia.get(position).getTitle());
                params.putString("type", listMedia.get(position).getType());
                mFirebaseAnalytics.logEvent("Description_Film", params);
                // [END custom_event]

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMedia.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView poster;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_movie_title);
            poster = itemView.findViewById(R.id.item_movie_img);
        }

        void setFilm(Media media) {
            title.setText(media.getTitle());

            if (media.getCover().equals("null")) {
                poster.setImageResource(R.drawable.no_cover_found);
            }
            else {
                Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + media.getCover()).into(poster);
            }

        }

    }
}
