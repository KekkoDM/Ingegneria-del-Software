package com.example.cinemates.classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cinemates.R;

public class ReportDialog extends Dialog {

    private RadioGroup alertGroup;
    private RadioButton radioButton;
    private Button sendAlert;

    public ReportDialog(@NonNull Context context) {
        super(context);
    }

    public boolean showPopUp(){
        ImageView close;
        this.setContentView(R.layout.popup_report);
        alertGroup = findViewById(R.id.radioGroup2);
        sendAlert = findViewById(R.id.sendAlert);
        close = findViewById(R.id.closealert);

        sendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
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
        Toast.makeText(getContext(),"Motivo della segnalazione : "+ radioButton.getText(), Toast.LENGTH_SHORT).show();

    }
}
