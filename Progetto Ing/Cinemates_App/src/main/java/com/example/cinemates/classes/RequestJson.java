package com.example.cinemates.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.GenreAdapter;
import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.adapters.SliderAdapter;
import com.example.cinemates.fragments.HomeFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;

public class RequestJson<JSONParser>{



    private ArrayList listFilm;


    private RequestQueue requestQueue;
    private String tmdb="https://api.themoviedb.org/3/movie/";
    private String apikey="?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&page=1";
    private Context context;

    public RequestJson(Context c){
        context=c;
        requestQueue = Volley.newRequestQueue(c);

    }


    public void parseJSONSlide(ViewPager2 viewPager2, SliderAdapter adapter) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tmdb+"now_playing"+apikey, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        getFilmSlider(viewPager2, adapter,response,context);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
//        System.out.println("PROVA ARRAY FINE SLIDE: "+listFilm.size());
    }


    public void parseJSONFilm(RecyclerView recyclerView, FilmAdapter filmAdapter,String type) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tmdb+type+apikey, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmHome(recyclerView,filmAdapter,response,context);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });// System.out.println("PROVA ARRAY FINE FILM: "+listFilm.size());
        requestQueue.add(request);



    }


    /*private void parseJSONTv(String url, RecyclerView recyclerView) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RequestJson requestJson = new RequestJson();
                        requestJson.getFilmHome(recyclerView,  filmAdapter,response,HomeFragment.this.getContext());
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }*/

    public void getFilmHome(RecyclerView recyclerView, FilmAdapter adapter, JSONObject response, Context a){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++)
                setListFilm(jsonArray,i);

            adapter =  new FilmAdapter(listFilm, a);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getFilmGenres(RecyclerView recyclerView, GenreAdapter adapter, JSONObject response, Context a){
        try {
            listFilm = new ArrayList<Genre>();
            JSONArray jsonArray = response.getJSONArray("genres");

            setGenre(jsonArray);

            adapter = new GenreAdapter(a,listFilm);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getFilmSlider(ViewPager2 viewPager, SliderAdapter adapter, JSONObject response, Context a){
        try {
            HomeFragment.listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");

            setSlider(jsonArray);

            adapter =  new SliderAdapter(HomeFragment.listFilm,viewPager,a);
//            System.out.println("PROVA ARRAY: "+listFilm.size());

            viewPager.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected void setListFilm(JSONArray jsonArray,int i) throws JSONException {

        JSONObject hit = jsonArray.getJSONObject(i);
        String id = hit.getString("id");
        String cover = hit.getString("poster_path");
        String title = hit.getString("title");
        String description = hit.getString("overview");
        String releaseD = hit.getString("release_date");
        String vote = String.valueOf(hit.getDouble("vote_average"));
        listFilm.add(new Film(id,cover, title, description, releaseD, vote));

    }

    protected void setListSeries(JSONArray jsonArray,int i) throws JSONException {
      JSONObject hit = jsonArray.getJSONObject(i);
        String id = hit.getString("id");
      String cover = hit.getString("poster_path");
      String description = hit.getString("overview");
      String releaseD = hit.getString("first_air_date");
      String title = hit.getString("name");
      String type = "Serie TV";
      listFilm.add(new Film(id,cover, title, description, releaseD, type));
    }


    public void getFilmResults(RecyclerView recyclerView, ResultsAdapter adapter, JSONObject response, Context a){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");
            System.out.println("RESULTS ENTER: ");
            setFMV(jsonArray);

            adapter = new ResultsAdapter(listFilm, a);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected void  setFMV(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject hit = jsonArray.getJSONObject(i);

            String type = hit.getString("media_type");
            System.out.println("RESULTS : "+i);

            switch (type) {
                case "movie":
                    setListFilm(jsonArray,i);
                    break;

                case "tv":
                    setListSeries(jsonArray,i);
                    break;

                case "person":
                    String id = hit.getString("id");
                    String cover = hit.getString("profile_path");
                    String title = hit.getString("name");
                    type = "Attore";

                    listFilm.add(new Film(id,cover, title, null, null, type));

                    try {
                        JSONArray filmsOf = hit.getJSONArray("known_for");
                        for (int j = 0; j < filmsOf.length(); j++) {
                            JSONObject actorFilm = filmsOf.getJSONObject(j);
                            if (actorFilm.getString("media_type").equals("movie")) {
                                setListFilm(filmsOf,j);
                            }
                            else {
                                setListSeries(filmsOf,j);
                            }
                        }
                    } catch (JSONException f) {
                        f.printStackTrace();
                    }
                    break;
            }
        }
    }




    protected void setSlider(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject hit = jsonArray.getJSONObject(i);
            String cover = "https://image.tmdb.org/t/p/w500" + hit.getString("backdrop_path");
            String title = hit.getString("title");
            String id = hit.getString("id");
            HomeFragment.listFilm.add(new Film(id,cover, title, hit.getString("overview"), null, null));
        }
    }




    protected void setGenre(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject hit = jsonArray.getJSONObject(i);
            String gen = hit.getString("name");
            listFilm.add(new Genre(gen));
        }
    }

    public ArrayList getListFilm() {
        return listFilm;
    }

}

