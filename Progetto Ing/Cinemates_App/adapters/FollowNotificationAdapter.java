package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.classes.Notifica;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FollowNotificationAdapter extends RecyclerView.Adapter<FollowNotificationAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Notifica> notifications;

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
