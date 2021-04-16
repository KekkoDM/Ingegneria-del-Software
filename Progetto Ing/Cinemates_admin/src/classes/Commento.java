package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Commento {
    private int id;
    private String descrizione, username, recensione;

    
    public void setId(int id) {
        this.id = id;
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
    
    public void setUsername(String username) {
        this.username = username;
    }

	public void setRecensione(String recensione) {
		this.recensione = recensione;
		
	}

	public String getRecensione() {
		return recensione;
	}
	
	public void setAll(ResultSet rs) {
		try {
			while (rs.next()) {
				this.descrizione=rs.getString("testo");
				this.username=rs.getString("utente");
				this.recensione=rs.getString("recensione");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
