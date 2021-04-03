package com.example.cinemates.activities;
import com.example.cinemates.MainActivity;
import com.example.cinemates.adapters.CommentAdapter;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.GeneralNotificationAdapter;
import com.example.cinemates.adapters.ReviewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Comment;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.classes.Reaction;
import com.example.cinemates.classes.ReportDialog;
import com.example.cinemates.classes.Review;
import com.example.cinemates.R;
import com.example.cinemates.fragments.DescriptionFragment;
import com.example.cinemates.fragments.GeneralNotificationsFragment;
import com.example.cinemates.handlers.RequestHandler;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfigBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentsActivity extends AppCompatActivity {
    public static RecyclerView rvComments;
    private TextView detailReview;
    private TextView usernameReview;
    private TextView dateReview;
    private TextView contLike;
    private TextView likeBtn;
    private ImageView commentReview;
    private ImageView alertReview;
    private EditText textComment;
    private CommentAdapter commentAdapter;
    private Review review;
    private ImageButton backBtn;
    private ReportDialog dialog;
    private Button sendComment;
    private TextView errorComment;
    private ArrayList<Comment> comments;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        review = (Review) intent.getSerializableExtra("recensione");

        rvComments = findViewById(R.id.list_comment);
        rvComments.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(CommentsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComments.setLayoutManager(linearLayoutManager);

        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments, CommentsActivity.this);
        getComments(review);

        backBtn = findViewById(R.id.backBtnComments);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        detailReview = findViewById(R.id.detail_review_comment);
        detailReview.setText(review.getDescrizione());

        usernameReview = findViewById(R.id.username_review_comment);
        usernameReview.setText(review.getUser() + " ha scritto:");

        dateReview = findViewById(R.id.date_review_comment);
        dateReview.setText(review.getData());

        likeBtn = findViewById(R.id.likeBtnReview);
        contLike = findViewById(R.id.cont_like_comment);

        commentReview = findViewById(R.id.comment_review_comment);

        textComment = findViewById(R.id.textComment);
        sendComment = findViewById(R.id.send_comment);
        errorComment = findViewById(R.id.error_comment_label);
        alertReview = findViewById(R.id.alert_review_comment);

        Reaction reaction = new Reaction(this);

        if(MainActivity.utente.isAutenticato())
            likeBtn.setOnTouchListener(reaction.getReaction(review));
        else{
            likeBtn.setVisibility(View.INVISIBLE);
            contLike.setVisibility(View.INVISIBLE);
            sendComment.setVisibility(View.INVISIBLE);
            textComment.setVisibility(View.INVISIBLE);
            errorComment.setVisibility(View.VISIBLE);
            alertReview.setVisibility(View.INVISIBLE);
        }

        alertReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CommentsActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_report, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.reportItem:
                                dialog = new ReportDialog(CommentsActivity.this);
                                return dialog.showPopUp(review,"Recensione");
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textComment.getText().length() == 0) {
                    textComment.setHintTextColor(getResources().getColor(R.color.google_color));
                    Toast.makeText(CommentsActivity.this, "Il campo commento è vuoto", Toast.LENGTH_SHORT).show();
                }
                else {
                    rvComments = new RecyclerView(CommentsActivity.this);
                    textComment.setHintTextColor(getResources().getColor(R.color.light_grey));
                    sendComment(textComment.getText().toString());
                    textComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    textComment.setText(null);
                }
            }
        });
    }

    private void sendComment(String comment) {
        class CommentSender extends AsyncTask<Void, Void, String> {

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
                params.put("comment", comment);
                params.put("review", review.getId());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_COMMENT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Comment cmt = new Comment(
                                -1,
                                comment,
                                MainActivity.utente.getUsername()
                        );

                        if (CommentAdapter.comments.isEmpty()) {
                            comments.add(cmt);
                            swapAdapter(new CommentAdapter(comments, CommentsActivity.this), new LinearLayoutManager(CommentsActivity.this));
                        }
                        else {
                            commentAdapter.addItem(cmt);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        CommentSender commentSender = new CommentSender();
        commentSender.execute();
    }

    private void swapAdapter(CommentAdapter commentAdapter, LinearLayoutManager linearLayoutManager) {
        rvComments = findViewById(R.id.list_comment);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setAdapter(commentAdapter);
    }

    private void getComments(Review review) {
        class CommentsLoader extends AsyncTask<Void, Void, String> {

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
                        for (int i = 0; i < commentsJson.length(); i++) {
                            JSONObject cmt = commentsJson.getJSONObject(i);
                            Comment comment = new Comment(
                                    cmt.getInt("id"),
                                    cmt.getString("commento"),
                                    cmt.getString("username")
                            );
                            comments.add(comment);
                        }
                        commentAdapter = new CommentAdapter(comments, CommentsActivity.this);
                        rvComments.setAdapter(commentAdapter);
                    } else {
                        ArrayList<String> error = new ArrayList<String>();
                        error.add(obj.getString("message"));
                        ErrorAdapter errorAdapter = new ErrorAdapter(CommentsActivity.this, error);
                        rvComments.setAdapter(errorAdapter);
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