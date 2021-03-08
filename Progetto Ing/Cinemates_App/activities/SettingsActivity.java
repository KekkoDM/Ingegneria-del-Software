package com.example.cinemates.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.LoginFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.restapi.CinematesDB;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    private EditText oldPw;
    private EditText newPw;
    private ImageButton backBtn;
    private Button updateBtn;
    private CardView deleteAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backBtn = findViewById(R.id.backBtnSettings);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldPw = findViewById(R.id.oldPassword);
        newPw = findViewById(R.id.newPassword);

        updateBtn = findViewById(R.id.updatePasswordBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword(MainActivity.utente, oldPw.getText().toString(), newPw.getText().toString());
            }
        });

        deleteAcc = findViewById(R.id.deleteAccountBtn);
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(SettingsActivity.this, deleteAcc);
                popup.getMenuInflater().inflate(R.menu.menu_delete_account, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteAccount(MainActivity.utente);
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    private void deleteAccount(Utente utente) {
        class AccountDeleter extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(SettingsActivity.this);

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
                        Toast.makeText(SettingsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        // delete logged user
                        MainActivity.utente.setAutenticato(false);

                        // returning to login page
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SettingsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        AccountDeleter accountDeleter = new AccountDeleter();
        accountDeleter.execute();
    }

    private void updatePassword(Utente utente, String oldPassword, String newPassword) {
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(SettingsActivity.this, "Alcuni campi sono vuoti", Toast.LENGTH_SHORT).show();
        }
        else if ((oldPassword.equals(utente.getPassword())) && (newPassword.length() >=6)) {
            class PaswordUpdater extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(SettingsActivity.this);

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
                            Toast.makeText(SettingsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                            // update logged user password
                            MainActivity.utente.setPassword(newPassword);

                            // returning to account fragment
                            onBackPressed();
                        } else {
                            Toast.makeText(SettingsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            PaswordUpdater paswordUpdater = new PaswordUpdater();
            paswordUpdater.execute();
        }
        else {
            Toast.makeText(SettingsActivity.this, "Verificare che la vecchia password sia corretta e che la nuova sia di almeno 6 caratteri", Toast.LENGTH_LONG).show();
        }
    }
}