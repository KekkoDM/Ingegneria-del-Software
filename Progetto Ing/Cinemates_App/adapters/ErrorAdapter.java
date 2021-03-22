package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.classes.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> error;

    public ErrorAdapter(Context context,ArrayList error) {
        this.context = context;
        this.error = error;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.error_cardview, parent, false);
        return new ErrorAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorAdapter.MyViewHolder holder, int position) {
        String text = error.get(position);
        holder.setError(text);
    }

    @Override
    public int getItemCount() {
        return error.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView errorText;
        public ImageView errorImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            errorText = itemView.findViewById(R.id.errorText);
            errorImage = itemView.findViewById(R.id.errorImage);
        }

        public void setError(String text){
            errorText.setText(text);

            if (text.equals("Non ci sono ancora recensioni")){
                errorImage.setImageResource(R.drawable.ic_no_review);
            }
            else {
                errorImage.setImageResource(R.drawable.ic_search_error);
            }
        }


    }
}
