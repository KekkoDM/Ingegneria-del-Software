package com.example.cinemates.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Adapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.GenreAdapter;
import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.adapters.SliderAdapter;
import com.example.cinemates.fragments.HomeFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class RequestJson<JSONParser> {

    private ArrayList listFilm;

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
            listFilm = new ArrayList<Film>();
            JSONArray jsonArray = response.getJSONArray("results");


            setSlider(jsonArray);

            adapter =  new SliderAdapter(listFilm,viewPager,a);
            viewPager.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected void setListFilm(JSONArray jsonArray,int i) throws JSONException {

        JSONObject hit = jsonArray.getJSONObject(i);
        String cover = hit.getString("poster_path");
        String title = hit.getString("title");
        String description = hit.getString("overview");
        String releaseD = hit.getString("release_date");
        String vote = String.valueOf(hit.getDouble("vote_average"));
        listFilm.add(new Film(cover, title, description, releaseD, vote));

    }

    protected void setListSeries(JSONArray jsonArray,int i) throws JSONException {
      JSONObject hit = jsonArray.getJSONObject(i);
      String cover = hit.getString("poster_path");
      String description = hit.getString("overview");
      String releaseD = hit.getString("first_air_date");
      String title = hit.getString("name");
      String type = "Serie TV";
      listFilm.add(new Film(cover, title, description, releaseD, type));
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
                    String cover = hit.getString("profile_path");
                    String title = hit.getString("name");
                    type = "Attore";

                    listFilm.add(new Film(cover, title, null, null, type));

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
            listFilm.add(new Film(cover, title, null, null, null));
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

