package com.example.cinemates.activities;
import com.example.cinemates.MainActivity;
import com.example.cinemates.adapters.CommentAdapter;
import com.example.cinemates.adapters.ErrorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.classes.Commento;
import com.example.cinemates.classes.Reaction;
import com.example.cinemates.dialog.ReportDialog;
import com.example.cinemates.classes.Recensione;
import com.example.cinemates.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView rvComments;
    private CommentAdapter commentAdapter;
    public ArrayList<Commento> comments;
    private TextView detailReview;
    private TextView usernameReview;
    private TextView dateReview;
    private TextView contLike;
    private ImageView likeBtn;
    private ImageView alertReview;
    private EditText textComment;
    private Recensione review;
    private Commento comment;
    private ImageButton backBtn;
    private ReportDialog dialog;
    private Button sendComment;
    private TextView errorComment;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        review = (Recensione) intent.getSerializableExtra("recensione");

        comment = new Commento();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        rvComments = findViewById(R.id.list_comment);
        rvComments.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(CommentsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComments.setLayoutManager(linearLayoutManager);

        comments = new ArrayList<>();
        comment.getComments(review, CommentsActivity.this);

        // [START custom_event]
        Bundle params = new Bundle();
        params.putString("id_review", review.getId());
        params.putString("writer", review.getUser());
        params.putString("reader", MainActivity.utente.getUsername());
        mFirebaseAnalytics.logEvent("Description_Review", params);

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

        textComment = findViewById(R.id.textComment);
        sendComment = findViewById(R.id.send_comment);
        errorComment = findViewById(R.id.error_comment_label);
        alertReview = findViewById(R.id.alert_review_comment);

        if(MainActivity.utente.isAutenticato()) {
            Reaction reaction = new Reaction(this);
            reaction.getReaction(review, likeBtn, contLike);
            likeBtn.setOnTouchListener(reaction.showReaction(review, likeBtn, contLike));
        }
        else {
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
                    MainActivity.utente.sendComment(textComment.getText().toString(), review, CommentsActivity.this);

                    // [START custom_event]
                    Bundle params = new Bundle();
                    params.putString("id_review", review.getId());
                    params.putString("writer", MainActivity.utente.getUsername());
                    mFirebaseAnalytics.logEvent("Comment_Writed", params);

                    textComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    textComment.setText(null);
                }
            }
        });
    }

    private void swapAdapter(CommentAdapter adapter, LinearLayoutManager linearLayoutManager) {
        commentAdapter = adapter;
        rvComments = findViewById(R.id.list_comment);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setAdapter(commentAdapter);
    }

    public void showComments(ArrayList<Commento> comments) {
        this.comments = comments;
        commentAdapter = new CommentAdapter(comments, this);
        rvComments.setAdapter(commentAdapter);
    }

    public void showNoCommentsError(String errorMessage) {
        ArrayList<String> error = new ArrayList<String>();
        error.add(errorMessage);
        ErrorAdapter errorAdapter = new ErrorAdapter(this, error);
        rvComments.setAdapter(errorAdapter);
    }

    public void postComment(Commento comment) {
        if (comments.isEmpty()) {
            comments.add(comment);
            swapAdapter(new CommentAdapter(comments, this), new LinearLayoutManager(this));
        }
        else {
            commentAdapter.addItem(comment);
        }
    }
}