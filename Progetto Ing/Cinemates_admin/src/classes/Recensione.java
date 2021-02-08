package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Recensione {
	
	private int idRecensione;
	private String titolo;
	private String descrizione;
	private int valPositive;
	private int valNegative;
	private int valTotali;
	private String utenteProp;
	private int film;
	
	public int getIdRecensione() {
		return idRecensione;
	}
	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
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
	public String getUtenteProp() {
		return utenteProp;
	}
	public void setUtenteProp(String utenteProp) {
		this.utenteProp = utenteProp;
	}
	public int getValPositive() {
		return valPositive;
	}
	public void setValPositive(int valPositive) {
		this.valPositive = valPositive;
	}
	public int getValNegative() {
		return valNegative;
	}
	public void setValNegative(int valNegative) {
		this.valNegative = valNegative;
	}
	public int getValTotali() {
		return valTotali;
	}
	public void setValTotali(int valTotali) {
		this.valTotali = valTotali;
	}
	public int getFilm() {
		return film;
	}
	public void setFilm(int film) {
		this.film = film;
	}
	
	public void setAll(ResultSet rs) {
		try {
			while (rs.next()) {
				this.utenteProp=rs.getString("utente");
				this.titolo=rs.getString("titolo");
				this.descrizione=rs.getString("descrizione");
				this.valPositive=rs.getInt("val_positive");
				this.valNegative=rs.getInt("val_negative");
				this.valTotali=rs.getInt("val_totali");
				this.film=rs.getInt("film");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
