package com.example.cinemates.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.classes.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private ArrayList<Result> results;
    private RecyclerView rv;
    private ResultsAdapter adapter;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String textToSearch = intent.getStringExtra("textSearched");

        rv = findViewById(R.id.resultContainer);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
        results = new ArrayList<>();

        parseJSON(textToSearch);

        backBtn = findViewById(R.id.backButton2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void parseJSON(String query) {
        String url = "https://api.themoviedb.org/3/search/multi?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&query="+Uri.parse(query)+"&include_adult=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String cover ="";
                                String title = "";
                                String description = "";
                                String releaseD = "";
                                String type = hit.getString("media_type");

                                switch (type) {
                                    case "movie":
                                        cover = hit.getString("poster_path");
                                        description = hit.getString("overview");
                                        releaseD = hit.getString("release_date");
                                        title = hit.getString("title");
                                        type = "Film";
                                        results.add(new Result(cover, title, description, releaseD, type));
                                        break;

                                    case "tv":
                                        cover = hit.getString("poster_path");
                                        description = hit.getString("overview");
                                        releaseD = hit.getString("first_air_date");
                                        title = hit.getString("name");
                                        type = "Serie TV";
                                        results.add(new Result(cover, title, description, releaseD, type));
                                        break;

                                    case "person":
                                        cover = hit.getString("profile_path");
                                        title = hit.getString("name");
                                        type = "Attore";

                                        results.add(new Result(cover, title, null, null, type));

                                        try {
                                            JSONArray filmsOf = hit.getJSONArray("known_for");
                                            for (int j = 0; j < filmsOf.length(); j++) {
                                                JSONObject actorFilm = filmsOf.getJSONObject(j);
                                                if (actorFilm.getString("media_type").equals("movie")) {
                                                    cover = actorFilm.getString("poster_path");
                                                    description = actorFilm.getString("overview");
                                                    releaseD = actorFilm.getString("release_date");
                                                    title = actorFilm.getString("title");
                                                    type = "Film";
                                                }
                                                else {
                                                    cover = actorFilm.getString("poster_path");
                                                    description = actorFilm.getString("overview");
                                                    releaseD = actorFilm.getString("first_air_date");
                                                    title = actorFilm.getString("name");
                                                    type = "Serie TV";
                                                }

                                                results.add(new Result(cover, title, description, releaseD, type));
                                            }
                                        } catch (JSONException f) {
                                            f.printStackTrace();
                                        }
                                        break;
                                }
                            }
                            adapter = new ResultsAdapter(ResultsActivity.this, results);
                            rv.setAdapter(adapter);

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