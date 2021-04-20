package com.example.cinemates.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.activities.MediaDescriptorActivity;
import com.example.cinemates.adapters.DescriptionFilmAdapter;
import com.example.cinemates.classes.Media;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DescriptionFragment extends Fragment {
    private DescriptionFilmAdapter adapter;
    private ImageView mCover;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mReleaseDate;
    private TextView mValutation;
    private ImageView mBackdrop;
    private Media media;
    private FloatingActionButton addTo, favoritesBtn, bookmarkBtn;
    private TextView favoritesTextView, bookmarkTextView;
    private Boolean menuOpen = false;
    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private static DescriptionFragment instance;

    public DescriptionFragment() {
        // blank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_descriptor, container, false);

        instance = this;

        media = ((MediaDescriptorActivity) getActivity()).getMedia();
        adapter = new DescriptionFilmAdapter(media, getContext());

        mCover = view.findViewById(R.id.detail_movie_img);
        mTitle = view.findViewById(R.id.detail_movie_title);
        mDescription = view.findViewById(R.id.detail_movie_desc);
        mReleaseDate = view.findViewById(R.id.detail_movie_realise_date);
        mValutation = view.findViewById(R.id.detail_movie_valutation);
        mBackdrop = view.findViewById(R.id.detail_movie_cover);


        if (media.getCover().equals("null")) {
            mCover.setImageResource(R.drawable.no_cover_found);
        }
        else {
            Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/w500"+ media.getCover()).into(mCover);
        }

        if (media.getBackdrop().equals("null")) {
            mBackdrop.setImageResource(R.drawable.no_cover_found);
        }
        else {
            Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/w500"+ media.getBackdrop()).into(mBackdrop);
        }

        mTitle.setText(media.getTitle());
        mDescription.setText(media.getDescription());
        mReleaseDate.setText("Data di rilascio: " + media.getReleaseDate());
        mValutation.setText("Valutazione: " + media.getValutation());

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
            media.checkExistInList(media, "Preferiti");
            media.checkExistInList(media, "Da vedere");
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
                    MainActivity.utente.addToList(media, "Preferiti", getContext());
                }
                else {
                    MainActivity.utente.removeFromList(media, "Preferiti", getContext());
                }
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmarkTextView.getText().equals("Aggiungi a Da Vedere")) {
                    MainActivity.utente.addToList(media, "Da vedere", getContext());
                }
                else {
                    MainActivity.utente.removeFromList(media, "Da vedere", getContext());
                }
            }
        });
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

    public void enableFavoritesButton() {
        favoritesBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.favorites_button));
        favoritesTextView.setText("Rimuovi da Preferiti");
    }

    public void enableToSeeButton() {
        bookmarkBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.bookmark_button));
        bookmarkTextView.setText("Rimuovi da lista Da Vedere");
    }

    public void disableFavoritesButton() {
        favoritesBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.light_grey));
        favoritesTextView.setText("Aggiungi a Preferiti");
    }

    public void disableToSeeButton() {
        bookmarkBtn.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.light_grey));
        bookmarkTextView.setText("Aggiungi a Da Vedere");
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public static DescriptionFragment getInstance() {
        return instance;
    }
}