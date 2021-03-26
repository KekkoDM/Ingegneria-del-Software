package com.example.cinemates.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.cinemates.MainActivity;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FilmAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cinemates.R;
import com.example.cinemates.adapters.GeneralNotificationAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerViewToSee;
    private RecyclerView recyclerViewFavorites;
    private FilmAdapter filmAdapter;
    private RequestJson requestJson;
    private Button buttonCasual;
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

        buttonCasual = view.findViewById(R.id.btn_casual);
        buttonCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewFavorites.getAdapter()!=null)
                    casualFilm();
            }
        });

        listItem = new ArrayList<>();
        requestJson = new RequestJson(getContext());

        loadList(recyclerViewFavorites, CinematesDB.LIST_FAVORITES_URL);
        loadList(recyclerViewToSee, CinematesDB.LIST_TO_SEE_URL);

        return view;
    }

    private void casualFilm(){
        Random random = new Random();
        int nextId;

        do {                       //DUALMENTE CON ALTRA RECYCLERVIEW
            nextId = random.nextInt(recyclerViewFavorites.getAdapter().getItemCount());
        }while(id == nextId);

        id = nextId;
        filmAdapter = (FilmAdapter) recyclerViewFavorites.getAdapter();

        //DA CAMBIARE CON POPUP
        Intent intent = new Intent(getContext(), MovieDescriptorActivity.class);
        intent.putExtra("Film",filmAdapter.getItem(id));
        getContext().startActivity(intent);
    }

    private void loadList(RecyclerView recyclerView, String url) {
        class ListLoader extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
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

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        JSONArray list = obj.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
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
                            requestJson.parseJSONSavedList(recyclerView, filmAdapter, item);
                        }
                    } else {
                        ArrayList<String> error = new ArrayList<String>();
                        error.add("La tua lista non ha ancora nessun elemento!");
                        ErrorAdapter adapter = new ErrorAdapter(getContext(),error);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ListLoader listLoader = new ListLoader();
        listLoader.execute();
    }
}