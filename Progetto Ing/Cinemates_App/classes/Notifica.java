package com.example.cinemates.classes;

public class Notifica {
    private int id;
    private String titolo;
    private String descrizione;
    private String tipo;
    private String mittente;
    private String destinatario;
    private String amministratore;

    public Notifica(int id, String titolo, String descrizione, String tipo, String mittente, String destinatario, String amministratore) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.amministratore = amministratore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(String amministratore) {
        this.amministratore = amministratore;
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
