package com.example.cinemates.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.PageAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.fragments.DescriptionFragment;
import com.example.cinemates.fragments.HomeFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MovieDescriptorActivity extends AppCompatActivity {
    private TabLayout tabDescriptorFilm;
    private ViewPager viewPager;
    private ImageButton backButton;
    private FirebaseAnalytics mFirebaseAnalytics;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_descriptor);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent intent = getIntent();
        film = (Film) intent.getSerializableExtra("Film");

        // [START custom_event]
        Bundle params = new Bundle();
        params.putString("media_id", film.getId());
        params.putString("title", film.getTitle());
        params.putString("type", film.getType());
        params.putString("user", MainActivity.utente.getUsername());
        mFirebaseAnalytics.logEvent("Description_Film", params);

        backButton = findViewById(R.id.backButtonDescr);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabDescriptorFilm = (TabLayout)findViewById(R.id.Tab_DescriptorFilm);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),tabDescriptorFilm.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabDescriptorFilm));

        tabDescriptorFilm.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}