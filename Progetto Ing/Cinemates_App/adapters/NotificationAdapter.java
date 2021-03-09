package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Utente;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Notifica> notifications;

    public NotificationAdapter(Context context, ArrayList<Notifica> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_cardview, parent, false);
        return new NotificationAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        Notifica notification = notifications.get(position);
        holder.setNotification(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTitle;
        public TextView notificationDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationDescription = itemView.findViewById(R.id.notificationDescription);

        }

        public void setNotification(Notifica notifica) {
            notificationTitle.setText(notifica.getTitolo());
            notificationDescription.setText(notifica.getDescrizione());
        }
    }
}
