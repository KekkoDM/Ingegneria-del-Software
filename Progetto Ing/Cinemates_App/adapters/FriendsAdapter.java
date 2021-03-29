package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.FriendsActivity;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private Context context;
    public static ArrayList<Utente> friends;

    public FriendsAdapter(Context context, ArrayList<Utente> friends) {
        this.context = context;
        this.friends = friends;
    }

    @Override
    public FriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friend_cardview, parent, false);
        return new FriendsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.MyViewHolder holder, int position) {
        Utente friend = friends.get(position);
        holder.setFriend(friend);
        holder.mSharedContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultsActivity.class);
                intent.putExtra("type", "sharedcontent");
                intent.putExtra("Friend", friends.get(position).getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mFriendUsername;
        public TextView mFriendName;
        public Button mSharedContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFriendUsername = itemView.findViewById(R.id.usernameFriend);
            mFriendName = itemView.findViewById(R.id.friendNameSurname);
            mSharedContent = itemView.findViewById(R.id.showSharedBtn);
        }

        public void setFriend(Utente friend) {
            mFriendUsername.setText(friend.getUsername());
            mFriendName.setText(friend.getNome() + " " + friend.getCognome());
        }
    }
}