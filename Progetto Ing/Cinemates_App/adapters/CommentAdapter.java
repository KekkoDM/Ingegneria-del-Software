
package com.example.cinemates.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.CommentsActivity;
import com.example.cinemates.classes.Comment;
import com.example.cinemates.classes.ReportDialog;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.MyViewHolder> {
    private Context context;
    public static List<Comment> comments;
    private ReportDialog dialog;

    public CommentAdapter(List<Comment> comments, Context context){
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.comment_cardview,parent,false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.setComment(comment);

        if (!MainActivity.utente.isAutenticato()) {
            holder.report.setVisibility(View.INVISIBLE);
        }

        if (MainActivity.utente.isAutenticato() && comment.getUsername().equals(MainActivity.utente.getUsername())) {
            holder.report.setVisibility(View.INVISIBLE);
        }

        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_report, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.reportItem:
                                dialog = new ReportDialog(context);
                                return dialog.showPopUp(comment,"Commento");
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user;
        public TextView textComment;
        public ImageView report;

        public MyViewHolder(View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.username_comment);
            textComment = itemView.findViewById(R.id.detail_comment);
            report = itemView.findViewById(R.id.alert_comment);
        }

        public void setComment(Comment comment) {
            user.setText(comment.getUsername() + " ha commentato:");
            textComment.setText(comment.getDescrizione());
        }
    }
}
