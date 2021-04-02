
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
                                return dialog.showPopUp();
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        holder.mainLikeBtn.setOnTouchListener(getReaction(review));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView username, review_description, review_date, mainLikeBtn;
        private ImageView img_user, comment_review, report;
        private RelativeLayout container;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            review_description = itemView.findViewById(R.id.detail_review);
            review_date = itemView.findViewById(R.id.date_review);
            report = itemView.findViewById(R.id.alert_review);
            username = itemView.findViewById(R.id.username_review);
            comment_review = itemView.findViewById(R.id.comment_review);
            mainLikeBtn = itemView.findViewById(R.id.likeBtnMain);
        }
    }

    // show reactions popup
    private ReactionPopup getReaction(Review review) {
        ReactionPopup popup = new ReactionPopup(
                context,
                new ReactionsConfigBuilder(context)
                        .withReactions(new int[]{
                                R.drawable.ic_like,
                                R.drawable.ic_love,
                                R.drawable.ic_laugh,
                                R.drawable.ic_sad,
                                R.drawable.ic_angry,
                        })
                        .withReactionTexts(position -> strings[position])
                        .build());

        popup.setReactionSelectedListener((position) -> {
            if (position > -1) {
                sendReaction(position, review);
            };
            return position >= -1;
        });

        return popup;
    }

    private void sendReaction(Integer position, Review review) {
        class ReactionSender extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());
                params.put("review", review.getId());
                params.put("reaction", strings[position]);

                //returning the response
                return requestHandler.sendPostRequest(CinematesDB.SEND_REACTION, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    /*if no error in response
                    if (!obj.getBoolean("error")) {

                    }*/
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ReactionSender reactionSender = new ReactionSender();
        reactionSender.execute();
    }
}
