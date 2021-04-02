package com.example.cinemates.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfigBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Reaction {
    private final String[] strings = {"like", "love", "ahah", "triste", "argh!"};
    Context context;

    public Reaction(Context context) {
        this.context = context;
    }

    // show reactions popup
    public ReactionPopup getReaction(Review review) {
        ReactionPopup popup = new ReactionPopup(
                context,
                new ReactionsConfigBuilder(context)
                        .withReactions(new int[]{
                                R.drawable.ic_like,
                                R.drawable.ic_love,
                                R.drawable.ic_laugh,
                                R.drawable.ic_sad,
                                R.drawable.ic_angry,
                        })
                        .withReactionTexts(position -> strings[position])
                        .build());

        popup.setReactionSelectedListener((position) -> {
            if (position > -1) {
                sendReaction(position, review);
            };
            return position >= -1;
        });

        return popup;
    }

    private void sendReaction(Integer position, Review review) {
        class ReactionSender extends AsyncTask<Void, Void, String> {

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
                params.put("review", review.getId());
                params.put("reaction", strings[position]);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_REACTION, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    /*if no error in response
                    if (!obj.getBoolean("error")) {

                    }*/
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReactionSender reactionSender = new ReactionSender();
        reactionSender.execute();
    }
}
