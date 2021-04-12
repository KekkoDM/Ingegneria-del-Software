package com.example.cinemates.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.cinemates.activities.FriendsActivity;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.AccountFragment;
import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultsActivity.class);
                intent.putExtra("type", "sharedcontent");
                intent.putExtra("Friend", friends.get(position).getUsername());
                context.startActivity(intent);
            }
        });

        holder.mRemoveFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.removeFriend(friend, position);
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
        public Button mRemoveFriend;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFriendUsername = itemView.findViewById(R.id.usernameFriend);
            mFriendName = itemView.findViewById(R.id.friendNameSurname);
            mRemoveFriend = itemView.findViewById(R.id.removeFriendBtn);
        }

        public void setFriend(Utente friend) {
            mFriendUsername.setText(friend.getUsername());
            mFriendName.setText(friend.getNome() + " " + friend.getCognome());
        }

        private void removeFriend(Utente friend, int position) {
            class FriendRemover extends AsyncTask<Void, Void, String> {
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
                    params.put("username", MainActivity.utente.getUsername());
                    params.put("friend", friend.getUsername());

                    //returning the response
                    return requestHandler.sendPostRequest(CinematesDB.REMOVE_FRIEND, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            friends.remove(position);
                            FriendsActivity.rv.removeViewAt(position);
                            FriendsActivity.friendsAdapter.notifyItemRemoved(position);
                            FriendsActivity.friendsAdapter.notifyItemRangeChanged(position, friends.size());
                        }

                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            FriendRemover friendRemover = new FriendRemover();
            friendRemover.execute();
        }
    }
}