package com.example.cinemates.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinemates.R;
import com.example.cinemates.adapters.SliderAdapter;
import com.example.cinemates.classes.Slide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Slide> sliderItems;
    private ViewPager2 viewPager2;
    private SliderAdapter adapter;
    private RequestQueue requestQueue;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = view.findViewById(R.id.viewPagerSlide);
        sliderItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this.getContext());

        parseJSON();

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

    private void parseJSON() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=d6f6fde62b39251f66a180f2c13ac19f&language=it-IT&page=1";
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
}