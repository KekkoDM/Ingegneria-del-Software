package com.example.cinemates.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.FriendsAdapter;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendsActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private Button searchBtn;
    private EditText friendSearched;
    private ImageView noFriendsIcon;
    private TextView noFriendsLabel;
    private ArrayList<Utente> friends;
    private RecyclerView rv;
    private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        backBtn = findViewById(R.id.backBtnFriends);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rv = findViewById(R.id.recyclerViewFriends);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        friends = new ArrayList<>();

        loadFriendsList(MainActivity.utente);

        friendSearched = findViewById(R.id.friendSearched);

        searchBtn = findViewById(R.id.searchFriendBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendSearched.getText().length() == 0) {
                    friendSearched.setHintTextColor(getResources().getColor(R.color.google_color));
                    Toast.makeText(FriendsActivity.this, "Il campo di ricerca è vuoto", Toast.LENGTH_SHORT).show();
                } else {
                    friendSearched.setHintTextColor(getResources().getColor(R.color.light_grey));
                    Intent intent = new Intent(FriendsActivity.this, ResultsActivity.class);
                    intent.putExtra("type", "friend");
                    intent.putExtra("friendsearched", friendSearched.getText().toString());
                    startActivity(intent);
                }
            }
        });

        noFriendsIcon = findViewById(R.id.noFriendIcon);
        noFriendsLabel = findViewById(R.id.noFriendLabel);
    }

    public void loadFriendsList(Utente utente) {
        class FriendsLoader extends AsyncTask<Void, Void, String> {

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

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.FRIENDS_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //pdLoading.dismiss();

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
                                    userJson.getString("cognome"),null,null
                            );

                            friends.add(utente);
                        }

                        friendsAdapter = new FriendsAdapter(FriendsActivity.this, friends);
                        rv.setAdapter(friendsAdapter);
                    } else {
                        noFriendsIcon.setVisibility(View.VISIBLE);
                        noFriendsLabel.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        FriendsLoader friendsLoader = new FriendsLoader();
        friendsLoader.execute();
    }
}