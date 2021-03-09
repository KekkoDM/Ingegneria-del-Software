package com.example.cinemates.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.cinemates.R;
import com.example.cinemates.activities.MovieDescriptor;
import com.example.cinemates.adapters.DescriptionFilmAdapter;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.PageAdapter;
import com.example.cinemates.adapters.SearchSuggestionsAdapter;
import com.example.cinemates.classes.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DescriptionFragment extends Fragment {
    private FilmAdapter filmAdapter;
    private DescriptionFilmAdapter adapter;
    private ImageView mCover;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mDuration;
    private TextView mReleaseDate;
    private TextView mValutation;
    private ImageView mBackdrop;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    private Film film;
    private ImageView favorites;
    private ImageView flag;


    public DescriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_descriptor, container, false);

        favorites = view.findViewById(R.id.favorites);
        flag = view.findViewById(R.id.to_see);
        film = ((MovieDescriptor)getActivity()).getFilm();
        adapter = new DescriptionFilmAdapter(film,getContext());
        System.out.println("FILM : "+ film.getTitle());

        mCover = view.findViewById(R.id.detail_movie_img);
        mTitle = view.findViewById(R.id.detail_movie_title);
        mDescription = view.findViewById(R.id.detail_movie_desc);
        mDuration = view.findViewById(R.id.detail_movie_duration);
        mReleaseDate = view.findViewById(R.id.detail_movie_realise_date);
        mValutation = view.findViewById(R.id.detail_movie_valutation);
        mBackdrop = view.findViewById(R.id.detail_movie_cover);


        if (film.getCover().equals("null")) {
            mCover.setImageResource(R.drawable.no_cover_found);
        }
        else {
            Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/w500"+film.getCover()).into(mCover);
        }
        if (film.getBackdrop().equals("null")) {
            mBackdrop.setImageResource(R.drawable.no_cover_found);
        }
        else {
            Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/w500"+film.getBackdrop()).into(mBackdrop);
        }
        mTitle.setText(film.getTitle());
        mDescription.setText(film.getDescription());
        mReleaseDate.setText(film.getReleaseDate());
        //mDuration.setText(result.getDuration());
        mValutation.setText(film.getValutation());

        return view;
    }
}