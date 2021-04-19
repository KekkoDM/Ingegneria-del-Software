package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Notifica;
import controller.Controller;

public class Notifica_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String utente, String id, Object notifica) {
		System.out.println("classe Notifica_DAO");
		notifica = Controller.notifica;
		int idnotifica = Integer.getInteger(id);
		
		String select = "utente,idnotifica";
		String where = "utente = '" + utente + "' AND idnotifica = " + idnotifica;
		
		System.out.println(select + where);
		try {
			ResultSet rs = Controller.conn.Query(select,"notifica",where);
			if(rs.wasNull()) 
				return false;
			else {
				((Notifica) notifica).setMittente(utente);
				((Notifica) notifica).setIdNotifica(idnotifica);
					
				return true;
			}
		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return false;
	}
		

	@Override
	public ResultSet getDAO(Object notifica) {
		notifica = Controller.notifica;
		
		ResultSet rs = null;
		String where = "idnotifica = " + ((Notifica) notifica).getIdNotifica();
		try {
			rs = Controller.conn.Query("*", "notifica",where);
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendReportDecision() {
		String attr = "titolo, tipo, descrizione, amministratore, destinatario";
		Controller.conn.Insert("notifica", attr, Controller.notifica.getAllInOne());
	}

}
