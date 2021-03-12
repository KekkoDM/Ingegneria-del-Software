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
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private ArrayList<Film> results;
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

        RequestJson requestJson = new RequestJson(this);
        requestJson.parseJSONSearch(rv,adapter,textToSearch);

        backBtn = findViewById(R.id.backButton2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}