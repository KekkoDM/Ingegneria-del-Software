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
                String username = usernameNewUser.getText().toString();
                String name = nameNewUser.getText().toString();
                String surname = surnameNewUser.getText().toString();
                String email = emailNewUser.getText().toString();
                String password = passwordNewUser.getText().toString();

                if (username.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Alcuni campi sono vuoti", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.utente.registerUser(name, surname, username, email, password, RegisterActivity.this);
                }
            }
        });
    }
}