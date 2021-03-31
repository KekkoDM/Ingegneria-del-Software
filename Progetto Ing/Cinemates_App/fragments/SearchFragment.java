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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinemates.R;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.SearchSuggestionsAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.RequestJson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private EditText query;
    private Button searchButton;
    private RecyclerView rv;
    private SearchSuggestionsAdapter adapter;


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

        RequestJson requestJson = new RequestJson(this.getContext());
        requestJson.parseJSONSearchSuggestion(rv,adapter);

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
                    intent.putExtra("type", "film");
                    intent.putExtra("textsearched", query.getText().toString());
                    startActivity(intent);
                }
            }
        });

        return view;
    }


}