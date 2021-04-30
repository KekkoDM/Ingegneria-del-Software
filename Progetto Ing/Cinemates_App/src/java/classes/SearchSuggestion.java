package com.example.cinemates.classes;

public class SearchSuggestion {
    private String cover;
    private String title;

    public SearchSuggestion(String cover, String title) {
        this.cover = cover;
        this.title = title;
    }

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
}
