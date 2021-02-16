package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.classes.Genre;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Genre> listGenres;

    public GenreAdapter(Context context, ArrayList<Genre> listGenres) {
        this.context = context;
        this.listGenres = listGenres;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_genres_cardview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Genre currentGenre =  listGenres.get(position);
        String genre = currentGenre.getGenre();

        holder.mGenre.setText(genre);
    }

    @Override
    public int getItemCount() {
        return listGenres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mGenre;

        public MyViewHolder(View itemView) {
            super(itemView);
            mGenre = itemView.findViewById(R.id.genreName);
        }
    }
}