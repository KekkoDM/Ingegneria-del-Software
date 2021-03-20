package com.example.cinemates.activities;
import com.example.cinemates.adapters.ReviewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Review;
import com.example.cinemates.R;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView rvComments;
    private TextView detailReview;
    private TextView usernameReview;
    private TextView dateReview;
    private TextView contLike;
    private TextView contDislike;
    private ImageView like;
    private ImageView dislike;
    private ImageView commentReview;
    private ImageView alertComment;
    private ReviewAdapter adapter;
    private Review review;

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        review = (Review) intent.getSerializableExtra("recensione");

        detailReview = findViewById(R.id.detail_review_comment);
        detailReview.setText(review.getDescrizione());

        usernameReview = findViewById(R.id.username_review_comment);
        usernameReview.setText(review.getUser());

        dateReview = findViewById(R.id.date_review_comment);
        dateReview.setText(review.getData());

        contLike = findViewById(R.id.cont_like_comment);
        contDislike = findViewById(R.id.cont_dislike_comment);
        like = findViewById(R.id.like_comment);
        dislike = findViewById(R.id.dislike_comment);
        commentReview = findViewById(R.id.comment_review_comment);
        alertComment = findViewById(R.id.alert_comment);
        rvComments = findViewById(R.id.list_comment);
        rvComments.setHasFixedSize(true);


        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvComments.setLayoutManager(l);


    }

}