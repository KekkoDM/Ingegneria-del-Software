package com.example.cinemates.activities;
import com.example.cinemates.MainActivity;
import com.example.cinemates.adapters.ReviewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Reaction;
import com.example.cinemates.classes.ReportDialog;
import com.example.cinemates.classes.Review;
import com.example.cinemates.R;
import com.example.cinemates.fragments.DescriptionFragment;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfigBuilder;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView rvComments;
    private TextView detailReview;
    private TextView usernameReview;
    private TextView dateReview;
    private TextView contLike;
    private TextView likeBtn;
    private ImageView commentReview;
    private ImageView alertComment;
    private EditText textComment;
    private ReviewAdapter adapter;
    private Review review;
    private ImageButton backBtn;
    private ReportDialog dialog;
    private Button sendComment;
    private TextView errorComment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        review = (Review) intent.getSerializableExtra("recensione");

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

        Reaction reaction = new Reaction(this);
        if(MainActivity.utente.isAutenticato())
            likeBtn.setOnTouchListener(reaction.getReaction(review));
        else{
            likeBtn.setVisibility(View.INVISIBLE);
            contLike.setVisibility(View.INVISIBLE);
            sendComment.setVisibility(View.INVISIBLE);
            textComment.setVisibility(View.INVISIBLE);
            errorComment.setVisibility(View.VISIBLE);
        }

        alertComment = findViewById(R.id.alert_review_comment);
        alertComment.setOnClickListener(new View.OnClickListener() {
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
                                return dialog.showPopUp();
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        rvComments = findViewById(R.id.list_comment);

        rvComments.setHasFixedSize(true);

        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvComments.setLayoutManager(l);

    }

}