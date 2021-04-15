package com.example.cinemates.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FollowNotificationAdapter;
import com.example.cinemates.classes.Notifica;

import java.util.ArrayList;

public class FollowNotificationsFragment extends Fragment {
    private RecyclerView rvFollow;
    private FollowNotificationAdapter followAdapter;
    private static FollowNotificationsFragment instance;

    public FollowNotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_follow_notifications, container, false);

        instance = this;

        rvFollow = v.findViewById(R.id.followNotificationsRV);
        rvFollow.setHasFixedSize(true);
        rvFollow.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Notifica notifica = new Notifica();

        notifica.loadFollowNotification(MainActivity.utente);

        return v;
    }

    public void showFollowNotifications(ArrayList<Notifica> notifications) {
        followAdapter = new FollowNotificationAdapter(getContext(), notifications);
        rvFollow.setAdapter(followAdapter);
    }

    public void showErrorNotifications() {
        ArrayList<String> error = new ArrayList<>();
        error.add("Non ci sono nuove richieste di collegamento");
        ErrorAdapter errorAdapter = new ErrorAdapter(getContext(), error);
        rvFollow.setAdapter(errorAdapter);
    }

    public void removeNotification(int position, String message) {
        followAdapter.removeNotification(position);
        rvFollow.removeViewAt(position);
        followAdapter.notifyItemRemoved(position);
        followAdapter.notifyItemRangeChanged(position, followAdapter.getItemCount());

        if (followAdapter.getItemCount() == 0) {
            showErrorNotifications();
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static FollowNotificationsFragment getInstance() {
        return instance;
    }
}