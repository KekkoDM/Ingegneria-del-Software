package com.example.cinemates.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cinemates.R;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.SliderAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.RequestJson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;

    public SliderAdapter getAdapter() {
        return adapter;
    }

    private SliderAdapter adapter;
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewLatest;
    int currentSlide = 0;

    public ViewPager2 getViewPager2() {
        return viewPager2;
    }





    public static ArrayList<Film> listFilm;



    private FilmAdapter filmAdapter;


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
        RequestJson requestJson = new RequestJson(HomeFragment.this.getContext());


        LinearLayoutManager ll = new LinearLayoutManager(this.getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewFilm.setLayoutManager(ll);
        recyclerViewLatest.setLayoutManager(llm);

//      System.out.println("PROVA ARRAY PRIMA DI CHIAMARE: "+ listFilm.size());
        requestJson.parseJSONSlide(viewPager2,adapter);

        requestJson.parseJSONFilm(recyclerViewFilm,filmAdapter,"popular");
        requestJson.parseJSONFilm(recyclerViewLatest,filmAdapter,"top_rated");

        //System.out.println("PROVA ARRAY DOPO LA CHIAMATA: "+listFilm.size());



        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (currentSlide == listFilm.size()) {
                    currentSlide = 0;
                    //System.out.println("PROVA ARRAYLIST: "+listFilm.size());
                }
                else {
                    currentSlide++;

                    //System.out.println("PROVA ARRAYLIST: "+listFilm.size());
                }
                handler.postDelayed(this, 3000);
            }
        } , 1500);




        return view;
    }

    public void sliderClick(View v) {
        int currentslide;
        System.out.println("FILM "+listFilm.get(currentSlide-1).getTitle()+listFilm.get(currentSlide-1).getDescription());

    }




}