package com.example.cinemates.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;


import com.example.cinemates.MainActivity;
import com.example.cinemates.activities.MovieDescriptorActivity;

import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FilmAdapter;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.cinemates.R;

import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Film;

import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FavoritesFragment extends Fragment {
    public static RecyclerView recyclerViewToSee;
    public static RecyclerView recyclerViewFavorites;
    private FilmAdapter filmAdapter;
    private CardView buttonCasualFavorites;
    private CardView buttonCasualToSee;
    private TextView showAllFavorites;
    private TextView showAllToSee;
    int id = -1;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Load Favorites list
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFavorites = view.findViewById(R.id.list_film_favorites);
        recyclerViewFavorites.setHasFixedSize(true);
        recyclerViewFavorites.setLayoutManager(llm);

        buttonCasualFavorites = view.findViewById(R.id.CardFavorites);
        showAllFavorites = view.findViewById(R.id.showAllFavorites);

        Film.loadList("Preferiti", getContext());


        // Load ToSee list
        LinearLayoutManager ll = new LinearLayoutManager(this.getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewToSee = view.findViewById(R.id.list_film_tosee);
        recyclerViewToSee.setHasFixedSize(true);
        recyclerViewToSee.setLayoutManager(ll);

        buttonCasualToSee = view.findViewById(R.id.CardToSee);
        showAllToSee = view.findViewById(R.id.showAllToSee);

        Film.loadList("Da vedere", getContext());

        return view;
    }

    private void casualFilm(RecyclerView rv) {
        Random random = new Random();
        int nextId = 0;

        filmAdapter = (FilmAdapter) rv.getAdapter();

        do {
            nextId = random.nextInt(rv.getAdapter().getItemCount());
        }while(id == nextId);

        id = nextId;

        //DA CAMBIARE CON POPUP
        Intent intent = new Intent(getContext(), MovieDescriptorActivity.class);
        intent.putExtra("Film", filmAdapter.getItem(id));
        getContext().startActivity(intent);
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
                                casualFilm(recyclerViewFavorites);
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
                                casualFilm(recyclerViewToSee);
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
}