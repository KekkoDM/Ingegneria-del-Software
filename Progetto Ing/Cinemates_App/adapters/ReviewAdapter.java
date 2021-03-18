package com.example.cinemates.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.activities.RecoveryPasswordActivity;
import com.example.cinemates.classes.Review;

import java.io.Serializable;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.ReviewViewHolder>{

    Context mContext;
    List<Review> mData;


    public ReviewAdapter(List<Review> list,Context c){
        mContext=c;
        mData = list;
    }

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
        holder.img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecoveryPasswordActivity.class);
                intent.putExtra("recensione", (Serializable) mData.get(position));
                mContext.startActivity(intent);
            }
        });
        //holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView review_title,review_description,review_date,username_review;
        ImageView img_user,img_comment;
        RelativeLayout container;


        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            review_description=itemView.findViewById(R.id.detail_review);
            review_date=itemView.findViewById(R.id.date_review);
            img_user=itemView.findViewById(R.id.image_user);
            img_comment = itemView.findViewById(R.id.comment_review);
            username_review=itemView.findViewById(R.id.username_review);
        }

        void setReview(Review review){

            review_description.setText(review.getDescrizione());
            review_date.setText(review.getData());
            username_review.setText(review.getUser());
            //img_user.setImageResource(review.getUserPhoto());
        }
    }
}
