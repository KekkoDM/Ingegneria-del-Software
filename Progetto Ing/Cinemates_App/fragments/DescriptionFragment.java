package com.example.cinemates.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.cinemates.R;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.classes.Film;
import com.squareup.picasso.Picasso;

public class DescriptionFragment extends Fragment {
    private FilmAdapter filmAdapter;

    private ImageView mCover;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mDuration;
    private TextView mReleaseDate;
    private TextView mValutation;
    private ImageView mBackdrop;
    private Button addTo;
    private Film film;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public DescriptionFragment() {
        // blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_descriptor, container, false);

        film = ((MovieDescriptorActivity)getActivity()).getFilm();

        System.out.println("FILM : "+ film.getTitle());

        addTo = view.findViewById(R.id.addToBtn);
        mCover = view.findViewById(R.id.detail_movie_img);
        mTitle = view.findViewById(R.id.detail_movie_title);
        mDescription = view.findViewById(R.id.detail_movie_desc);
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

        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DescriptionFragment.this.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_add_to, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        mTitle.setText(film.getTitle());
        mDescription.setText(film.getDescription());
        mReleaseDate.setText("Data di rilascio: " + film.getReleaseDate());
        mValutation.setText("Valutazione: " + film.getValutation());

        return view;
    }
}