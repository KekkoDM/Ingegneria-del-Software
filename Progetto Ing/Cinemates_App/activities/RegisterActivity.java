package com.example.cinemates.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cinemates.restapi.CinematesDB;
import com.example.cinemates.R;
import com.example.cinemates.handlers.RequestHandler;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            JSONObject userJson = obj.getJSONObject("utente");
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }

            RegisterUser register = new RegisterUser();
            register.execute();
        }
    }
}