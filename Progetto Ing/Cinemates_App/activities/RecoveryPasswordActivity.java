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

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RecoveryPasswordActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private EditText email;
    private Button recoveryBtn;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
                if (email.getText().length() == 0) {
                    Toast.makeText(RecoveryPasswordActivity.this, "Inserisci la tua e-mail", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.utente.recoveryPassword(email.getText().toString(), RecoveryPasswordActivity.this);
                }
            }
        });
    }
}