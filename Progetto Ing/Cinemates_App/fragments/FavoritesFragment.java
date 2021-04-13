package com.example.cinemates.fragments;


import android.app.ProgressDialog;
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
    private FilmAdapter filmAdapter;
    private ErrorAdapter errorAdapter;
    private RequestJson requestJson;
    private CardView buttonCasualFavorites;
    private CardView buttonCasualToSee;
    private TextView showAllFavorites;
    private TextView showAllToSee;
    private ArrayList<Film> listItem;
    int id = -1;

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

        buttonCasualFavorites = view.findViewById(R.id.CardFavorites);
        buttonCasualFavorites.setVisibility(View.GONE);

        buttonCasualFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewFavorites.getAdapter() != null){
                    casualFilm(recyclerViewFavorites);
                }
            }
        });

        buttonCasualToSee = view.findViewById(R.id.CardToSee);
        buttonCasualToSee.setVisibility(View.GONE);

        buttonCasualToSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewToSee.getAdapter() != null){
                    casualFilm(recyclerViewToSee);
                }
            }
        });

        loadList(recyclerViewFavorites, CinematesDB.LIST_FAVORITES_URL, buttonCasualFavorites, "Preferiti");
        loadList(recyclerViewToSee, CinematesDB.LIST_TO_SEE_URL, buttonCasualToSee, "Da vedere");

        showAllFavorites = view.findViewById(R.id.showAllFavorites);
        showAllFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmAdapter = (FilmAdapter) recyclerViewFavorites.getAdapter();
                Intent intent = new Intent(getContext(), ResultsActivity.class);
                intent.putExtra("type", "showall");
                intent.putExtra("list", (Serializable) filmAdapter.getListFilm());
                startActivity(intent);
            }
        });

        showAllToSee = view.findViewById(R.id.showAllToSee);
        showAllToSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmAdapter = (FilmAdapter) recyclerViewToSee.getAdapter();
                Intent intent = new Intent(getContext(), ResultsActivity.class);
                intent.putExtra("type", "showall");
                intent.putExtra("list", (Serializable) (Serializable) filmAdapter.getListFilm());
                startActivity(intent);
            }
        });

        return view;
    }

    private void casualFilm(RecyclerView rv){
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

    private void setButtonCasual(CardView casual, int i){
        if (i >1){
            casual.setVisibility(View.VISIBLE);
        }
    }

    private void loadList(RecyclerView recyclerView, String url, CardView casual, String listName) {
        class ListLoader extends AsyncTask<Void, Void, String> {

            ProgressDialog pdLoading = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tRecupero liste...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());

                //returing the response
                return requestHandler.sendPostRequest(url, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                int i = 0;
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        JSONArray list = obj.getJSONArray("list");
                        listItem = new ArrayList<>();
                        requestJson = new RequestJson(getContext());
                        for (i = 0; i < list.length(); i++) {
                            JSONObject film = list.getJSONObject(i);
                            Film item = new Film(
                                    film.getString("id"),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    film.getString("type")
                            );
                            listItem.add(item);
                            requestJson.parseJSONSavedList(recyclerView, item);
                        }
                    } else {
                        ArrayList<String> error = new ArrayList<String>();

                        if (listName.equals("Preferiti")) {
                            error.add("La tua lista dei Preferiti è vuota");
                        }
                        else {
                            error.add("La tua lista dei Contenuti da vedere è vuota");
                        }

                        errorAdapter = new ErrorAdapter(getContext(),error);
                        recyclerView.setAdapter(errorAdapter);
                    }

                    setButtonCasual(casual, i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ListLoader listLoader = new ListLoader();
        listLoader.execute();
    }
}