package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Segnalazione {
	
	private int id;
	private String tipo;
	private int recensione;
	private int admin;
	private String utente;
	private String segnalatore;
	private ArrayList<Segnalazione> reports;

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getRecensione() {
		return recensione;
	}

	public void setRecensione(int recensione) {
		this.recensione = recensione;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
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

	public ArrayList<Segnalazione> getReports() {
		return reports;
	}

	public void setReports(ResultSet rs) {
		reports = new ArrayList<Segnalazione>();
		try {
			while(rs.next()) {
				Segnalazione s = new Segnalazione();
				
				s.setId(rs.getInt("idsegnalazione"));
				s.setRecensione(rs.getInt("recensione"));
				s.setTipo(rs.getString("tipo"));
				s.setUtente(rs.getString("utente"));
				
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
				this.setRecensione(rs.getInt("recensione"));
				this.setTipo(rs.getString("tipo"));
				this.setUtente("prova");
				this.setSegnalatore("prova");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
