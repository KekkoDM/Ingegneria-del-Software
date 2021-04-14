package com.example.cinemates.classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.fragments.AccountFragment;
import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.fragments.GeneralNotificationsFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class Utente implements Serializable {
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean autenticato = false;

    public Utente(String username, String nome, String cognome, String email, String password, boolean autenticato) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.autenticato = autenticato;
    }

    public Utente(String username, String nome, String cognome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutenticato() {
        return autenticato;
    }

    public void setAutenticato(boolean autenticato) {
        this.autenticato = autenticato;
    }

    public void loginUser(String username, String password, FragmentActivity context) {
        class UserLogin extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            ProgressDialog pdLoading = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tAccesso in corso...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.LOGIN_URL, params);
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
                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("utente");
                        Utente utente = new Utente(
                                userJson.getString("username"),
                                userJson.getString("nome"),
                                userJson.getString("cognome"),
                                userJson.getString("email"),
                                userJson.getString("password")
                        );

                        //setting the logged user
                        MainActivity.utente = utente;
                        MainActivity.utente.setAutenticato(true);

                        // [START custom_event]
                        Bundle params = new Bundle();
                        params.putString("user", MainActivity.utente.getUsername());
                        mFirebaseAnalytics.logEvent("Login_Cinemates", params);

                        //starting the profile page
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        UserLogin login = new UserLogin();
        login.execute();
    }

    public void registerUser(String name, String surname, String username, String email, String password, Context context) {
        class RegisterUser extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            ProgressDialog pdLoading = new ProgressDialog(context);

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("nome", name);
                params.put("cognome", surname);
                params.put("email", email);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.REGISTER_URL, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this method will be running on UI thread
                pdLoading.setMessage("\tRegistrazione in corso...");
                pdLoading.setCancelable(false);
                pdLoading.show();
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
                        JSONObject userJson = obj.getJSONObject("utente");
                        Utente utente = new Utente(
                                userJson.getString("username"),
                                userJson.getString("nome"),
                                userJson.getString("cognome"),
                                userJson.getString("email"),
                                userJson.getString("password")
                        );


                        //setting the logged user
                        MainActivity.utente = utente;
                        MainActivity.utente.setAutenticato(true);

                        Bundle params = new Bundle();
                        params.putString("user", MainActivity.utente.getUsername());
                        params.putString("email", MainActivity.utente.getEmail());
                        mFirebaseAnalytics.logEvent("User_Created", params);

                        //starting the home page
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Ops! Qualcosa è andato storto", Toast.LENGTH_LONG).show();
                }
            }
        }

        RegisterUser register = new RegisterUser();
        register.execute();
    }

    public void recoveryPassword(String email, Context context) {
        class RecoveryPassword extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            ProgressDialog pdLoading = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tInvio e-mail di recupero...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.RECOVERY_PASSWORD, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Bundle params = new Bundle();
                        params.putString("user", MainActivity.utente.getUsername());
                        params.putString("email", MainActivity.utente.getEmail());
                        mFirebaseAnalytics.logEvent("Recovery_Password", params);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RecoveryPassword recoveryPassword = new RecoveryPassword();
        recoveryPassword.execute();
    }

    public void deleteAccount(Utente utente, Context context) {
        class AccountDeleter extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tEliminazione account in corso...");
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
                return requestHandler.sendPostRequest(CinematesDB.DELETE_URL, params);
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
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //logout current user
                        MainActivity.utente.setAutenticato(false);

                        //returning to login page
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        AccountDeleter accountDeleter = new AccountDeleter();
        accountDeleter.execute();
    }

    public void updatePassword(Utente utente, String newPassword, Context context) {
        class PaswordUpdater extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tAggiorno la password...");
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
                params.put("newpassword", newPassword);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.UPDATE_URL, params);
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
                        // update logged user password
                        MainActivity.utente.setPassword(newPassword);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        PaswordUpdater paswordUpdater = new PaswordUpdater();
        paswordUpdater.execute();
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
