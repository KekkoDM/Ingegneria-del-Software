package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.activities.MediaDescriptorActivity;
import com.example.cinemates.classes.Media;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Media> results;

    public ResultsAdapter(ArrayList<Media> results, Context context) {
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
        Media result = results.get(position);

        if (result.getType().equals("Persona")) {
            holder.setActorResult(result);
        }
        else {
            holder.setResult(result);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MediaDescriptorActivity.class);
                    intent.putExtra("Film", results.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }

    public void updateData(ArrayList<Media> list) {
        results.clear();
        results.addAll(list);
        notifyDataSetChanged();
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
        public TextView mValutation;
        public TextView nextIcon;
        public ImageView calendarIcon;
        public ImageView starIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCover = itemView.findViewById(R.id.coverResult);
            mTitle = itemView.findViewById(R.id.titleResult);
            mDescription = itemView.findViewById(R.id.descriptionResult);
            mReleaseDate = itemView.findViewById(R.id.dateResult);
            mValutation = itemView.findViewById(R.id.typeResult);
            nextIcon = itemView.findViewById(R.id.textView11);
            calendarIcon = itemView.findViewById(R.id.imageView8);
            starIcon = itemView.findViewById(R.id.imageView5);
        }

        public void setResult(Media result) {
            if (result.getCover().equals("null")) {
                mCover.setImageResource(R.drawable.no_cover_found);
            }
            else {
                Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getCover()).into(mCover);
            }

            mTitle.setText(result.getTitle());
            mDescription.setText(result.getDescription());
            mReleaseDate.setText(result.getReleaseDate());
            mValutation.setText(result.getValutation());
        }

        public void setActorResult(Media result) {
            if (result.getCover().equals("null")) {
                mCover.setImageResource(R.drawable.no_cover_found);
            }
            else {
                Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getCover()).into(mCover);
            }

            mTitle.setText(result.getTitle());
            mValutation.setText("Attore");
            nextIcon.setVisibility(View.INVISIBLE);
            calendarIcon.setVisibility(View.INVISIBLE);
            starIcon.setVisibility(View.INVISIBLE);
        }
    }
}
