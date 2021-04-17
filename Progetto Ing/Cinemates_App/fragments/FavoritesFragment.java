package com.example.cinemates.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;


import com.example.cinemates.MainActivity;
import com.example.cinemates.activities.MovieDescriptorActivity;

import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FilmAdapter;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cinemates.R;

import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Film;

import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerViewToSee;
    private RecyclerView recyclerViewFavorites;
    private FilmAdapter favoritesAdapter;
    private FilmAdapter toseeAdapter;
    private FilmAdapter filmAdapter;
    private CardView buttonCasualFavorites;
    private CardView buttonCasualToSee;
    private TextView showAllFavorites;
    private TextView showAllToSee;
    private TextView title;
    private Button showItem;
    private ImageButton regenerate;
    private ImageView cover, backdrop;
    private ImageView btnClose;
    private TextView rating;
    private TextView type;
    private int id = -1;
    private Film film;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        film = new Film();

        // Load Favorites list
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFavorites = view.findViewById(R.id.list_film_favorites);
        recyclerViewFavorites.setHasFixedSize(true);
        recyclerViewFavorites.setLayoutManager(llm);
        buttonCasualFavorites = view.findViewById(R.id.CardFavorites);
        showAllFavorites = view.findViewById(R.id.showAllFavorites);
        film.loadList("Preferiti", getContext());

        // Load ToSee list
        LinearLayoutManager ll = new LinearLayoutManager(this.getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewToSee = view.findViewById(R.id.list_film_tosee);
        recyclerViewToSee.setHasFixedSize(true);
        recyclerViewToSee.setLayoutManager(ll);
        buttonCasualToSee = view.findViewById(R.id.CardToSee);
        showAllToSee = view.findViewById(R.id.showAllToSee);
        film.loadList("Da vedere", getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonCasualFavorites.setVisibility(View.GONE);
        showAllFavorites.setVisibility(View.GONE);
        film.loadList("Preferiti", getContext());

        buttonCasualToSee.setVisibility(View.GONE);
        showAllToSee.setVisibility(View.GONE);
        film.loadList("Da vedere", getContext());
    }

    private void casualFilm(RecyclerView rv, int id) {
        filmAdapter = (FilmAdapter) rv.getAdapter();
        Intent intent = new Intent(getContext(), MovieDescriptorActivity.class);
        intent.putExtra("Film", filmAdapter.getItem(id));
        getContext().startActivity(intent);
    }

    public void showGeneratedItem(int item, String listName) {
        Dialog popup = new Dialog(getContext());
        this.id = item;

        if (listName.equals("Preferiti")) {
            filmAdapter = (FilmAdapter) recyclerViewFavorites.getAdapter();
        }
        else {
            filmAdapter = (FilmAdapter) recyclerViewToSee.getAdapter();
        }

        popup.setContentView(R.layout.popup_casual_film);

        backdrop = popup.findViewById(R.id.backdrop_casual_popup);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500" + filmAdapter.getItem(item).getBackdrop()).into(backdrop);

        cover = popup.findViewById(R.id.filmCasual_img);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500" + filmAdapter.getItem(item).getCover()).into(cover);

        title = popup.findViewById(R.id.title_film_casual);
        title.setText(filmAdapter.getItem(item).getTitle());

        rating = popup.findViewById(R.id.rating_film_casual);
        rating.setText(filmAdapter.getItem(item).getValutation());

        type = popup.findViewById(R.id.type_media_casual);
        type.setText(filmAdapter.getItem(item).getType());

        regenerate = popup.findViewById(R.id.btn_regenerate);
        regenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                MainActivity.utente.generateRandomFromList(listName, filmAdapter.getItemCount());
            }
        });

        btnClose = popup.findViewById(R.id.closePopBtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        showItem = popup.findViewById(R.id.btn_showcasual);

        if (filmAdapter.getItem(item).getType().equals("Film")) {
            showItem.setText("Vai al Film");
        }
        else {
            showItem.setText("Vai alla Serie TV");
        }

        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                if (listName.equals("Preferiti")) {
                    casualFilm(recyclerViewFavorites, item);
                }
                else {
                    casualFilm(recyclerViewToSee, item);
                }
            }
        });

        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
    }

    public void setButtonVisibility(String listName, int i, Context context) {
        switch (listName) {
            case "Preferiti":
                if (i > 1) {
                    buttonCasualFavorites.setVisibility(View.VISIBLE);
                    buttonCasualFavorites.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (recyclerViewFavorites.getAdapter() != null) {
                                MainActivity.utente.generateRandomFromList(listName, recyclerViewFavorites.getAdapter().getItemCount());
                            }
                        }
                    });

                    showAllFavorites.setVisibility(View.VISIBLE);
                    showAllFavorites.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            filmAdapter = (FilmAdapter) recyclerViewFavorites.getAdapter();
                            Intent intent = new Intent(context, ResultsActivity.class);
                            intent.putExtra("type", "showall");
                            intent.putExtra("list", (Serializable) filmAdapter.getListFilm());
                            startActivity(intent);
                        }
                    });
                }

                break;

            case "Da vedere":
                if (i > 1) {
                    buttonCasualToSee.setVisibility(View.VISIBLE);
                    buttonCasualToSee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (recyclerViewToSee.getAdapter() != null) {
                                MainActivity.utente.generateRandomFromList(listName, recyclerViewToSee.getAdapter().getItemCount());
                            }
                        }
                    });

                    showAllToSee.setVisibility(View.VISIBLE);
                    showAllToSee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            filmAdapter = (FilmAdapter) recyclerViewToSee.getAdapter();
                            Intent intent = new Intent(context, ResultsActivity.class);
                            intent.putExtra("type", "showall");
                            intent.putExtra("list", (Serializable) filmAdapter.getListFilm());
                            startActivity(intent);
                        }
                    });
                }

                break;
        }
    }

    public void setEmptyFavoritesListError() {
        ArrayList<String> error = new ArrayList<String>();
        error.add("La tua lista dei Preferiti è vuota");
        ErrorAdapter errorAdapter = new ErrorAdapter(getContext(), error);
        recyclerViewFavorites.setAdapter(errorAdapter);
    }

    public void setEmptyToSeeListError() {
        ArrayList<String> error = new ArrayList<String>();
        error.add("La tua lista dei Contenuti da vedere è vuota");
        ErrorAdapter errorAdapter = new ErrorAdapter(getContext(), error);
        recyclerViewToSee.setAdapter(errorAdapter);
    }

    public RecyclerView getRecyclerViewToSee() {
        return recyclerViewToSee;
    }

    public RecyclerView getRecyclerViewFavorites() {
        return recyclerViewFavorites;
    }

    public int getIdentifier() {
        return id;
    }

    public FilmAdapter getFavoritesAdapter() {
        return favoritesAdapter;
    }

    public void setFavoritesAdapter(FilmAdapter favoritesAdapter) {
        this.favoritesAdapter = favoritesAdapter;
    }

    public FilmAdapter getToseeAdapter() {
        return toseeAdapter;
    }

    public void setToseeAdapter(FilmAdapter toseeAdapter) {
        this.toseeAdapter = toseeAdapter;
    }
}