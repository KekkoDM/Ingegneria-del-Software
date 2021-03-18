package com.example.cinemates.classes;

import java.io.Serializable;

public class Review implements Serializable {

    String title;
    String descrizione;



    String data;
    String id;
    String user;
    int userPhoto;

    public Review() {
    }

    public Review(String id,String user, String descrizione, String data) {
        this.id =id;
        this.user = user;
        this.descrizione = descrizione;
        this.data = data;
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

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
