package com.example.cinemates.classes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ReportDialog extends Dialog {
    private RadioGroup alertGroup;
    private RadioButton radioButton;
    private Button sendAlert;
    private Context context;

    public ReportDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public boolean showPopUp(Object item, String type){
        ImageView close;
        this.setContentView(R.layout.popup_report);
        alertGroup = findViewById(R.id.radioGroup2);
        sendAlert = findViewById(R.id.sendAlert);
        close = findViewById(R.id.closealert);

        sendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
                sendReport(item, type);
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

    private void sendReport(Object item, String type) {
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
                    params.put("item", ((Review) item).getId());
                }
                else {
                    params.put("item", String.valueOf(((Comment) item).getId()));
                }
                params.put("reason", radioButton.getText().toString());

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
