package com.example.cinemates.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.adapters.MediaAdapter;
import com.example.cinemates.adapters.ResultsAdapter;
import com.example.cinemates.adapters.SearchUserAdapter;
import com.example.cinemates.classes.Media;
import com.example.cinemates.classes.RequestJson;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.FavoritesFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private SearchUserAdapter searchUserAdapter;
    private ResultsAdapter resultsAdapter;
    private ImageButton backBtn;
    private Intent intent;
    private Media media;
    private TextView activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        intent = getIntent();

        rv = findViewById(R.id.resultContainer);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        media = new Media();

        activityTitle = findViewById(R.id.resultFor);

        switchScreen(intent);

        backBtn = findViewById(R.id.backButton2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (intent.getStringExtra("type").equals("showall")) {
            if (intent.getStringExtra("name").equals("preferiti")) {
                media.loadList("Preferiti", MainActivity.selectedFragment.getContext());
                updateAdapter(((FavoritesFragment) MainActivity.selectedFragment).getRecyclerViewFavorites().getAdapter(), "Preferiti");
            }
            else {
                media.loadList("Da vedere", MainActivity.selectedFragment.getContext());
                updateAdapter(((FavoritesFragment) MainActivity.selectedFragment).getRecyclerViewToSee().getAdapter(), "Da vedere");
            }
        }
    }

    private void updateAdapter(RecyclerView.Adapter adapter, String listName) {
        if (adapter instanceof MediaAdapter) {
            resultsAdapter.updateData(((MediaAdapter) adapter).getListMedia());
        }
        else if (listName.equals("Preferiti")) {
            setEmptyFavoritesListError();
        }
        else {
            setEmptyToSeeListError();
        }
    }

    private void switchScreen(Intent intent){
        Bundle bundle = new Bundle();
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        switch (intent.getStringExtra("type")) {
            case "film":
                bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, intent.getStringExtra("textsearched"));
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

                RequestJson requestJson = new RequestJson(this);
                requestJson.parseJSONSearch(rv, intent.getStringExtra("textsearched"));
                break;

            case "friend":
                bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, intent.getStringExtra("friendsearched"));
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

                MainActivity.utente.searchUser(intent.getStringExtra("friendsearched"), ResultsActivity.this);
                break;

            case "sharedcontent":
                activityTitle.setText("In comune");
                String friend = intent.getStringExtra("Friend");

                bundle.putString("id_film", friend);
                bundle.putString("type", "Contenuti in comune");
                mFirebaseAnalytics.logEvent("Shared_Content", bundle);

                MainActivity.utente.showSharedContents(friend, ResultsActivity.this);
                break;

            case "showall":
                activityTitle.setText("Mostra tutto");
                ArrayList<Media> list = (ArrayList<Media>) intent.getSerializableExtra("list");
                resultsAdapter = new ResultsAdapter(list, this);
                rv.setAdapter(resultsAdapter);

                bundle.putString("user", MainActivity.utente.getUsername());
                bundle.putString("type", "Mostra tutta la lista");
                mFirebaseAnalytics.logEvent("Show_All_List", bundle);
                break;
        }
    }


    private void setEmptyFavoritesListError() {
        ArrayList<String> error = new ArrayList<String>();
        error.add("La tua lista dei Preferiti è vuota");
        ErrorAdapter errorAdapter = new ErrorAdapter(this, error);
        rv.setAdapter(errorAdapter);
    }

    public void setEmptyToSeeListError() {
        ArrayList<String> error = new ArrayList<String>();
        error.add("La tua lista dei Contenuti da vedere è vuota");
        ErrorAdapter errorAdapter = new ErrorAdapter(this, error);
        rv.setAdapter(errorAdapter);
    }

    public void showSearchUserResult(ArrayList<Utente> users) {
        searchUserAdapter = new SearchUserAdapter(this, users);
        rv.setAdapter(searchUserAdapter);
    }

    public void showSearchUserError() {
        ArrayList<String> error = new ArrayList<String>();
        error.add("La ricerca non ha prodotto risultati");
        ErrorAdapter errorAdapter = new ErrorAdapter(this, error);
        rv.setAdapter(errorAdapter);
    }

    public void showSharedContentError(String friend) {
        ArrayList<String> error = new ArrayList<String>();
        error.add("Non hai nessun elemento in comune con " + friend);
        ErrorAdapter errorAdapter = new ErrorAdapter(this, error);
        rv.setAdapter(errorAdapter);
    }

    public void updateButtonSendRequest(SearchUserAdapter.MyViewHolder holder) {
        holder.updateSendRequestButton("Richiesta inviata");
    }

    public RecyclerView getRecyclerView() {
        return rv;
    }
}