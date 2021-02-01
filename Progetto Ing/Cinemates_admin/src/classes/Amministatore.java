package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Amministatore {
	private int idAmministratore;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	
	public int getID() {
		return idAmministratore;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	public void setAll(ResultSet rs) {
		try {
			while (rs.next()) {
				this.nome=rs.getString("nome");
				this.cognome=rs.getString("cognome");
				this.idAmministratore=rs.getInt("idamministratore");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean aggiornaPassword(String oldPw, String newPw) {
		if(oldPw.equals(newPw))
			return false;
		setPassword(newPw);
		return true;
	}
	
}
