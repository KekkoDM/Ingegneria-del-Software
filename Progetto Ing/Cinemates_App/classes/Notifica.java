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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(String amministratore) {
        this.amministratore = amministratore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static void loadGeneralNotifications(Utente utente, Context context) {
        class GeneralLoader extends AsyncTask<Void, Void, String> {
            Dialog loading = new Dialog(context);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
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

                        setGeneralNotifications(generalNot, context);
                    }
                    else {
                        ArrayList<String> error = new ArrayList<>();
                        error.add("Non ci sono nuove notifiche da mostrare");
                        setErrorNotification(error, "Generale", context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        GeneralLoader loader = new GeneralLoader();
        loader.execute();
    }

    public static void loadFollowNotification(Utente utente, Context context) {
        class FollowLoader extends AsyncTask<Void, Void, String> {

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

                        setFollowNotifications(followNot, context);
                    }
                    else {
                        ArrayList<String> error = new ArrayList<>();
                        error.add("Non ci sono nuove richieste di collegamento");
                        setErrorNotification(error, "Collegamento", context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        FollowLoader loader = new FollowLoader();
        loader.execute();
    }

    private static void setErrorNotification(ArrayList<String> error, String type, Context context) {
        if (type.equals("Generale")) {
            ErrorAdapter errorAdapter = new ErrorAdapter(context, error);
            GeneralNotificationsFragment.rvGeneral.setAdapter(errorAdapter);
        }
        else {
            ErrorAdapter errorAdapter = new ErrorAdapter(context, error);
            FollowNotificationsFragment.rvFollow.setAdapter(errorAdapter);
        }
    }

    private static void setFollowNotifications(ArrayList<Notifica> notifications, Context context) {
        FollowNotificationsFragment.followAdapter = new FollowNotificationAdapter(context, notifications);
        FollowNotificationsFragment.rvFollow.setAdapter(FollowNotificationsFragment.followAdapter);
    }

    private static void setGeneralNotifications(ArrayList<Notifica> notifications, Context context) {
        GeneralNotificationsFragment.generalAdapter = new GeneralNotificationAdapter(context, notifications);
        GeneralNotificationsFragment.rvGeneral.setAdapter(GeneralNotificationsFragment.generalAdapter);
    }

    public void acceptFollowNotification(Notifica notification, int position, Context context) {
        class AcceptFollowNotification extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

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
                params.put("sender", notification.getMittente());
                params.put("receiver", notification.getDestinatario());
                params.put("idnotification", String.valueOf(notification.getId()));

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.ACCEPT_FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        FollowNotificationsFragment.followAdapter.removeNotification(position);

                        // [START custom_event]
                        Bundle params = new Bundle();
                        params.putString("receiver",notification.getDestinatario());
                        params.putString("sender", notification.getMittente());
                        mFirebaseAnalytics.logEvent("Accepted_Follow_Notification", params);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        AcceptFollowNotification acceptFollowNotification = new AcceptFollowNotification();
        acceptFollowNotification.execute();
    }

    public void deleteNotification(Notifica notification, int position, String type, Context context) {
        class DeleteNotification extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

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
                params.put("idnotification", String.valueOf(notification.getId()));

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.REJECT_FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        if (type.equals("Generale")) {
                            GeneralNotificationsFragment.generalAdapter.removeNotification(position);
                            Toast.makeText(context, "Notifica cancellata correttamente", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            FollowNotificationsFragment.followAdapter.removeNotification(position);
                            Toast.makeText(context, "Richiesta rifiutata correttamente", Toast.LENGTH_SHORT).show();
                        }

                        // [START custom_event]
                        Bundle params = new Bundle();
                        params.putString("receiver",notification.getDestinatario());
                        params.putString("sender", notification.getMittente());
                        mFirebaseAnalytics.logEvent("Delete_Notification", params);
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        DeleteNotification deleteNotification = new DeleteNotification();
        deleteNotification.execute();
    }
}
