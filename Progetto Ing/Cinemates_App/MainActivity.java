package com.example.cinemates;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.AccountFragment;
import com.example.cinemates.fragments.FavoritesFragment;
import com.example.cinemates.fragments.HomeFragment;
import com.example.cinemates.fragments.LoginFragment;
import com.example.cinemates.fragments.NotificationFragment;
import com.example.cinemates.fragments.RequiredLoginFragment;
import com.example.cinemates.fragments.SearchFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    public static Utente utente = new Utente(null, null, null, null, null);
    private ChipNavigationBar bottomNav;
    private FragmentManager fragmentManager;
    private Fragment selectedFragment = null;

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
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {
                selectedFragment = null;

                switch (i) {
                    case R.id.homeItem:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.favoritesItem:
                        if (utente.isAutenticato()) {
                            selectedFragment = new FavoritesFragment();
                        }
                        else {
                            selectedFragment = new RequiredLoginFragment();
                        }
                        break;

                    case R.id.searchItem:
                        selectedFragment = new SearchFragment();
                        break;

                    case R.id.notificationItem:
                        if (utente.isAutenticato()) {
                            selectedFragment = new NotificationFragment();
                        }
                        else {
                            selectedFragment = new RequiredLoginFragment();
                        };
                        break;

                    case R.id.accountItem:
                        if (utente.isAutenticato()) {
                            selectedFragment = new AccountFragment();
                        }
                        else {
                            selectedFragment = new LoginFragment();
                        }
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });
    }
}