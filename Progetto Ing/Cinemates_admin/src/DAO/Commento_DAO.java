package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Commento;
import controller.Controller;

public class Commento_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String utente, String recensione, Object commento) {
		System.out.println("classe Commento_DAO");
		commento = Controller.commento;
		
		String select = "utente,recensione";
		String where = "utente = '" + utente + "' AND recensione = " + Integer.getInteger(recensione);
		System.out.println(select + where);
		
		try {
			ResultSet rs = Controller.conn.Query(select,"commento",where);
			while(rs.next()){
				((Commento) commento).setUtente(utente);
				((Commento) commento).setRecensione(Integer.getInteger(recensione));
					
				return true;
			}

		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return false;
	}
	
	

	@Override
	public ResultSet getDAO(Object commento) {
		commento = Controller.commento;
		
		ResultSet rs = null;
		String where = "utente = '" + ((Commento) commento).getUtente() + "' AND recensione = " + ((Commento) commento).getRecensione();
		try {
			rs = Controller.conn.Query("testo", "commento", where);
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}

}
