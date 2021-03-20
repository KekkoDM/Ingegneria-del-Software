package com.example.cinemates.activities;
import com.example.cinemates.adapters.ReviewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Review;
import com.example.cinemates.R;
import com.example.cinemates.fragments.DescriptionFragment;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfigBuilder;

public class CommentsActivity extends AppCompatActivity {
    private final String[] strings = {"like", "love", "ahah", "wow", "triste", "argh!"};
    private RecyclerView rvComments;
    private TextView detailReview;
    private TextView usernameReview;
    private TextView dateReview;
    private TextView contLike;
    private TextView likeBtn;
    private ImageView commentReview;
    private ImageView alertComment;
    private ReviewAdapter adapter;
    private Review review;
    private ImageButton backBtn;
    private Dialog dialog;

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }


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

        alertComment = findViewById(R.id.alert_comment);
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
                                dialog = new Dialog(CommentsActivity.this);
                                return showPopup();
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

        sampleCenterLeft();
    }

    private boolean showPopup() {
        ImageView close;

        close = findViewById(R.id.closealert);

        dialog.setContentView(R.layout.popup_report);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return true;
    }

    private void sampleCenterLeft() {
        ReactionPopup popup = new ReactionPopup(
                this,
                new ReactionsConfigBuilder(this)
                        .withReactions(new int[]{
                                R.drawable.ic_like,
                                R.drawable.ic_love,
                                R.drawable.ic_laugh,
                                R.drawable.ic_wow,
                                R.drawable.ic_sad,
                                R.drawable.ic_angry,
                        })
                        .withReactionTexts(position -> strings[position])
                        .build());

        findViewById(R.id.likeBtnReview).setOnTouchListener(popup);
    }
}