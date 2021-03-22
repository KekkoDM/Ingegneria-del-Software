package com.example.cinemates_app.classes;

public class Comment {
    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    String Descrizione,Username;

    public Comment(String descrizione, String username) {
        Descrizione = descrizione;
        Username = username;
    }
}
