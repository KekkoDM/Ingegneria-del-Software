package com.example.cinemates.classes;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class Review implements Serializable {
    private String title;
    private String descrizione;
    private String data;
    private String id;
    private String user;
    private int userPhoto;

    public Review() {
    }

    public Review(String id,String user, String descrizione, String data) {
        this.id =id;
        this.user = user;
        this.descrizione = descrizione;
        this.data = data;
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        descrizione = descrizione;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        data = data;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getId() {
        return id;
    }

    public void checkReviewVisibility(Review review, ReviewAdapter.ReviewViewHolder holder) {
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
