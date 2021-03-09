package com.example.cinemates.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.FriendsActivity;
import com.example.cinemates.activities.SettingsActivity;

public class AccountFragment extends Fragment {
    private CardView settings;
    private CardView friends;
    private CardView logout;
    private TextView accountName;
    private TextView accountUsername;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        settings = view.findViewById(R.id.settingsBtn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        accountName = view.findViewById(R.id.nameProfile);
        accountName.setText("Ciao, " + MainActivity.utente.getNome() + " " + MainActivity.utente.getCognome());

        accountUsername = view.findViewById(R.id.usernameProfile);
        accountUsername.setText("@" + MainActivity.utente.getUsername());

        friends = view.findViewById(R.id.friendsBtn);
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                startActivity(intent);
            }
        });

        logout = view.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.utente.setAutenticato(false);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        new LoginFragment()).commit();
            }
        });

        return view;
    }
}