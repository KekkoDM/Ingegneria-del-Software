package com.example.cinemates.classes;

public class Comment {
    private int id;
    private String descrizione, username;

    public Comment(int id, String descrizione, String username) {
        this.id = id;
        this.descrizione = descrizione;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getUsername() {
        return username;
    }
}
