package com.example.cinemates.adapters;

import android.content.Context;
import android.os.AsyncTask;
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
                acceptFollowNotification(notification, position);
            }
        });

        //delete follow notification
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectFollowNotification(notification, position);
            }
        });
    }

    private void rejectFollowNotification(Notifica notification, int position) {
        class RejectFollowNotification extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("idnotification", String.valueOf(notification.getId()));

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.REJECT_FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        notifications.remove(position);
                        FollowNotificationsFragment.rvFollow.removeViewAt(position);
                        FollowNotificationsFragment.followAdapter.notifyItemRemoved(position);
                        FollowNotificationsFragment.followAdapter.notifyItemRangeChanged(position, notifications.size());

                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RejectFollowNotification rejectFollowNotification = new RejectFollowNotification();
        rejectFollowNotification.execute();
    }

    private void acceptFollowNotification(Notifica notification, int position) {
        class AcceptFollowNotification extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("sender", notification.getMittente());
                params.put("receiver", notification.getDestinatario());
                params.put("idnotification", String.valueOf(notification.getId()));

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.ACCEPT_FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        notifications.remove(position);
                        FollowNotificationsFragment.rvFollow.removeViewAt(position);
                        FollowNotificationsFragment.followAdapter.notifyItemRemoved(position);
                        FollowNotificationsFragment.followAdapter.notifyItemRangeChanged(position, notifications.size());

                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        AcceptFollowNotification acceptFollowNotification = new AcceptFollowNotification();
        acceptFollowNotification.execute();
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
