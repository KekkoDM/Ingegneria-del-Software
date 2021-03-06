package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Segnalazione;
import controller.Controller;

public class Segnalazione_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String idSegnalazione, String s, Object segnalazione) {
		
		segnalazione = Controller.segnalazione;
		
		String select = "idsegnalazione";
		String where = "idsegnalazione = " + idSegnalazione;
		try {
			ResultSet rs = Controller.conn.Query(select,"segnalazione",where);
			while(rs.next()) {
				((Segnalazione) segnalazione).setId(Integer.getInteger(idSegnalazione));
				return true;
			}
				

		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return false;
	}

	
	@Override
	public ResultSet getDAO(Object segnalazione) {
		segnalazione = Controller.segnalazione;
		
		ResultSet rs = null;
		String where ="idsegnalazione= " + ((Segnalazione) segnalazione).getId();
		try {
			rs = Controller.conn.Query("*","segnalazione",where);
			
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet getAllDAO(){
		ResultSet rs = null;
		String select = "idsegnalazione,motivo,recensione,commento,utente";
		String from = "segnalazione";
		String where = "approvato = 1";
		try {
			rs = Controller.conn.Query(select,from,where);
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		} 
		
		return rs;
	}

	

}
