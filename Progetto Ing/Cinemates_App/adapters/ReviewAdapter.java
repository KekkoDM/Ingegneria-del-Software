package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.activities.CommentsActivity;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.classes.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.ReviewViewHolder>{

    Context context;
    List<Review> reviews;
    RelativeLayout container;

    public ReviewAdapter(List<Review> list, Context c){
        this.context = c;
        this.reviews = list;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout= LayoutInflater.from(context).inflate(R.layout.item_review,parent,false);
        return new ReviewViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        //holder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));
        //holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));

        holder.comment_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("recensione",reviews.get(position));
                context.startActivity(intent);
            }
        });

        holder.review_description.setText(reviews.get(position).getDescrizione());
        holder.review_date.setText(reviews.get(position).getData());
        holder.username.setText(reviews.get(position).getUser());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView username,review_description,review_date;
        ImageView img_user,comment_review;
        RelativeLayout container;


        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            review_description=itemView.findViewById(R.id.detail_review);
            review_date=itemView.findViewById(R.id.date_review);
            username=itemView.findViewById(R.id.username_review);
            comment_review = itemView.findViewById(R.id.comment_review);
        }
    }
}
