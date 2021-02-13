package com.example.cinemates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private List <Slide> firstslide;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderpager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);


        //prepare a list of tables
        firstslide = new ArrayList<>();
        firstslide.add(new Slide(R.drawable.slides1,"Crash bandicoot \n"));
        firstslide.add(new Slide(R.drawable.slides2,"Twilight /n"));
        firstslide.add(new Slide(R.drawable.slides3,"Transformers 3 \n Dark of the moon"));
        firstslide.add(new Slide(R.drawable.slides4,"De Luca \n"));
        SliderPagerAdapter adapter = new SliderPagerAdapter(this,firstslide);
        sliderpager.setAdapter(adapter);
        //setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderpager,true);

        //Recyclerview Setup
        //inizialize data

        List<Movie> firstMovies = new ArrayList<>();
        firstMovies.add(new Movie("GreenLand",R.drawable.film1));
        firstMovies.add(new Movie("Dolittle",R.drawable.film2));
        firstMovies.add(new Movie("Famiglia Addams",R.drawable.film3));
        firstMovies.add(new Movie("Lo Schiaccianoci",R.drawable.film4));
        firstMovies.add(new Movie("GreenLand",R.drawable.film1));
        firstMovies.add(new Movie("Dolittle",R.drawable.film2));
        firstMovies.add(new Movie("La famiglia Addams",R.drawable.film3));
        firstMovies.add(new Movie("Lo Schiaccianoci",R.drawable.film4));

        MovieAdapter movieAdapter = new MovieAdapter(this,firstMovies);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));






    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(sliderpager.getCurrentItem()<firstslide.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });
        }
    }
}