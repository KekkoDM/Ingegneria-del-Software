package com.example.cinemates.classes;

public class Result {
    private String cover;
    private String title;
    private String description;
    private String releaseDate;
    private String type;

    public Result(String cover, String title, String description, String releaseDate, String type) {
        this.cover = cover;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
