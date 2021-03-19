package com.example.cinemates_app.activities;
import com.example.cinemates.adapters.ReviewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cinemates.classes.Review;
import com.example.cinemates.R;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView rvComments;
    private TextView detailReview;
    private TextView usarnameReview;
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

        detailReview = findViewById(R.id.detail_review_comment);
        usarnameReview = findViewById(R.id.username_review_comment);
        dateReview = findViewById(R.id.date_review_comment);
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