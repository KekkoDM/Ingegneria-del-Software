package com.example.cinemates.fragments;

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

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.FollowNotificationAdapter;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FollowNotificationsFragment extends Fragment {
    public static RecyclerView rvFollow;
    private ArrayList<Notifica> followNotifications;
    public static FollowNotificationAdapter followAdapter;
    private ImageView icon;
    private TextView label;

    public FollowNotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_follow_notifications, container, false);

        rvFollow = v.findViewById(R.id.followNotificationsRV);
        rvFollow.setHasFixedSize(true);
        rvFollow.setLayoutManager(new LinearLayoutManager(this.getContext()));

        followNotifications = new ArrayList<>();
        icon = v.findViewById(R.id.iconNoFollowNotification);
        label = v.findViewById(R.id.noFollowLabel);

        loadFollowNotification(MainActivity.utente);

        return v;
    }

    private void loadFollowNotification(Utente utente) {
        class NotificationsLoader extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", utente.getUsername());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        JSONArray notificationJson = obj.getJSONArray("notifica");
                        for (int i = 0; i < notificationJson.length(); i++) {
                            JSONObject notification = notificationJson.getJSONObject(i);
                            Notifica notifica = new Notifica(
                                    notification.getInt("id"),
                                    notification.getString("titolo"),
                                    notification.getString("descrizione"),
                                    notification.getString("tipo"),
                                    notification.getString("mittente"),
                                    notification.getString("destinatario"),
                                    notification.getString("amministratore")
                            );
                            followNotifications.add(notifica);
                        }

                        followAdapter = new FollowNotificationAdapter(FollowNotificationsFragment.this.getContext(), followNotifications);
                        rvFollow.setAdapter(followAdapter);
                    } else {
                        icon.setImageResource(R.drawable.ic_no_notification);
                        label.setText("Non ci sono nuove richieste di collegamento");
                        rvFollow.setVisibility(View.INVISIBLE);
                        icon.setVisibility(View.VISIBLE);
                        label.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        NotificationsLoader notificationsLoader = new NotificationsLoader();
        notificationsLoader.execute();
    }
}