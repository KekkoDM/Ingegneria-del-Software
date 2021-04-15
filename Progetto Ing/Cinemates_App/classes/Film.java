package com.example.cinemates.classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemates.MainActivity;
import com.example.cinemates.R;
import com.example.cinemates.adapters.ErrorAdapter;
import com.example.cinemates.api.CinematesDB;
import com.example.cinemates.fragments.DescriptionFragment;
import com.example.cinemates.fragments.FavoritesFragment;
import com.example.cinemates.handlers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Film implements Serializable {
    private String id;
    private String title;
    private String cover;
    private String backdrop;
    private String description;
    private String releaseDate;
    private String valutation;
    private String type;

    public Film(String id,String cover,String backdrop,String title, String description, String releaseDate, String valutation, String type) {
        this.id=id;
        this.cover = cover;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.valutation = valutation;
        this.backdrop = backdrop;
        setType(type);
    }

    public Film(){
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getBackdrop() { return backdrop; }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getValutation() {
        return valutation;
    }

    public String getType() { return type; }

    public void setType(String type) {
        switch (type){
            case "movie":
                this.type = "Film";
                break;

            case "tv":
                this.type = "Serie TV";
                break;

            case "person":
                this.type = "Persona";
                break;

            default:
                this.type=type;
        }
    }

    public void checkExistInList(Film film, String list) {
        class Checker extends AsyncTask<Void, Void, String> {
            DescriptionFragment descriptionFragment = DescriptionFragment.getInstance();

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
                            descriptionFragment.enableFavoritesButton();
                        }
                        else {
                            descriptionFragment.enableToSeeButton();
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

    public void loadList(String listName, Context context) {
        class ListLoader extends AsyncTask<Void, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(context);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdLoading.setMessage("\tCaricamento liste...");
                pdLoading.setCancelable(true);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", MainActivity.utente.getUsername());

                //returing the response
                if (listName.equals("Preferiti")) {
                    return requestHandler.sendPostRequest(CinematesDB.LIST_FAVORITES_URL, params);
                }
                else {
                    return requestHandler.sendPostRequest(CinematesDB.LIST_TO_SEE_URL, params);
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                int i = 0;

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        JSONArray list = obj.getJSONArray("list");
                        ArrayList<Film> listItem = new ArrayList<>();
                        RequestJson requestJson = new RequestJson(context);

                        for (i = 0; i < list.length(); i++) {
                            JSONObject film = list.getJSONObject(i);
                            Film item = new Film(
                                    film.getString("id"),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    film.getString("type")
                            );

                            listItem.add(item);

                            if (listName.equals("Preferiti")) {
                                requestJson.parseJSONSavedList(((FavoritesFragment) MainActivity.selectedFragment).getRecyclerViewFavorites(), item);
                            }
                            else {
                                requestJson.parseJSONSavedList(((FavoritesFragment) MainActivity.selectedFragment).getRecyclerViewToSee(), item);
                            }
                        }

                        ((FavoritesFragment) MainActivity.selectedFragment).setButtonVisibility(listName, i, context);

                    } else {
                        if (listName.equals("Preferiti")) {
                            ((FavoritesFragment) MainActivity.selectedFragment).setEmptyFavoritesListError();
                        }
                        else {
                            ((FavoritesFragment) MainActivity.selectedFragment).setEmptyToSeeListError();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ListLoader listLoader = new ListLoader();
        listLoader.execute();
    }
}
