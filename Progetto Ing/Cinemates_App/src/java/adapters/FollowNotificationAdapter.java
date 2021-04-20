package com.example.cinemates.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.AccountFragment;
import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.fragments.NotificationFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.example.cinemates.api.CinematesDB;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FollowNotificationAdapter extends RecyclerView.Adapter<FollowNotificationAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Notifica> notifications = new ArrayList<>() ;

    public FollowNotificationAdapter(Context context, ArrayList<Notifica> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public FollowNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.follow_notification_cardview, parent, false);
        return new FollowNotificationAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowNotificationAdapter.MyViewHolder holder, int position) {
        Notifica notification = notifications.get(position);
        holder.setNotification(notification);

        //accept follow notification
        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.utente.acceptFollowNotification(notification, position, context);
            }
        });

        //reject follow notification
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.utente.deleteNotification(notification, position, "Collegamento", context);
            }
        });
    }

    public void removeNotification(int position) {
        notifications.remove(position);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTitle;
        public TextView notificationDescription;
        public Button rejectBtn;
        public Button confirmBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.followTitle);
            notificationDescription = itemView.findViewById(R.id.followDescription);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
            confirmBtn = itemView.findViewById(R.id.confirmBtn);
        }

        public void setNotification(Notifica notifica) {
            notificationTitle.setText(notifica.getTitolo());
            notificationDescription.setText(notifica.getDescrizione());
        }
    }
}
