package com.example.cinemates.classes;

public class Review {

    String title,descrizione,data;
    int userPhoto;

    public Review() {
    }

    public Review(String title, String descrizione, String data) {
        this.title = title;
        this.descrizione = descrizione;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        descrizione = descrizione;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        data = data;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }
}
