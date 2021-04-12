package com.example.cinemates.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemates.MainActivity;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.R;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private Button submitBtn;
    private EditText nameNewUser;
    private EditText surnameNewUser;
    private EditText usernameNewUser;
    private EditText emailNewUser;
    private EditText passwordNewUser;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nameNewUser = findViewById(R.id.name);
        surnameNewUser = findViewById(R.id.surname);
        usernameNewUser = findViewById(R.id.username);
        emailNewUser = findViewById(R.id.email);
        passwordNewUser = findViewById(R.id.password);

        Intent intent = getIntent();
        if (intent != null){
            nameNewUser.setText(MainActivity.utente.getNome());
            emailNewUser.setText(MainActivity.utente.getEmail());
            usernameNewUser.setText(MainActivity.utente.getUsername());
        }

        submitBtn = findViewById(R.id.registerBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(v);
            }
        });
    }

    public void registerUser(View view){
        final String username = usernameNewUser.getText().toString();
        final String name = nameNewUser.getText().toString();
        final String surname = surnameNewUser.getText().toString();
        final String email = emailNewUser.getText().toString();
        final String password = passwordNewUser.getText().toString();

        if (username.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Alcuni campi sono vuoti", Toast.LENGTH_SHORT).show();
        }
        else {
            class RegisterUser extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(RegisterActivity.this);

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

                            MainActivity.utente = utente;
                            MainActivity.utente.setAutenticato(true);

                            Bundle params = new Bundle();
                            params.putString("user", MainActivity.utente.getUsername());
                            params.putString("email", MainActivity.utente.getEmail());
                            mFirebaseAnalytics.logEvent("User_Created", params);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Ops! Qualcosa è andato storto", Toast.LENGTH_LONG).show();
                    }
                }
            }

            RegisterUser register = new RegisterUser();
            register.execute();
        }
    }
}