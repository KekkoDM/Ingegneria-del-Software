package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.classes.Utente;
import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private Context context;
    public static ArrayList<Utente> friends = new ArrayList<>();

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
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mFriendUsername;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFriendUsername = itemView.findViewById(R.id.usernameFriend);

        }

        public void setFriend(Utente friend) {
            mFriendUsername.setText(friend.getUsername());
        }
    }
}