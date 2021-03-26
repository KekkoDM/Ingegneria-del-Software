package com.example.cinemates.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.MovieDescriptorActivity;
import com.example.cinemates.adapters.DescriptionFilmAdapter;
import com.example.cinemates.adapters.FilmAdapter;
import com.example.cinemates.adapters.GeneralNotificationAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.classes.Film;
import com.example.cinemates.classes.Notifica;
import com.example.cinemates.handlers.RequestHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DescriptionFragment extends Fragment {
    private FilmAdapter filmAdapter;
    private DescriptionFilmAdapter adapter;
    private ImageView mCover;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mReleaseDate;
    private TextView mValutation;
    private ImageView mBackdrop;
    private Film film;
    private FloatingActionButton addTo, favoritesBtn, bookmarkBtn;
    private TextView favoritesTextView, bookmarkTextView;
    private Boolean menuOpen = false;
    private Boolean exist = false;
    private OvershootInterpolator interpolator = new OvershootInterpolator();

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public DescriptionFragment() {
        // blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_descriptor, container, false);

        film = ((MovieDescriptorActivity)getActivity()).getFilm();
        adapter = new DescriptionFilmAdapter(film,getContext());

        mCover = view.findViewById(R.id.detail_movie_img);
        mTitle = view.findViewById(R.id.detail_movie_title);
        mDescription = view.findViewById(R.id.detail_movie_desc);
        mReleaseDate = view.findViewById(R.id.detail_movie_realise_date);
        mValutation = view.findViewById(R.id.detail_movie_valutation);
        mBackdrop = view.findViewById(R.id.detail_movie_cover);

        if (film.getCover().equals("null")) {
            mCover.setImageResource(R.drawable.no_cover_found);
        }
        else {
            Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/w500"+film.getCover()).into(mCover);
        }

        if (film.getBackdrop().equals("null")) {
            mBackdrop.setImageResource(R.drawable.no_cover_found);
        }
        else {
            Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/w500"+film.getBackdrop()).into(mBackdrop);
        }

        mTitle.setText(film.getTitle());
        mDescription.setText(film.getDescription());
        mReleaseDate.setText("Data di rilascio: " + film.getReleaseDate());
        mValutation.setText("Valutazione: " + film.getValutation());

        showMenu(view);

        return view;
    }

    private void showMenu(View v) {
        addTo = v.findViewById(R.id.addToBtn);
        favoritesBtn = v.findViewById(R.id.addFavoritesBtn);
        bookmarkBtn = v.findViewById(R.id.addBookmarkBtn);
        favoritesTextView = v.findViewById(R.id.favoritesTextView);
        bookmarkTextView = v.findViewById(R.id.toSeeTextView);

        favoritesTextView.setAlpha(0f);
        favoritesBtn.setAlpha(0f);

        bookmarkTextView.setAlpha(0f);
        bookmarkBtn.setAlpha(0f);

        favoritesTextView.setTranslationY(100f);
        favoritesBtn.setTranslationY(100f);

        bookmarkTextView.setTranslationY(100f);
        bookmarkBtn.setTranslationY(100f);

        if (MainActivity.utente.isAutenticato()) {
            addTo.setVisibility(View.VISIBLE);
            checkExistInList(film, "Preferiti");
            checkExistInList(film, "Da vedere");
        }
        else {
            addTo.setVisibility(View.INVISIBLE);
        }

        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOpen) {
                    closeMenu();
                }
                else {
                    openMenu();
                }
            }
        });

        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoritesTextView.getText().equals("Aggiungi a Preferiti")) {
                    addToList(film, "Preferiti");
                }
                else {
                    removeFromList(film, "Preferiti");
                }
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmarkTextView.getText().equals("Aggiungi a Da Vedere")) {
                    addToList(film, "Da vedere");
                }
                else {
                    removeFromList(film, "Da vedere");
                }
            }
        });
    }

    private void removeFromList(Film film, String list) {
        class Remover extends AsyncTask<Void, Void, String> {

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
                params.put("item", film.getId());
                params.put("list", list);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.REMOVE_FROM_LIST, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        if (list.equals("Preferiti")) {
                            favoritesBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.light_grey));
                            favoritesTextView.setText("Aggiungi a Preferiti");
                        }
                        else {
                            bookmarkBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.light_grey));
                            bookmarkTextView.setText("Aggiungi a Da Vedere");
                        }
                    }

                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Remover remover = new Remover();
        remover.execute();
    }

    private void checkExistInList(Film film, String list) {
        class Checker extends AsyncTask<Void, Void, String> {

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
                params.put("item", film.getId());
                params.put("list", list);

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.CHECK_EXIST_IN_LIST, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (obj.getBoolean("error")) {
                        if (list.equals("Preferiti")) {
                            favoritesBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.favorites_button));
                            favoritesTextView.setText("Rimuovi da Preferiti");
                        }
                        else {
                            bookmarkBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.bookmark_button));
                            bookmarkTextView.setText("Rimuovi da lista Da Vedere");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Checker checker = new Checker();
        checker.execute();
    }

    private void addToList(Film film, String list) {
        class ListAdder extends AsyncTask<Void, Void, String> {

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
                params.put("list", list);
                params.put("username", MainActivity.utente.getUsername());
                params.put("item", film.getId());
                params.put("type", film.getType());

                //returing the response
                return requestHandler.sendPostRequest(CinematesDB.ADD_TO_LIST_URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        if (list.equals("Preferiti")) {
                            favoritesBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.favorites_button));
                            favoritesTextView.setText("Rimuovi da Preferiti");
                        }
                        else {
                            bookmarkBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.bookmark_button));
                            bookmarkTextView.setText("Rimuovi da lista Da Vedere");
                        }
                    }
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ListAdder listAdder = new ListAdder();
        listAdder.execute();
    }

    private void openMenu() {
        menuOpen = true;

        addTo.setImageResource(R.drawable.ic_baseline_close);

        favoritesTextView.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        favoritesBtn.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

        bookmarkTextView.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        bookmarkBtn.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void closeMenu() {
        menuOpen = false;

        addTo.setImageResource(R.drawable.ic_add);

        favoritesTextView.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        favoritesBtn.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

        bookmarkTextView.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        bookmarkBtn.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }
}