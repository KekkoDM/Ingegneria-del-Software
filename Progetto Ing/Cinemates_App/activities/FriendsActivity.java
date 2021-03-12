package com.example.cinemates.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.FriendsAdapter;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.AccountFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.restapi.CinematesDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendsActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private ArrayList<Utente> friends;
    private RecyclerView rv;
    private FriendsAdapter adapter;

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
    }

    public void loadFriendsList(Utente utente) {
        class FriendsLoader extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(FriendsActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tCarico la lista amici...");
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

                System.out.println(params);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.FRIENDS_URL, params);
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
                                   null,null,null,null
                            );

                            friends.add(utente);
                        }
                        adapter = new FriendsAdapter(FriendsActivity.this, friends);
                        rv.setAdapter(adapter);
                    } else {
                        Toast.makeText(FriendsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
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