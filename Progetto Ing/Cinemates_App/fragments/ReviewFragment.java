package com.example.cinemates.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemates.R;
import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.classes.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {

    private Dialog alert;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> mData;
    private TextView username;
    private TextView titleReview;
    private TextView detailReview;
    private TextView dateReview;
    private ImageView alertImg;
    private ImageView like;
    private ImageView dislike;
    private ImageView comment;
    private TextView contatorLike;
    private TextView contatorDislike;

    private Review review;

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        mData = new ArrayList<>();
        alert = new Dialog(getContext());
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        username = view.findViewById(R.id.username_review);
        titleReview = view.findViewById(R.id.title_review);
        detailReview = view.findViewById(R.id.detail_review);
        dateReview = view.findViewById(R.id.date_review);
        alertImg = view.findViewById(R.id.alert);
        like = view.findViewById(R.id.like2);
        dislike = view.findViewById(R.id.dislike2);
        comment = view.findViewById(R.id.comment_review1);
        contatorLike = view.findViewById(R.id.contLike1);
        contatorDislike = view.findViewById(R.id.contDislike1);
        reviewRecyclerView = view.findViewById(R.id.review_rv);
        reviewRecyclerView.setHasFixedSize(true);

        LinearLayoutManager l = new LinearLayoutManager(this.getContext());
        l.setOrientation(LinearLayoutManager.HORIZONTAL);

        return view;
    }
    public void showDialog(View v){
        Button btnOk;
        ImageButton btnClose = null;
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }
}