package com.example.cinemates.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FollowNotificationAdapter;
import com.example.cinemates.adapters.GeneralNotificationAdapter;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneralNotificationsFragment extends Fragment {
    private RecyclerView rvGeneral;
    private GeneralNotificationAdapter generalAdapter;
    private static GeneralNotificationsFragment instance;

    public GeneralNotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_general_notifications, container, false);

        instance = this;

        rvGeneral = v.findViewById(R.id.generalNotificationsRV);
        rvGeneral.setHasFixedSize(true);
        rvGeneral.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Notifica notifica = new Notifica();

        notifica.loadGeneralNotifications(MainActivity.utente, getContext());

        return v;
    }

    public void showGeneralNotifications(ArrayList<Notifica> notifications) {
        generalAdapter = new GeneralNotificationAdapter(getContext(), notifications);
        rvGeneral.setAdapter(generalAdapter);
    }

    public void showErrorNotifications() {
        ArrayList<String> error = new ArrayList<>();
        error.add("Non ci sono nuove notifiche da mostrare");
        ErrorAdapter errorAdapter = new ErrorAdapter(getContext(), error);
        rvGeneral.setAdapter(errorAdapter);
    }

    public void removeNotification(int position) {
        generalAdapter.removeNotification(position);
        rvGeneral.removeViewAt(position);
        generalAdapter.notifyItemRemoved(position);
        generalAdapter.notifyItemRangeChanged(position, generalAdapter.getItemCount());

        if (generalAdapter.getItemCount() == 0) {
            showErrorNotifications();
        }

        Toast.makeText(getContext(), "Notifica cancellata correttamente", Toast.LENGTH_SHORT).show();
    }

    public static GeneralNotificationsFragment getInstance() {
        return instance;
    }
}