package com.example.cinemates.classes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cinemates.MainActivity;
import com.example.cinemates.activities.CommentsActivity;
import com.example.cinemates.adapters.CommentAdapter;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Comment {
    private int id;
    private String descrizione;
    private String username;
    private int reportNumber;

    public Comment(int id, String descrizione, String username, int reportNumber) {
        this.id = id;
        this.descrizione = descrizione;
        this.username = username;
        this.reportNumber = reportNumber;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void getComments(Review review, Context context) {
        class CommentsLoader extends AsyncTask<Void, Void, String> {
            CommentsActivity commentsActivity = (CommentsActivity) context;

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
                params.put("review", review.getId());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.GET_COMMENTS, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        JSONArray commentsJson = obj.getJSONArray("commenti");
                        commentsActivity.comments = new ArrayList<>();

                        for (int i = 0; i < commentsJson.length(); i++) {
                            JSONObject cmt = commentsJson.getJSONObject(i);
                            Comment comment = new Comment(
                                    cmt.getInt("id"),
                                    cmt.getString("commento"),
                                    cmt.getString("username"),
                                    cmt.getInt("report")
                            );
                            commentsActivity.comments.add(comment);
                        }

                        commentsActivity.commentAdapter = new CommentAdapter(commentsActivity.comments, context);
                        commentsActivity.rvComments.setAdapter(commentsActivity.commentAdapter);
                    } else {
                        ArrayList<String> error = new ArrayList<String>();
                        error.add(obj.getString("message"));
                        ErrorAdapter errorAdapter = new ErrorAdapter(context, error);
                        commentsActivity.rvComments.setAdapter(errorAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        CommentsLoader commentsLoader = new CommentsLoader();
        commentsLoader.execute();
    }
}
