package com.example.cinemates.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import com.example.cinemates.classes.Comment;
import com.example.cinemates.classes.Review;
import com.example.cinemates.handlers.RequestHandler;

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
                MainActivity.utente.sendReport(item, type, note.getText().toString(), radioButton.getText().toString(), getContext());
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

    private void checkButton(View v){
        int radioId = alertGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}
