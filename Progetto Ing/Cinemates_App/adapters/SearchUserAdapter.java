package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Utente;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Utente> results;

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
        holder.setResult(result);
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
            friendName = itemView.findViewById(R.id.friendName);
            followBtn = itemView.findViewById(R.id.followBtn);
        }

        public void setResult(Utente result) {
            friendUsername.setText(result.getUsername());
            friendName.setText(result.getNome() + " " + result.getCognome());
        }
    }
}
