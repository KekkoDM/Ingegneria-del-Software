package com.example.cinemates.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinemates.R;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.SliderAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Slide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Slide> sliderItems;
    private ViewPager2 viewPager2;
    private SliderAdapter adapter;
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewLatest;
    private ArrayList<Film> listFilm;

    private FilmAdapter filmAdapter;
    private FilmAdapter filmAdapter1;
    private RequestQueue requestQueue;
    private String url = "https://api.themoviedb.org/3/movie/";
    private String apiKey ="?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&page=1";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = view.findViewById(R.id.viewPagerSlide);
        recyclerViewFilm = view.findViewById(R.id.list_film);
        recyclerViewFilm.setHasFixedSize(true);
        recyclerViewLatest = view.findViewById(R.id.latest_film);
        recyclerViewLatest.setHasFixedSize(true);


        LinearLayoutManager ll = new LinearLayoutManager(this.getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewFilm.setLayoutManager(ll);
        recyclerViewLatest.setLayoutManager(llm);
        listFilm = new ArrayList<>();
        sliderItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this.getContext());

        parseJSON(url+"popular"+apiKey);
        parseJSONFilm(url+"popular"+apiKey,recyclerViewFilm);
        parseJSONFilm(url+"now_playing"+apiKey,recyclerViewLatest);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            int currentSlide = 0;
            @Override
            public void run() {
                viewPager2.setCurrentItem(currentSlide);

                if (currentSlide == sliderItems.size()) {
                    currentSlide = 0;
                }
                else {
                    currentSlide++;
                }
                handler.postDelayed(this, 5000);
            }
        } , 3000);

        return view;
    }

    private void parseJSON(String url) {
        //String url = "https://api.themoviedb.org/3/movie/popular?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String movieTitle = hit.getString("title");
                                String coverPath = "https://image.tmdb.org/t/p/w500" + hit.getString("backdrop_path");
                                sliderItems.add(new Slide(movieTitle, coverPath));
                            }
                            adapter = new SliderAdapter(sliderItems, viewPager2, getContext());
                            viewPager2.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }


    private void parseJSONFilm(String url, RecyclerView recyclerView) {
        //String url = "https://api.themoviedb.org/3/movie/popular?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listFilm = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String movieTitle = hit.getString("title");
                                String coverPath = "https://image.tmdb.org/t/p/w500" + hit.getString("poster_path");

                                listFilm.add(new Film(movieTitle, coverPath));
                            }
                            filmAdapter = new FilmAdapter(listFilm,HomeFragment.this.getContext());
                            recyclerView.setAdapter(filmAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void parseJSONFilmLatest() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=6ff4c2846a2910d753ff91a81eee4f6c&language=it-IT&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listFilm = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String movieTitle = hit.getString("title");
                                String coverPath = "https://image.tmdb.org/t/p/w500" + hit.getString("poster_path");

                                listFilm.add(new Film(movieTitle, coverPath));
                            }
                            filmAdapter1 = new FilmAdapter(listFilm,HomeFragment.this.getContext());
                            recyclerViewLatest.setAdapter(filmAdapter1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}