package com.example.cinemates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import main_fragments.FavoritesFragment;
import main_fragments.HomeFragment;
import main_fragments.LoginFragment;
import main_fragments.NotificationFragment;
import main_fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setItemSelected(R.id.homeItem, true);
        fragmentManager = getSupportFragmentManager();

        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,
                homeFragment).commit();

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment selectedFragment = null;

                switch (i) {
                    case R.id.homeItem:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.favoritesItem:
                        selectedFragment = new FavoritesFragment();
                        break;

                    case R.id.searchItem:
                        selectedFragment = new SearchFragment();
                        break;

                    case R.id.notificationItem:
                        selectedFragment = new NotificationFragment();
                        break;

                    case R.id.accountItem:
                        selectedFragment = new LoginFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });
    }
}