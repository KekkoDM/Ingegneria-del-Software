package com.example.cinemates.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.fragments.GeneralNotificationsFragment;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneralNotificationAdapter extends RecyclerView.Adapter<GeneralNotificationAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Notifica> notifications = new ArrayList<>();

    public GeneralNotificationAdapter(Context context, ArrayList<Notifica> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public GeneralNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.general_notification_cardview, parent, false);
        return new GeneralNotificationAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralNotificationAdapter.MyViewHolder holder, int position) {
        Notifica notification = notifications.get(position);
        holder.setNotification(notification);

        holder.deleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.utente.deleteNotification(notification, position, "Generale", context);
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
        public ImageView deleteNotification;

        public MyViewHolder(View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationDescription = itemView.findViewById(R.id.notificationDescription);
            deleteNotification = itemView.findViewById(R.id.deleteNotification);
        }

        public void setNotification(Notifica notifica) {
            notificationTitle.setText(notifica.getTitolo());
            notificationDescription.setText(notifica.getDescrizione());
        }
    }
}
