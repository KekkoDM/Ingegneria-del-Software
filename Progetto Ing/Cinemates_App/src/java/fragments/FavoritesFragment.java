package com.example.cinemates.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import com.example.cinemates.MainActivity;
import com.example.cinemates.activities.MediaDescriptorActivity;

import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.MediaAdapter;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cinemates.R;

import com.example.cinemates.classes.Media;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerViewToSee;
    private RecyclerView recyclerViewFavorites;
    private MediaAdapter mediaAdapter;
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
    private Media media;
    private String listName;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        media = new Media();

        // Favorites list
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFavorites = view.findViewById(R.id.list_film_favorites);
        recyclerViewFavorites.setHasFixedSize(true);
        recyclerViewFavorites.setLayoutManager(llm);
        buttonCasualFavorites = view.findViewById(R.id.CardFavorites);
        showAllFavorites = view.findViewById(R.id.showAllFavorites);

        // ToSee list
        LinearLayoutManager ll = new LinearLayoutManager(this.getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewToSee = view.findViewById(R.id.list_film_tosee);
        recyclerViewToSee.setHasFixedSize(true);
        recyclerViewToSee.setLayoutManager(ll);
        buttonCasualToSee = view.findViewById(R.id.CardToSee);
        showAllToSee = view.findViewById(R.id.showAllToSee);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonCasualFavorites.setVisibility(View.GONE);
        showAllFavorites.setVisibility(View.GONE);
        media.loadList("Preferiti", getContext());

        buttonCasualToSee.setVisibility(View.GONE);
        showAllToSee.setVisibility(View.GONE);
        media.loadList("Da vedere", getContext());
    }

    private void casualFilm(RecyclerView rv, int id) {
        mediaAdapter = (MediaAdapter) rv.getAdapter();
        Intent intent = new Intent(getContext(), MediaDescriptorActivity.class);
        intent.putExtra("Film", mediaAdapter.getItem(id));
        getContext().startActivity(intent);
    }

    public void showGeneratedItem(String listName) {
        this.listName = listName;

        if (listName.equals("Preferiti")) {
            mediaAdapter = (MediaAdapter) recyclerViewFavorites.getAdapter();
        }
        else {
            mediaAdapter = (MediaAdapter) recyclerViewToSee.getAdapter();
        }

        id = MainActivity.utente.generateRandomFromList(id ,mediaAdapter.getItemCount());

        setPopUp(id);
    }

    private void setPopUp(int id){
        Dialog popup = new Dialog(getContext());
        popup.setContentView(R.layout.popup_casual_film);

        backdrop = popup.findViewById(R.id.backdrop_casual_popup);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500" + mediaAdapter.getItem(id).getBackdrop()).into(backdrop);

        cover = popup.findViewById(R.id.filmCasual_img);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500" + mediaAdapter.getItem(id).getCover()).into(cover);

        title = popup.findViewById(R.id.title_film_casual);
        title.setText(mediaAdapter.getItem(id).getTitle());

        rating = popup.findViewById(R.id.rating_film_casual);
        rating.setText(mediaAdapter.getItem(id).getValutation());

        type = popup.findViewById(R.id.type_media_casual);
        type.setText(mediaAdapter.getItem(id).getType());

        regenerate = popup.findViewById(R.id.btn_regenerate);
        regenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                showGeneratedItem(listName);
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

        if (mediaAdapter.getItem(id).getType().equals("Film")) {
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
                    casualFilm(recyclerViewFavorites, id);
                }
                else {
                    casualFilm(recyclerViewToSee, id);
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
                                showGeneratedItem(listName);
                            }
                        }
                    });

                    showAllFavorites.setVisibility(View.VISIBLE);
                    showAllFavorites.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mediaAdapter = (MediaAdapter) recyclerViewFavorites.getAdapter();
                            Intent intent = new Intent(context, ResultsActivity.class);
                            intent.putExtra("type", "showall");
                            intent.putExtra("name", "preferiti");
                            intent.putExtra("list", (Serializable) mediaAdapter.getListMedia());
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
                                showGeneratedItem(listName);
                            }
                        }
                    });

                    showAllToSee.setVisibility(View.VISIBLE);
                    showAllToSee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mediaAdapter = (MediaAdapter) recyclerViewToSee.getAdapter();
                            Intent intent = new Intent(context, ResultsActivity.class);
                            intent.putExtra("type", "showall");
                            intent.putExtra("name", "davedere");
                            intent.putExtra("list", (Serializable) mediaAdapter.getListMedia());
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

    public int getCurrentId() {
        return id;
    }
}