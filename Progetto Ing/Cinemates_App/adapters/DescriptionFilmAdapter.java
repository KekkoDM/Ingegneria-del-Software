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
import com.example.cinemates.classes.Film;
import com.example.cinemates.fragments.DescriptionFragment;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DescriptionFilmAdapter extends RecyclerView.Adapter<DescriptionFilmAdapter.MyViewHolder> {
    private Context context;
    private Film results;

    public DescriptionFilmAdapter(Film results,Context context) {
        this.context = context;
        this.results = results;
    }
    public DescriptionFilmAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_descriptor, parent, false);
        return new DescriptionFilmAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionFilmAdapter.MyViewHolder holder, int position) {

        Film result = results;
        holder.setResult(results);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mCover;
        public TextView mTitle;
        public TextView mDescription;
        public TextView mDuration;
        public TextView mReleaseDate;
        public TextView mValutation;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCover = itemView.findViewById(R.id.detail_movie_cover);
            mTitle = itemView.findViewById(R.id.detail_movie_title);
            mDescription = itemView.findViewById(R.id.detail_movie_desc);
            mReleaseDate = itemView.findViewById(R.id.detail_movie_realise_date);
            mValutation = itemView.findViewById(R.id.detail_movie_valutation);
        }

        public void setResult(Film result) {
            if (result.getCover().equals("null")) {
                mCover.setImageResource(R.drawable.no_cover_found);
            }
            else {
                Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getCover()).into(mCover);
            }
            mTitle.setText(result.getTitle());
            mDescription.setText(result.getDescription());
            mReleaseDate.setText(result.getReleaseDate());
            //mDuration.setText(result.getDuration());
            mValutation.setText(result.getValutation());
        }
    }

}
