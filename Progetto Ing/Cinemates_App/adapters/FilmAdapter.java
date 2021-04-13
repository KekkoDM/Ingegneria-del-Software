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
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.classes.Film;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Film> listFilm;

    private FirebaseAnalytics mFirebaseAnalytics;


    public FilmAdapter(ArrayList<Film> listFilm, Context context) {
        this.context = context;
        this.listFilm = listFilm;
    }

    public Film getItem(int position) {
        return listFilm.get(position);
    }

    public ArrayList<Film> getListFilm() {
        return listFilm;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        return new FilmAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.MyViewHolder holder, int position) {
        holder.setFilm(listFilm.get(position));
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDescriptorActivity.class);
                intent.putExtra("Film",listFilm.get(position));

                // [START custom_event]
                Bundle params = new Bundle();
                params.putString("id_film", listFilm.get(position).getId());
                params.putString("title", listFilm.get(position).getTitle());
                params.putString("type", listFilm.get(position).getType());
                mFirebaseAnalytics.logEvent("Description_Film", params);
                // [END custom_event]

                System.out.println("Bundle: "+params);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFilm.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView poster;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_movie_title);
            poster = itemView.findViewById(R.id.item_movie_img);
        }

        void setFilm(Film film) {
            title.setText(film.getTitle());

            if (film.getCover().equals("null")) {
                poster.setImageResource(R.drawable.no_cover_found);
            }
            else {
                Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + film.getCover()).into(poster);
            }

        }

    }
}
