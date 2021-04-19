package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Segnalazione {
	
	private int id;
	private String tipo;
	private String recensione;
	private int commento;
	private String motivo;
	private String utente;
	private String segnalatore;
	private ArrayList<Segnalazione> reports;

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRecensione() {
		return recensione;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public int getId() {
		return id;
	}

	public void setId(int idSegnalazione) {
		this.id = idSegnalazione;
	}

	public String getSegnalatore() {
		return segnalatore;
	}

	public void setSegnalatore(String segnalatore) {
		this.segnalatore = segnalatore;
	}
	
	public int getCommento() {
		return commento;
	}

	public void setCommento(int commento) {
		this.commento = commento;
	}

	public ArrayList<Segnalazione> getReports() {
		return reports;
	}

	public void setReports(ResultSet rs) {
		reports = new ArrayList<Segnalazione>();
		try {
			while(rs.next()) {
				Segnalazione s = new Segnalazione();
				
				s.setId(rs.getInt("idsegnalazione"));
				s.setRecensione(rs.getString("recensione"));
				s.setTipo(rs.getString("tipo"));
				s.setUtente(rs.getString("utente"));
				s.setCommento(rs.getInt("commento"));
				
				reports.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void setAll(ResultSet rs) {
		try {
			while (rs.next()) {
				this.setId(rs.getInt("idsegnalazione"));
				this.setRecensione(rs.getString("recensione"));
				this.setTipo(rs.getString("tipo"));
				this.setSegnalatore(rs.getString("utente"));
				this.setMotivo(rs.getString("motivo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
}
