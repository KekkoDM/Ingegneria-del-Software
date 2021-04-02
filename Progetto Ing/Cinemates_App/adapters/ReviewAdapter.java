
package com.example.cinemates.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.CommentsActivity;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Reaction;
import com.example.cinemates.classes.ReportDialog;
import com.example.cinemates.classes.Review;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.handlers.RequestHandler;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfigBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import kotlin.jvm.functions.Function1;

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.ReviewViewHolder> {
    private final String[] strings = {"Mi piace", "Love", "Ahah", "Triste", "Grrr"};
    Context context;
    List<Review> reviews;
    RelativeLayout container;

    private ImageButton backBtn;
    private ReportDialog dialog;
    private RadioGroup alertGroup;
    private RadioButton radioButton;
    private Button sendAlert;
    private Review review;

    public ReviewAdapter(List<Review> list, Context c){
        this.context = c;
        this.reviews = list;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_review,parent,false);
        return new ReviewViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        //holder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));
        //holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scroll_animation));
        review = reviews.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("recensione", review);
                context.startActivity(intent);
            }
        });

        holder.comment_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("recensione", review);
                context.startActivity(intent);
            }
        });

        holder.review_description.setText(review.getDescrizione());
        holder.review_date.setText(review.getData());
        holder.username.setText(review.getUser() + " ha scritto:");

        Reaction reaction = new Reaction(context);

        if (MainActivity.utente.isAutenticato())
            holder.likeBtn.setOnTouchListener(reaction.getReaction(review));

        else{
            holder.likeBtn.setVisibility(View.INVISIBLE);
            holder.contLike.setVisibility(View.INVISIBLE);
            holder.comment_review.setVisibility(View.INVISIBLE);
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
                                return dialog.showPopUp(review,"Recensione");
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
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView username, review_description, review_date, contLike;
        private ImageView img_user, comment_review, report,likeBtn;
        private RelativeLayout container;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            review_description = itemView.findViewById(R.id.detail_review);
            review_date = itemView.findViewById(R.id.date_review);
            report = itemView.findViewById(R.id.alert_review);
            username = itemView.findViewById(R.id.username_review);
            comment_review = itemView.findViewById(R.id.comment_review);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            contLike = itemView.findViewById(R.id.cont_like);
        }
    }

}
