package com.example.cinemates.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.FriendsAdapter;
import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.adapters.SearchUserAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity {
    private ImageButton backBtn;

    private ArrayList<Utente> users;
    public static RecyclerView rv;
    public static ImageView noResultIcon;
    public static TextView noResultLabel;
    private RequestJson requestJson;

    private ErrorAdapter errorAdapter;
    private SearchUserAdapter searchUserAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rv = findViewById(R.id.resultContainer);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        requestJson = new RequestJson(this);

        noResultIcon = findViewById(R.id.noResultIcon);
        noResultLabel = findViewById(R.id.noResultLabel);

        Intent intent = getIntent();
        if (intent.getStringExtra("type").equals("film")) {
            
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, intent.getStringExtra("textsearched"));
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

            requestJson.parseJSONSearch(rv, intent.getStringExtra("textsearched"));
        }
        else if (intent.getStringExtra("type").equals("friend")) {
            users = new ArrayList<>();
            searchUser(intent.getStringExtra("friendsearched"));

        }
        else {
            String friend = intent.getStringExtra("Friend");
            showSharedContents(friend);
        }

        backBtn = findViewById(R.id.backButton2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void searchUser(String friendsearched) {
        class SearchUser extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(ResultsActivity.this);

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
                        searchUserAdapter = new SearchUserAdapter(ResultsActivity.this, users);
                        rv.setAdapter(searchUserAdapter);
                    }
                    else{
                        ArrayList<String> error = new ArrayList<String>();
                        error.add("Ops! La ricerca non ha prodotto risultati");
                        errorAdapter = new ErrorAdapter(ResultsActivity.this, error);
                        rv.setAdapter(errorAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        SearchUser searchUser = new SearchUser();
        searchUser.execute();
    }

    private void showSharedContents(String friend) {
        class SharedContent extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(ResultsActivity.this);

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
                            requestJson.parseJSONSavedList(rv, film);
                        }
                    } else {
                        ArrayList<String> error = new ArrayList<String>();
                        error.add("Non hai nessun elemento in comune con " +friend);
                        errorAdapter = new ErrorAdapter(ResultsActivity.this, error);
                        rv.setAdapter(errorAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        SharedContent sharedContent = new SharedContent();
        sharedContent.execute();
    }
}