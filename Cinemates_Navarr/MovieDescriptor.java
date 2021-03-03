package com.example.cinemates.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import com.example.cinemates.R;
import com.example.cinemates.adapters.PageAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.fragments.HomeFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MovieDescriptor extends AppCompatActivity {
    private TabLayout tabDescriptorFilm;
    private ViewPager viewPager;
    private TabItem descrizione,recensione;
    public PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_descriptor);

        Intent intent = getIntent();
        Film film = (Film)intent.getSerializableExtra("Film");
        System.out.println("TIECCT O FILM"+film);

        tabDescriptorFilm=(TabLayout) findViewById(R.id.Tab_DescriptorFilm);
        descrizione=(TabItem) findViewById(R.id.DescriptorFilm);
        recensione=(TabItem) findViewById(R.id.ReviewFilm);
        viewPager=findViewById(R.id.viewPager);
        pagerAdapter=new PageAdapter(getSupportFragmentManager(),tabDescriptorFilm.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabDescriptorFilm.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()== 0) {
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()== 1){
                        pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabDescriptorFilm));

    }
}