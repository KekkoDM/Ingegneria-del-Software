package com.example.cinemates.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Utente> results;

    public SearchUserAdapter(Context context, ArrayList<Utente> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public SearchUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_cardview, parent, false);
        return new SearchUserAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Utente result = results.get(position);

        if (alreadyFriends(result)) {
            holder.followBtn.setText("Già aggiunto");
            holder.followBtn.setBackgroundColor(Color.LTGRAY);
            holder.followBtn.setEnabled(false);
        }

        holder.setResult(result);

        //send follow request
        holder.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFollowRequest(result, holder);
            }
        });
    }

    private Boolean alreadyFriends(Utente utente) {
        Boolean found = false;

        System.out.println("SIZE: " + FriendsAdapter.friends.size());
        for (Utente friend : FriendsAdapter.friends) {
            if (friend.getUsername().equals(utente.getUsername())) {
                found = true;
            }
        }

        return found;
    }

    private void sendFollowRequest(Utente result, MyViewHolder holder) {
        class SendFollowRequest extends AsyncTask<Void, Void, String> {
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
                params.put("sender", MainActivity.utente.getUsername());
                params.put("receiver", result.getUsername());

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_FOLLOW_NOTIFICATION_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        holder.followBtn.setText("Richiesta inviata");
                        holder.followBtn.setBackgroundColor(Color.LTGRAY);
                        holder.followBtn.setEnabled(false);
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        SendFollowRequest sendFollowRequest = new SendFollowRequest();
        sendFollowRequest.execute();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView friendUsername;
        public TextView friendName;
        public Button followBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            friendUsername = itemView.findViewById(R.id.friendUsername);
            friendName = itemView.findViewById(R.id.friendNameSurname);
            followBtn = itemView.findViewById(R.id.followBtn);
        }

        public void setResult(Utente result) {
            friendUsername.setText(result.getUsername());
            friendName.setText(result.getNome() + " " + result.getCognome());
        }
    }
}
