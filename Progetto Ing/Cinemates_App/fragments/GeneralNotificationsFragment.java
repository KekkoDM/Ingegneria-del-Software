package com.example.cinemates.fragments;

import android.app.Dialog;
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

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
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
    public static RecyclerView rvGeneral;
    private ArrayList<Notifica> generalNotifications;
    public static GeneralNotificationAdapter generalAdapter;
    private ErrorAdapter errorAdapter;
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
                            JSONObject notification = usersJson.getJSONObject(i);
                            Notifica notifica = new Notifica(
                                    notification.getInt("id"),
                                    notification.getString("titolo"),
                                    notification.getString("descrizione"),
                                    notification.getString("tipo"),
                                    notification.getString("mittente"),
                                    notification.getString("destinatario"),
                                    notification.getString("amministratore")
                            );
                            generalNotifications.add(notifica);
                        }

                        generalAdapter = new GeneralNotificationAdapter(GeneralNotificationsFragment.this.getContext(), generalNotifications);
                        rvGeneral.setAdapter(generalAdapter);
                    } else {
                        ArrayList<String> error = new ArrayList<String>();
                        error.add("Non ci sono nuove notifiche da mostrare");
                        errorAdapter = new ErrorAdapter(getContext(),error);
                        rvGeneral.setAdapter(errorAdapter);
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