package com.example.cinemates.fragments;

import android.os.Bundle;
import com.example.cinemates.adapters.FilmAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cinemates.R;
import com.example.cinemates.classes.Film;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerViewToSee;
    private RecyclerView recyclerViewFavorites;
    private FilmAdapter filmAdapter;
    public static ArrayList<Film> listFilm;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerViewToSee = view.findViewById(R.id.list_film_tosee);
        recyclerViewToSee.setHasFixedSize(true);
        recyclerViewFavorites = view.findViewById(R.id.list_film_favorites);
        recyclerViewFavorites.setHasFixedSize(true);

        LinearLayoutManager ll = new LinearLayoutManager(this.getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewToSee.setLayoutManager(ll);
        recyclerViewFavorites.setLayoutManager(llm);

        return view;
    }
}