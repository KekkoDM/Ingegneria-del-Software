package com.example.cinemates.classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FollowNotificationAdapter;
import com.example.cinemates.adapters.GeneralNotificationAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.fragments.GeneralNotificationsFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Notifica {
    private int id;
    private String titolo;
    private String descrizione;
    private String tipo;
    private String mittente;
    private String destinatario;
    private String amministratore;

    public Notifica(int id, String titolo, String descrizione, String tipo, String mittente, String destinatario, String amministratore) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.amministratore = amministratore;
    }

    public Notifica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMittente() {
        return mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void loadGeneralNotifications(Utente utente, Context context) {
        class GeneralLoader extends AsyncTask<Void, Void, String> {
            Dialog loading = new Dialog(context);
            GeneralNotificationsFragment generalNotificationsFragment = GeneralNotificationsFragment.getInstance();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading.setContentView(R.layout.loading);
                loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loading.setCancelable(false);
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
                        ArrayList<Notifica> generalNot = new ArrayList<>();
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
                            generalNot.add(notifica);
                        }

                        generalNotificationsFragment.showGeneralNotifications(generalNot);
                    }
                    else {
                        generalNotificationsFragment.showErrorNotifications();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        GeneralLoader loader = new GeneralLoader();
        loader.execute();
    }

    public void loadFollowNotifications(Utente utente) {
        class FollowLoader extends AsyncTask<Void, Void, String> {
            FollowNotificationsFragment followNotificationsFragment = FollowNotificationsFragment.getInstance();

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
                        ArrayList<Notifica> followNot = new ArrayList<>();
                        for (int i = 0; i < notificationJson.length(); i++) {
                            JSONObject notification = notificationJson.getJSONObject(i);
                            Notifica notifica = new Notifica(
                                    notification.getInt("id"),
                                    notification.getString("titolo"),
                                    notification.getString("descrizione"),
                                    notification.getString("tipo"),
                                    notification.getString("mittente"),
                                    notification.getString("destinatario"),
                                    null
                            );
                            followNot.add(notifica);
                        }

                        followNotificationsFragment.showFollowNotifications(followNot);
                    }
                    else {
                        followNotificationsFragment.showErrorNotifications();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        FollowLoader loader = new FollowLoader();
        loader.execute();
    }
}
