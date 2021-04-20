package com.example.cinemates.classes;

import android.os.AsyncTask;

import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class Recensione implements Serializable {
    private String descrizione;
    private String data;
    private String id;
    private String user;

    public Recensione() {
    }

    public Recensione(String id, String user, String descrizione, String data) {
        this.id = id;
        this.user = user;
        this.descrizione = descrizione;
        this.data = data;
    }

    public String getUser() { return user; }

    public String getDescrizione() {
        return descrizione;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public void checkReviewVisibility(Recensione review, ReviewAdapter.ReviewViewHolder holder) {
        class ReviewVisibility extends AsyncTask<Void, Void, String> {

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
                params.put("item", review.getId());
                params.put("type", "Recensione");

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.REPORT_COUNT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    // if no error in response
                    if (obj.getBoolean("error")) {
                        holder.setCensoredReview(review);
                    }
                    else {
                        holder.setReview(review);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReviewVisibility reviewVisibility = new ReviewVisibility();
        reviewVisibility.execute();
    }
}
