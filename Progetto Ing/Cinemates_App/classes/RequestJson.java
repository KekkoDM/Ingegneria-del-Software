package com.example.cinemates.classes;

import android.content.Context;
import android.net.Uri;



import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import com.android.volley.toolbox.Volley;
import com.example.cinemates.adapters.FilmAdapter;

import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.adapters.SearchSuggestionsAdapter;
import com.example.cinemates.adapters.SliderAdapter;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;



public class RequestJson<JSONParser>{



    private ArrayList listFilm;

    private RequestQueue requestQueue;
    private String tmdb="https://api.themoviedb.org/3/trending/";
    private String apikey="/week?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&page=1";
    private Context context;

    public RequestJson(Context c){
        context=c;
        requestQueue = Volley.newRequestQueue(c);

    }


    public void parseJSONSlide(ViewPager2 viewPager2, SliderAdapter adapter) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tmdb+"all"+apikey, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmSlider(viewPager2, adapter,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void parseJSONSearchSuggestion(RecyclerView recyclerView, SearchSuggestionsAdapter adapter) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tmdb+"all"+apikey, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmSearchSuggestion(recyclerView,adapter,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }


    public void parseJSONFilm(RecyclerView recyclerView, RecyclerView.Adapter filmAdapter,String type) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tmdb+type+apikey, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmHome(recyclerView, filmAdapter,response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void parseJSONSearch(RecyclerView recyclerView,ResultsAdapter resultsAdapter, String query){
        String url = "https://api.themoviedb.org/3/search/multi?api_key=6ff4c2846a2910d753ff91a81eee4f6c&language=it-IT&query="+query+"&include_adult=false";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        getFilmResults(recyclerView,resultsAdapter,response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }



    public void getFilmHome(RecyclerView recyclerView, RecyclerView.Adapter adapter, JSONObject response){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");

            setFMV(jsonArray);

            adapter = new FilmAdapter(listFilm,context);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getFilmSearchSuggestion(RecyclerView recyclerView, RecyclerView.Adapter adapter, JSONObject response){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");

            setFMV(jsonArray);

            adapter = new SearchSuggestionsAdapter(context, listFilm);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void getFilmSlider(ViewPager2 viewPager, SliderAdapter adapter, JSONObject response){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");

            setFMV(jsonArray);

            adapter =  new SliderAdapter(listFilm,viewPager,context);
            viewPager.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void getFilmResults(RecyclerView recyclerView, ResultsAdapter adapter, JSONObject response){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");

            setFMV(jsonArray);

            adapter = new ResultsAdapter(listFilm, context);
            recyclerView.setAdapter(adapter);
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
        String valutation = hit.getString("vote_average");
        String type = hit.getString("media_type");
        String releaseD;
        String backdrop ;

        if (hit.isNull("release_date")){
            backdrop = "";
        }else
        {
            backdrop = hit.getString("backdrop_path");
        }
        if (hit.isNull("release_date")){
            releaseD = "Da Definire";
        }else
        {
            releaseD = hit.getString("release_date");
        }

        listFilm.add(new Film(id,cover,backdrop, title, description, releaseD, valutation,type));


    }


    protected void setListSeries(JSONArray jsonArray,int i) throws JSONException {
        JSONObject hit = jsonArray.getJSONObject(i);
        String id = hit.getString("id");
        String cover = hit.getString("poster_path");
        String backdrop = hit.getString("backdrop_path");
        String description = hit.getString("overview");
        String releaseD = hit.getString("first_air_date");
        String title = hit.getString("name");
        String valutation = hit.getString("vote_average");
        String type = hit.getString("media_type");
        listFilm.add(new Film(id,cover,backdrop, title, description, releaseD, valutation,type));
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

                    listFilm.add(new Film(id,cover, null,title, null, null, null,type));

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

}

