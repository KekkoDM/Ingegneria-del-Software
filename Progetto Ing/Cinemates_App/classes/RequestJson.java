package com.example.cinemates.classes;

import android.content.Context;


import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import com.android.volley.toolbox.Volley;

import com.example.cinemates.MainActivity;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FilmAdapter;

import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.adapters.SearchSuggestionsAdapter;
import com.example.cinemates.adapters.SliderAdapter;
import com.example.cinemates.fragments.ReviewFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;



public class RequestJson<JSONParser>{


    public ArrayList getListFilm() {
        return listFilm;
    }
    private ArrayList listFilm;
    private ArrayList reviews;
    private RecyclerView.Adapter adapter;

    private RequestQueue requestQueue;

    private static final String TMDB = "https://api.themoviedb.org/3/";
    private static final String APIKEY = "?api_key=6ff4c2846a2910d753ff91a81eee4f6c";

    private static final String IT_IT = "&language=it-IT";
    private static final String EN_US = "&language=en-US";
    private static final String TRENDING_ALL = TMDB + "trending/all/week" + APIKEY + IT_IT;
    private static final String TRENDING =  TMDB + "trending/";
    private static final String SEARCH =  TMDB + "search/multi" + APIKEY + IT_IT;
    private static final String ADULT = "&include_adult=true";

    private Context context;

    public RequestJson(Context c){
        context=c;
        requestQueue = Volley.newRequestQueue(c);

    }

    //SLIDER
    public void parseJSONSlide(ViewPager2 viewPager2) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, TRENDING_ALL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmSlider(viewPager2, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    protected void getFilmSlider(ViewPager2 viewPager, JSONObject response){
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


    //SEARCH SUGGESTION
    public void parseJSONSearchSuggestion(RecyclerView recyclerView) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, TRENDING_ALL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmSearchSuggestion(recyclerView,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    protected void getFilmSearchSuggestion(RecyclerView recyclerView, JSONObject response){
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

    //RECYCLERVIEW FILM
    public void parseJSONFilm(RecyclerView recyclerView,String type) {

        String url = TRENDING + type + "/week" + APIKEY + IT_IT;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmHome(recyclerView, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    protected void getFilmHome(RecyclerView recyclerView, JSONObject response){
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


    //RICERCA
    public void parseJSONSearch(RecyclerView recyclerView, String query){

        String url = SEARCH + "&query=" + query + ADULT ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getFilmResults(recyclerView,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    protected void getFilmResults(RecyclerView recyclerView, JSONObject response){
        try {
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");
            ArrayList<String> error = new ArrayList<String>();
            error.add("La ricerca non ha prodotto risultati");

            setFMV(jsonArray);

            if (listFilm.size()>0)
                adapter = new ResultsAdapter(listFilm, context);
            else
                adapter = new ErrorAdapter(context,error);

            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //RECENSIONI
    public void parseJSONReviews(RecyclerView recyclerView,String id,String type) {

        String url = "";
        if(type.equals("Film"))
            url = TMDB + "movie/";
        else
            url = TMDB + "tv/";
        url = url + id + "/reviews" + APIKEY + EN_US ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getReviews(recyclerView,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    protected void getReviews(RecyclerView recyclerView,JSONObject response){
        try {
            reviews = new ArrayList<>();
            JSONArray jsonArray = response.getJSONArray("results");
            ArrayList<String> error = new ArrayList<String>();
            error.add("Non ci sono ancora recensioni");

            setReview(jsonArray);

            ReviewFragment reviewFragment = ReviewFragment.getInstance();

            if (reviews.size()>0) {
                adapter = new ReviewAdapter(reviews, context);
                reviewFragment.setReviewAdapter((ReviewAdapter) adapter);
            }
            else {
                adapter = new ErrorAdapter(context, error);
            }

            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //FAVORITI E DA VEDERE
    public void parseJSONSavedList(RecyclerView recyclerView, Film film){
        String type ="";
        if (film.getType().equals("Film"))
            type="movie/";
        else
            type="tv/";
        String url = TMDB + type + film.getId() + APIKEY + IT_IT;
        listFilm = new ArrayList<Film>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getMediaOnId(recyclerView,response,film.getType());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    protected void getMediaOnId(RecyclerView recyclerView, JSONObject response, String type){
        try {
            if(type.equals("Film"))
                setListFilm(response,0);
            else
                setListSeries(response,0);

            if(context.equals(MainActivity.selectedFragment.getContext()))
                adapter = new FilmAdapter(listFilm, context);
            else
                adapter = new ResultsAdapter(listFilm,context);

            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //SET PARAMETRI
    protected void setListFilm(JSONObject hit,int i) throws JSONException {

        String id = hit.getString("id");
        String cover = hit.getString("poster_path");
        String title = hit.getString("title");
        String description = hit.getString("overview");
        String valutation = hit.getString("vote_average");
        String type;
        String releaseD;
        String backdrop ;

        if(hit.isNull("media_type"))
            type="Film";
        else
            type = hit.getString("media_type");

        if (hit.isNull("backdrop_path"))
            backdrop = "";
        else
            backdrop = hit.getString("backdrop_path");

        if (hit.isNull("release_date"))
            releaseD = "Da Definire";
        else
            releaseD = hit.getString("release_date");

        listFilm.add(new Film(id,cover,backdrop, title, description, releaseD, valutation,type));

    }

    protected void setReview(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject hit = jsonArray.getJSONObject(i);
            String id = hit.getString("id");
            String username = hit.getString("author");
            String description = hit.getString("content");
            String data = hit.getString("created_at").substring(0,10);
            reviews.add(new Recensione(id,username,description,data));
        }
    }

    protected void setListSeries(JSONObject hit,int i) throws JSONException {

        String id = hit.getString("id");
        String cover = hit.getString("poster_path");
        String backdrop ;
        String description = hit.getString("overview");
        String releaseD ;
        String title = hit.getString("name");
        String valutation = hit.getString("vote_average");
        String type ;

        if(hit.isNull("media_type"))
            type="Serie TV";

        else
            type = hit.getString("media_type");


        if (hit.isNull("backdrop_path"))
            backdrop = "";
        else
            backdrop = hit.getString("backdrop_path");

        if (hit.isNull("release_date"))
            releaseD = "Da Definire";
        else
            releaseD = hit.getString("release_date");

        listFilm.add(new Film(id,cover,backdrop, title, description, releaseD, valutation,type));
    }

    protected void  setFMV(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject hit = jsonArray.getJSONObject(i);

            String type = hit.getString("media_type");

            switch (type) {
                case "movie":
                    setListFilm(hit,i);
                    break;

                case "tv":
                    setListSeries(hit,i);
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
                                setListFilm(actorFilm,j);
                            }
                            else {
                                setListSeries(actorFilm,j);
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