package com.example.cinemates.classes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ReportDialog extends Dialog {
    private RadioGroup alertGroup;
    private RadioButton radioButton;
    private EditText note;
    private TextView charCounter;
    private ImageView close;
    private Button sendAlert;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    public ReportDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public boolean showPopUp(Object item, String type){
        this.setContentView(R.layout.popup_report);

        alertGroup = findViewById(R.id.radioGroup2);
        note = findViewById(R.id.noteReport);
        sendAlert = findViewById(R.id.sendAlert);
        charCounter = findViewById(R.id.charCount);
        close = findViewById(R.id.closealert);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        note.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                charCounter.setText(String.valueOf(s.length()) + "/30");
            }

            public void afterTextChanged(Editable s) {

            }
            });

        sendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
                sendReport(item, type, note.getText().toString());

                // [START custom_event]
                Bundle params = new Bundle();
                params.putString("type", type);
                params.putString("note", String.valueOf(note));
                params.putString("signaler", MainActivity.utente.getUsername());
                params.putString("reason", radioButton.getText().toString());
                mFirebaseAnalytics.logEvent("Report", params);

                dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        show();

        return true;
    }

    private void sendReport(Object item, String type, String note) {
        ProgressDialog pdLoading = new ProgressDialog(getContext());

        class ReportSender extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tInvio segnalazione...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());
                params.put("type", type);
                if (type.equals("Recensione")) {
                    params.put("item", ((Recensione) item).getId());
                }
                else {
                    params.put("item", String.valueOf(((Commento) item).getId()));
                }
                params.put("reason", radioButton.getText().toString());
                params.put("note", note);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_REPORT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReportSender reportSender = new ReportSender();
        reportSender.execute();
    }

    private void checkButton(View v){
        int radioId = alertGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}
