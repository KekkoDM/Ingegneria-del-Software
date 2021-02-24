package com.example.cinemates.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinemates.R;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.SearchSuggestionsAdapter;
import com.example.cinemates.classes.Film;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private EditText query;
    private Button searchButton;
    private RecyclerView rv;
    private SearchSuggestionsAdapter adapter;
    private ArrayList<Film> list;
    private RequestQueue requestQueue;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rv = view.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        list = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this.getContext());
        parseJSON();

        query = view.findViewById(R.id.textSearched);
        searchButton = view.findViewById(R.id.searchBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (query.getText().length() == 0) {
                    query.setHintTextColor(getResources().getColor(R.color.google_color));
                }
                else {
                    query.setHintTextColor(getResources().getColor(R.color.light_grey));
                    Intent intent = new Intent(getActivity(), ResultsActivity.class);
                    intent.putExtra("textSearched", query.getText().toString());
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void parseJSON() {
        String url = "https://api.themoviedb.org/3/trending/all/day?api_key=d6f6fde62b39251f66a180f2c13ac19f";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String title = "";
                                String type = hit.getString("media_type");

                                switch (type) {
                                    case "movie":
                                        title = hit.getString("title");
                                        break;

                                    case "tv":
                                        title = hit.getString("name");
                                        break;
                                }

                                String cover = hit.getString("backdrop_path");
                                list.add(new Film(cover, title,null,null,null));
                            }
                            adapter = new SearchSuggestionsAdapter(SearchFragment.this.getContext(), list);
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