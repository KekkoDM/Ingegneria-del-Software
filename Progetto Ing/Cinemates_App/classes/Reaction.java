package com.example.cinemates.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfigBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Reaction {
    private final String[] strings = {"Mi piace", "Love", "Ahah", "Triste", "Wow", "Grrr"};
    Context context;

    public Reaction(Context context) {
        this.context = context;
    }

    // show reactions popup
    public ReactionPopup showReaction(Review review, ImageView button, TextView count) {
        ReactionPopup popup = new ReactionPopup(
                context,
                new ReactionsConfigBuilder(context)
                        .withReactions(new int[]{
                                R.drawable.ic_like,
                                R.drawable.ic_love,
                                R.drawable.ic_laugh,
                                R.drawable.ic_sad,
                                R.drawable.ic_wow,
                                R.drawable.ic_angry,
                        })
                        .withReactionTexts(position -> strings[position])
                        .build());

        popup.setReactionSelectedListener((position) -> {
            if (position > -1) {
                sendReaction(position, review, button, count);
            };
            return position >= -1;
        });

        return popup;
    }

    private void sendReaction(Integer position, Review review, ImageView button, TextView count) {
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

                    // if no error in response
                    if (!obj.getBoolean("error")) {
                        if (obj.getString("message").equals("Reazione eliminata correttamente")) {
                            setReaction("null", Integer.parseInt(count.getText().toString()) - 1, button, count);
                        }
                        else {
                            getReaction(review, button, count);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReactionSender reactionSender = new ReactionSender();
        reactionSender.execute();
    }

    public void getReaction(Review review, ImageView button, TextView count) {
        class ReactionGetter extends AsyncTask<Void, Void, String> {

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

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.GET_REACTION, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    // if no error in response
                    if (obj.getBoolean("error")) {
                        setReaction("null", obj.getInt("contatore"), button, count);
                    }
                    else {
                        setReaction(obj.getString("reazione"), obj.getInt("contatore"), button, count);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReactionGetter reactionGetter = new ReactionGetter();
        reactionGetter.execute();
    }

    private void setReaction(String reaction, int counter, ImageView button, TextView count) {
        count.setText(String.valueOf(counter));

        switch (reaction) {
            case "Mi piace":
                button.setImageResource(R.drawable.ic_like);
                count.setTextColor(context.getResources().getColor(R.color.blue_hype));
                break;
            case "Love":
                button.setImageResource(R.drawable.ic_love);
                count.setTextColor(context.getResources().getColor(R.color.love_color));
                break;
            case "Ahah":
                button.setImageResource(R.drawable.ic_laugh);
                count.setTextColor(context.getResources().getColor(R.color.laugh_sad_wow_color));
                break;
            case "Triste":
                button.setImageResource(R.drawable.ic_sad);
                count.setTextColor(context.getResources().getColor(R.color.laugh_sad_wow_color));
                break;
            case "Wow":
                button.setImageResource(R.drawable.ic_wow);
                count.setTextColor(context.getResources().getColor(R.color.laugh_sad_wow_color));
                break;
            case "Grrr":
                button.setImageResource(R.drawable.ic_angry);
                count.setTextColor(context.getResources().getColor(R.color.angry_color));
                break;
            default:
                button.setImageResource(R.drawable.ic_no_reaction);
                count.setTextColor(context.getResources().getColor(R.color.light_grey));
        }
    }
}
