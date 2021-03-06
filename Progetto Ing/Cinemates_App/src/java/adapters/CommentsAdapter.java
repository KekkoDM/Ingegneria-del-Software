package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.R;
import com.example.cinemates.classes.Commento;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter <CommentsAdapter.Commentsviewholder> {
    Context mContext;
    List<Commento> mData;
    RelativeLayout container;

    public CommentsAdapter(List<Commento> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public Commentsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new Commentsviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Commentsviewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class Commentsviewholder extends RecyclerView.ViewHolder{

        public Commentsviewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
