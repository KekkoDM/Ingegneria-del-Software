package com.example.cinemates.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {
    private String id;
    private String title;
    private String cover;
    private String backdrop;
    private String description;
    private String releaseDate;
    private String valutation;
    private String type;

    public Film(String id, String cover, String backdrop, String title, String description, String releaseDate, String valutation, String type) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.valutation = valutation;
        this.backdrop = backdrop;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackdrop() { return backdrop; }

    public void setBackdrop(String backdrop) { this.backdrop = backdrop; }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getValutation() {
        return valutation;
    }

    public void setValutation(String valutation) {
        this.valutation = valutation;
    }
}
