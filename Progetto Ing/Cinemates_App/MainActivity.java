package com.example.cinemates;

import android.os.Bundle;
import android.widget.Toast;

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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    public static Utente utente = new Utente(null, null, null, null, null);
    private ChipNavigationBar bottomNav;
    private FragmentManager fragmentManager;

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