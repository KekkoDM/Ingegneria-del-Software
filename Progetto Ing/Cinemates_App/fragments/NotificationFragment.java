package com.example.cinemates.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.FriendsActivity;
import com.example.cinemates.adapters.FriendsAdapter;
import com.example.cinemates.adapters.NotificationAdapter;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.restapi.CinematesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationFragment extends Fragment {
    private ArrayList<Notifica> notifications;
    private RecyclerView rv;
    private NotificationAdapter adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        rv = v.findViewById(R.id.notificationsRecyclerViev);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        notifications = new ArrayList<>();
        
        loadNotification(MainActivity.utente);

        return v;
    }

    public void loadNotification(Utente utente) {
        class NotificationsLoader extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(NotificationFragment.this.getContext());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tCarico le notifiche...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", utente.getUsername());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        //getting the list friends from the response
                        JSONArray usersJson = obj.getJSONArray("notifica");
                        for (int i = 0; i < usersJson.length(); i++) {
                            JSONObject userJson = usersJson.getJSONObject(i);
                            Notifica notifica = new Notifica(
                                    userJson.getString("titolo"),
                                    userJson.getString("descrizione"),
                                    userJson.getString("tipo")
                            );

                            notifications.add(notifica);
                        }
                        adapter = new NotificationAdapter(NotificationFragment.this.getContext(), notifications);
                        rv.setAdapter(adapter);
                    } else {
                        Toast.makeText(NotificationFragment.this.getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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