package com.example.cinemates.classes;

public class Review {

    String title,Descrizione,Data;
    int userPhoto;

    public Review() {
    }

    public Review(String title, String descrizione, String data, int userPhoto) {
        this.title = title;
        Descrizione = descrizione;
        Data = data;
        this.userPhoto = userPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }
}
