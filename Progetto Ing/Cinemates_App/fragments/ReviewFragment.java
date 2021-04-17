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
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.adapters.ReviewAdapter;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.classes.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    private Dialog alert;
    private RecyclerView reviewRecyclerView;
    private Film film;
    private Review review;
    private ReviewAdapter reviewAdapter;
    private static ReviewFragment instance;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        instance = this;

        alert = new Dialog(getContext());
        film = ((MovieDescriptorActivity)getActivity()).getFilm();

        reviewRecyclerView = view.findViewById(R.id.review_rv);
        reviewRecyclerView.setHasFixedSize(true);
        LinearLayoutManager l = new LinearLayoutManager(this.getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        reviewRecyclerView.setLayoutManager(l);

        RequestJson requestJson = new RequestJson(getContext());
        requestJson.parseJSONReviews(reviewRecyclerView, film.getId(), film.getType());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (reviewAdapter != null) {
            reviewAdapter.notifyDataSetChanged();
        }
    }

    public Review getReview() { return review; }

    public void setReview(Review review) { this.review = review; }

    public void setReviewAdapter(ReviewAdapter reviewAdapter) {
        this.reviewAdapter = reviewAdapter;
    }

    public static ReviewFragment getInstance() {
        return instance;
    }
}