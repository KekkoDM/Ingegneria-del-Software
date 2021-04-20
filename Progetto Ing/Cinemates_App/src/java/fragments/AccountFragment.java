package com.example.cinemates.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.FriendsActivity;
import com.example.cinemates.activities.SettingsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    private CardView settings;
    private CardView friends;
    private CardView logout;
    private TextView accountName;
    private TextView accountUsername;
    private Dialog dialog;

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
        accountName.setText("Bentornato, " + MainActivity.utente.getNome() + "!");

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
                dialog = new Dialog(getActivity());
                showPopup(v);
            }
        });

        return view;
    }

    private void showPopup(View v) {
        TextView title;
        TextView subtitle;
        ImageView icon;
        ImageView close;
        Button btnPop;

        dialog.setContentView(R.layout.popup);

        title = dialog.findViewById(R.id.titlePopup);
        subtitle = dialog.findViewById(R.id.subtitlePopup);
        icon = dialog.findViewById(R.id.iconPop);
        close = dialog.findViewById(R.id.closePopBtn);
        btnPop = dialog.findViewById(R.id.confirmPopupBtn);

        icon.setImageResource(R.drawable.ic_logout_popup);
        title.setText("Sei sicuro?");
        subtitle.setText("Se scegli di proseguire, verrai disconnesso da Cinemates");
        btnPop.setText("Disconnetti");

        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.utente.setAutenticato(false);
                FirebaseAuth.getInstance().signOut();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        new LoginFragment()).commit();
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}