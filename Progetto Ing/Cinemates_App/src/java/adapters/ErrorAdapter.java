package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;

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
        View v = LayoutInflater.from(context).inflate(R.layout.error_message, parent, false);
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
        public TextView errorTitle;
        public ImageView errorImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            errorText = itemView.findViewById(R.id.errorText);
            errorTitle = itemView.findViewById(R.id.errorTitle);
            errorImage = itemView.findViewById(R.id.errorImage);
        }

        public void setError(String text){
            errorText.setText(text);

            switch (text) {
                case "Non ci sono ancora recensioni":
                    errorImage.setImageResource(R.drawable.ic_no_review);
                    break;

                case "La tua lista dei Preferiti è vuota":
                    errorTitle.setVisibility(View.GONE);
                    errorImage.setImageResource(R.drawable.ic_no_favorites_items);
                    break;

                case "La tua lista dei Contenuti da vedere è vuota":
                    errorTitle.setVisibility(View.GONE);
                    errorImage.setImageResource(R.drawable.ic_list_empty_error);
                    break;

                case "Non hai nessun amico al momento":
                    errorImage.setImageResource(R.drawable.ic_no_friends);
                    break;

                case "Non ci sono nuove notifiche da mostrare":
                    errorTitle.setVisibility(View.GONE);
                    errorImage.setImageResource(R.drawable.ic_no_notification);
                    break;

                case "Non ci sono nuove richieste di collegamento":
                    errorTitle.setVisibility(View.GONE);
                    errorImage.setImageResource(R.drawable.ic_no_notification);
                    break;

                default:
                    errorImage.setImageResource(R.drawable.ic_result_error);
            }
        }
    }
}
