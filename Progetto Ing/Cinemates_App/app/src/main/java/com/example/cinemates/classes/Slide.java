package com.example.cinemates.classes;

import java.util.SplittableRandom;

public class Slide {
    private String title;
    private String cover;

    public Slide(String title, String cover) {
        this.title = title;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
