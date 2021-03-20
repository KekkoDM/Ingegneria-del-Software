package com.example.cinemates.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.cinemates.adapters.FollowNotificationAdapter;
import com.example.cinemates.adapters.GeneralNotificationAdapter;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.restapi.CinematesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneralNotificationsFragment extends Fragment {
    private RecyclerView rvGeneral;
    private ArrayList<Notifica> generalNotifications;
    private GeneralNotificationAdapter generalAdapter;
    private ImageView icon;
    private TextView label;
    private Dialog loading;

    public GeneralNotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_general_notifications, container, false);

        rvGeneral = v.findViewById(R.id.generalNotificationsRV);
        rvGeneral.setHasFixedSize(true);
        rvGeneral.setLayoutManager(new LinearLayoutManager(this.getContext()));

        generalNotifications = new ArrayList<>();
        icon = v.findViewById(R.id.iconNoGeneralNotification);
        label = v.findViewById(R.id.noGeneralLabel);

        loadGeneralNotifications(MainActivity.utente);

        return v;
    }

    private void loadGeneralNotifications(Utente utente) {
        class NotificationsLoader extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new Dialog(getActivity());
                loading.setContentView(R.layout.loading);
                loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", utente.getUsername());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.GENERAL_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        JSONArray usersJson = obj.getJSONArray("notifica");
                        for (int i = 0; i < usersJson.length(); i++) {
                            JSONObject userJson = usersJson.getJSONObject(i);
                            Notifica notifica = new Notifica(
                                    userJson.getString("titolo"),
                                    userJson.getString("descrizione"),
                                    userJson.getString("tipo")
                            );
                            generalNotifications.add(notifica);
                        }

                        generalAdapter = new GeneralNotificationAdapter(GeneralNotificationsFragment.this.getContext(), generalNotifications);
                        rvGeneral.setAdapter(generalAdapter);
                    } else {
                        icon.setImageResource(R.drawable.ic_no_notification);
                        label.setText("Non ci sono nuove notifiche");
                        rvGeneral.setVisibility(View.INVISIBLE);
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