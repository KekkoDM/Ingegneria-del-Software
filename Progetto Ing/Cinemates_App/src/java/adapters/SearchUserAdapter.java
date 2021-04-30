package com.example.cinemates.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Utente> results;
    private FirebaseAnalytics mFirebaseAnalytics;

    public SearchUserAdapter(Context context, ArrayList<Utente> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public SearchUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_cardview, parent, false);
        return new SearchUserAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Utente result = results.get(position);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        if (alreadyFriends(result)) {
            holder.updateSendRequestButton("Già aggiunto");
        }

        holder.setResult(result);

        //send follow request
        holder.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.utente.sendFollowRequest(result, holder, context);

                //[START custom_event]
                Bundle params = new Bundle();
                params.putString("receiver", result.getUsername());
                params.putString("sender", MainActivity.utente.getUsername());
                mFirebaseAnalytics.logEvent("Request_Friendship", params);
            }
        });
    }

    public Boolean alreadyFriends(Utente utente) {
        Boolean found = false;
        for (Utente friend : FriendsAdapter.friends) {
            if (friend.getUsername().equals(utente.getUsername())) {
                found = true;
            }
        }

        return found;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView friendUsername;
        public TextView friendName;
        public Button followBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            friendUsername = itemView.findViewById(R.id.friendUsername);
            friendName = itemView.findViewById(R.id.friendNameSurname);
            followBtn = itemView.findViewById(R.id.followBtn);
        }

        public void setResult(Utente result) {
            friendUsername.setText(result.getUsername());
            friendName.setText(result.getNome() + " " + result.getCognome());
        }

        public void updateSendRequestButton(String textButton) {
            followBtn.setText(textButton);
            followBtn.setBackgroundColor(Color.LTGRAY);
            followBtn.setEnabled(false);
        }
    }
}
