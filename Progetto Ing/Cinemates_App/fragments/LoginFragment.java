package com.example.cinemates.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.RecoveryPasswordActivity;
import com.example.cinemates.activities.RegisterActivity;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.restapi.CinematesDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginFragment extends Fragment {
    private TextView forgotPW;
    private TextView register;
    private ImageView showPassword;
    private EditText usernameField;
    private EditText passwordField;
    private Button loginBtn;
    private Boolean isShowed;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameField = view.findViewById(R.id.username);
        passwordField = view.findViewById(R.id.password);
        forgotPW = view.findViewById(R.id.forgotPw);
        register = view.findViewById(R.id.register);
        loginBtn = view.findViewById(R.id.loginButton);
        showPassword = view.findViewById(R.id.showPw);
        isShowed = false;

        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecoveryPasswordActivity.class);
                startActivity(intent);
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowed) {
                    passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());;
                    isShowed = false;
                }
                else {
                    passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowed = true;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });

        return view;
    }

    public void loginUser(View v) {
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this.getContext(), "Alcuni campi sono vuoti", Toast.LENGTH_SHORT).show();
        }
        else {
            class UserLogin extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(getContext());

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
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            //getting the user from the response
                            JSONObject userJson = obj.getJSONObject("utente");
                            Utente utente = new Utente(
                                    userJson.getString("username"),
                                    userJson.getString("nome"),
                                    userJson.getString("cognome"),
                                    userJson.getString("email"),
                                    userJson.getString("password")
                            );

                            //starting the profile page
                            MainActivity.utente = utente;
                            MainActivity.utente.setAutenticato(true);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                        } else {
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            UserLogin login = new UserLogin();
            login.execute();
        }
    }
}