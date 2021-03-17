package com.example.cinemates.adapters;

import android.content.Context;
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
import com.example.cinemates.classes.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.ReviewViewHolder>{

    Context mContext;
    List<Review>mData;
    RelativeLayout container;

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout= LayoutInflater.from(mContext).inflate(R.layout.item_review,parent,false);
        return new ReviewViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.setReview(mData.get(position));
        holder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView review_title,review_description,review_date;
        ImageView img_user;
        RelativeLayout container;


        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            review_title=itemView.findViewById(R.id.title_review);
            review_description=itemView.findViewById(R.id.detail_review);
            review_date=itemView.findViewById(R.id.date_review);
            img_user=itemView.findViewById(R.id.img_user);
        }

        void setReview(Review review){
            review_title.setText(review.getTitle());
            review_description.setText(review.getDescrizione());
            review_date.setText(review.getData());
            img_user.setImageResource(review.getUserPhoto());
        }
    }
}
