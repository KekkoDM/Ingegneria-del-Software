package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.classes.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Result> results;

    public ResultsAdapter(Context context, ArrayList<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ResultsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.result_cardview, parent, false);
        return new ResultsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Result result = results.get(position);
        holder.setResult(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mCover;
        public TextView mTitle;
        public TextView mDescription;
        public TextView mReleaseDate;
        public TextView mType;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCover = itemView.findViewById(R.id.coverResult);
            mTitle = itemView.findViewById(R.id.titleResult);
            mDescription = itemView.findViewById(R.id.descriptionResult);
            mReleaseDate = itemView.findViewById(R.id.dateResult);
            mType = itemView.findViewById(R.id.typeResult);
        }

        public void setResult(Result result) {
            if (result.getCover().equals("null")) {
                mCover.setImageResource(R.drawable.no_cover_found);
            }
            else {
                Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getCover()).into(mCover);
            }

            mTitle.setText(result.getTitle());
            mDescription.setText(result.getDescription());
            mReleaseDate.setText(result.getReleaseDate());
            mType.setText(result.getType());
        }
    }
}