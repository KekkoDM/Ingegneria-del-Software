package com.example.cinemates.classes;

public class Notifica {
    private String titolo;
    private String descrizione;
    private String tipo;

    public Notifica(String titolo, String descrizione, String tipo) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tipo = tipo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
