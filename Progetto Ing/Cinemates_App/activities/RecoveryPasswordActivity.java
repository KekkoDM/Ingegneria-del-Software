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

import com.example.cinemates.R;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RecoveryPasswordActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private EditText email;
    private Button recoveryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        email = findViewById(R.id.email);
        recoveryBtn = findViewById(R.id.recoveryPw);
        recoveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoveryPassword(email.getText().toString());
            }
        });
    }

    private void recoveryPassword(String email) {
        if (email.isEmpty()) {
            Toast.makeText(RecoveryPasswordActivity.this, "Inserisci la tua e-mail", Toast.LENGTH_SHORT).show();
        } else {
            class RecoveryPassword extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(RecoveryPasswordActivity.this);

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
                            onBackPressed();
                        }

                        Toast.makeText(RecoveryPasswordActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            RecoveryPassword recoveryPassword = new RecoveryPassword();
            recoveryPassword.execute();
        }
    }
}