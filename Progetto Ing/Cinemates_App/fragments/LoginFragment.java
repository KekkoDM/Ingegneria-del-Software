package com.example.cinemates.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.cinemates.api.CinematesDB;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment {
    private TextView forgotPW;
    private TextView register;
    private ImageView showPassword;
    private EditText usernameField;
    private EditText passwordField;
    private Button loginBtn;
    private Boolean isShowed;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private Button googleBtn;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
        googleBtn = view.findViewById(R.id.googleBtn);
        isShowed = false;
        mAuth = FirebaseAuth.getInstance();

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRequest();
                signIn();
            }
        });

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

    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void signIn() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

                MainActivity.utente.setEmail(account.getEmail());
                MainActivity.utente.setNome(account.getDisplayName());
                MainActivity.utente.setUsername(account.getGivenName());

                checkEmail();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Errore di Accesso con Google!", task.getException());

                        }
                    }
                });
    }

    private void checkEmail() {
        class EmailChecker extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tAccesso con Google in corso...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", MainActivity.utente.getEmail());

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.CHECK_EMAIL, params);
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
                        Intent intent = new Intent(getContext(), RegisterActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        EmailChecker checker = new EmailChecker();
        checker.execute();
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