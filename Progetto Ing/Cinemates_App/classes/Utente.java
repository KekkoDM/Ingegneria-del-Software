package com.example.cinemates.classes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.CommentsActivity;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.activities.RecoveryPasswordActivity;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.activities.SettingsActivity;
import com.example.cinemates.adapters.CommentAdapter;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.adapters.SearchUserAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.fragments.AccountFragment;
import com.example.cinemates.fragments.DescriptionFragment;
import com.example.cinemates.fragments.FavoritesFragment;
import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.fragments.GeneralNotificationsFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Utente implements Serializable {
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean autenticato = false;

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
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
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
                        ((RecoveryPasswordActivity) context).onBackPressed();

                        Bundle params = new Bundle();
                        params.putString("user", MainActivity.utente.getUsername());
                        params.putString("email", MainActivity.utente.getEmail());
                        mFirebaseAnalytics.logEvent("Recovery_Password", params);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
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
                        //logout current user
                        MainActivity.utente.setAutenticato(false);

                        //returning to login page
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                        ((SettingsActivity) context).onBackPressed();

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
                        followNotificationsFragment.removeNotification(position, obj.getString("message"));

                        // [START custom_event]
                        Bundle params = new Bundle();
                        params.putString("receiver",notification.getDestinatario());
                        params.putString("sender", notification.getMittente());
                        mFirebaseAnalytics.logEvent("Accepted_Follow_Notification", params);
                    }
                    else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
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
            FollowNotificationsFragment followNotificationsFragment = FollowNotificationsFragment.getInstance();
            GeneralNotificationsFragment generalNotificationsFragment = GeneralNotificationsFragment.getInstance();

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
                            generalNotificationsFragment.removeNotification(position);
                        }
                        else {
                            followNotificationsFragment.removeNotification(position, obj.getString("message"));
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

    public void sendFollowRequest(Utente user, SearchUserAdapter.MyViewHolder holder, Context context) {
        class SendFollowRequest extends AsyncTask<Void, Void, String> {
            ResultsActivity resultsActivity = (ResultsActivity) context;

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
                params.put("sender", MainActivity.utente.getUsername());
                params.put("receiver", user.getUsername());

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        resultsActivity.updateButtonSendRequest(holder);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        SendFollowRequest sendFollowRequest = new SendFollowRequest();
        sendFollowRequest.execute();
    }

    public void searchUser(String friendsearched, Context context) {
        class SearchUser extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(context);
            ResultsActivity resultsActivity = (ResultsActivity) context;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tRicerca in corso...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());
                params.put("usersearched", friendsearched);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEARCH_USER, params);
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
                        JSONArray usersJson = obj.getJSONArray("utente");
                        ArrayList<Utente> users = new ArrayList<>();
                        for (int i = 0; i < usersJson.length(); i++) {
                            JSONObject userJson = usersJson.getJSONObject(i);
                            Utente utente = new Utente(
                                    userJson.getString("username"),
                                    userJson.getString("nome"),
                                    userJson.getString("cognome"),
                                    null,
                                    null
                            );

                            users.add(utente);
                        }
                        resultsActivity.showSearchUserResult(users);
                    }
                    else {
                        resultsActivity.showSearchUserError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        SearchUser searchUser = new SearchUser();
        searchUser.execute();
    }

    public void showSharedContents(String friend, Context context) {
        class SharedContent extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(context);
            ResultsActivity resultsActivity = (ResultsActivity) context;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tCaricamento in corso...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());
                params.put("friend", friend);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SHARED_CONTENT, params);
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
                        JSONArray sharedItems = obj.getJSONArray("comune");
                        RequestJson requestJson = new RequestJson(context);

                        for (int i = 0; i < sharedItems.length(); i++) {
                            JSONObject item = sharedItems.getJSONObject(i);
                            Film film = new Film(
                                    item.getString("item"),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    item.getString("type")
                            );

                            requestJson.parseJSONSavedList(resultsActivity.getRecyclerView(), film);
                        }
                    } else {
                        resultsActivity.showSharedContentError(friend);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        SharedContent sharedContent = new SharedContent();
        sharedContent.execute();
    }

    public void addToList(Film film, String list, Context context) {
        class ListAdder extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            DescriptionFragment descriptionFragment = DescriptionFragment.getInstance();

            protected void onPreExecute(String s) {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("list", list);
                params.put("username", MainActivity.utente.getUsername());
                params.put("item", film.getId());
                params.put("type", film.getType());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.ADD_TO_LIST_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        if (list.equals("Preferiti")) {
                            descriptionFragment.enableFavoritesButton();
                        }
                        else {
                            descriptionFragment.enableToSeeButton();
                        }

                        // [START custom_event]
                        Bundle params = new Bundle();
                        params.putString("user",MainActivity.utente.getUsername());
                        params.putString("list", list);
                        params.putString("media_id", film.getId());
                        params.putString("media_title", film.getTitle());
                        mFirebaseAnalytics.logEvent("Added_To_List", params);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ListAdder listAdder = new ListAdder();
        listAdder.execute();
    }

    public void removeFromList(Film film, String list, Context context) {
        class Remover extends AsyncTask<Void, Void, String> {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            DescriptionFragment descriptionFragment = DescriptionFragment.getInstance();

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
                params.put("username", MainActivity.utente.getUsername());
                params.put("item", film.getId());
                params.put("list", list);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.REMOVE_FROM_LIST, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        if (list.equals("Preferiti")) {
                            descriptionFragment.disableFavoritesButton();
                        }
                        else {
                            descriptionFragment.disableToSeeButton();
                        }

                        // [START custom_event]
                        Bundle params = new Bundle();
                        params.putString("user", MainActivity.utente.getUsername());
                        params.putString("list", list);
                        params.putString("media_id", film.getId());
                        params.putString("media_title", film.getTitle());
                        mFirebaseAnalytics.logEvent("Removed_From_List", params);
                    }

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Remover remover = new Remover();
        remover.execute();
    }

    public void sendComment(String comment, Review review, Context context) {
        class CommentSender extends AsyncTask<Void, Void, String> {
            CommentsActivity commentsActivity = (CommentsActivity) context;

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
                params.put("username", MainActivity.utente.getUsername());
                params.put("comment", comment);
                params.put("review", review.getId());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_COMMENT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Comment cmt = new Comment(
                                -1,
                                comment,
                                MainActivity.utente.getUsername(),
                                0
                        );

                        commentsActivity.postComment(cmt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        CommentSender commentSender = new CommentSender();
        commentSender.execute();
    }

    public void sendReaction(String reaction, Review review) {
        class ReactionSender extends AsyncTask<Void, Void, String> {

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
                params.put("username", MainActivity.utente.getUsername());
                params.put("review", review.getId());
                params.put("reaction", reaction);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_REACTION, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }

        ReactionSender reactionSender = new ReactionSender();
        reactionSender.execute();
    }

    public void sendReport(Object item, String type, String note, String reason, Context context) {
        ProgressDialog pdLoading = new ProgressDialog(context);

        class ReportSender extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tInvio segnalazione...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());
                params.put("type", type);
                if (type.equals("Recensione")) {
                    params.put("item", ((Review) item).getId());
                }
                else {
                    params.put("item", String.valueOf(((Comment) item).getId()));
                }
                params.put("reason", reason);
                params.put("note", note);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_REPORT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReportSender reportSender = new ReportSender();
        reportSender.execute();
    }

    public void generateRandomFromList(String listName, int numberItems) {
        Random random = new Random();
        int nextId = 0;
        int id = ((FavoritesFragment) MainActivity.selectedFragment).getIdentifier();

        do {
            nextId = random.nextInt(numberItems);
        } while(id == nextId);

        id = nextId;

        ((FavoritesFragment) MainActivity.selectedFragment).showGeneratedItem(id, listName);
    }
}
