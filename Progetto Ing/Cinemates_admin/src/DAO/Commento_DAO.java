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
		String where = "utente = '" + utente + "' AND recensione = " + recensione;
		System.out.println(select + where);
		
		try {
			ResultSet rs = Controller.conn.Query(select,"commento",where);
			while(rs.next()){
				((Commento) commento).setUsername(utente);
				((Commento) commento).setRecensione(recensione);
					
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
		String where = "idcommento =" + ((Commento) commento).getId();
		try {
			rs = Controller.conn.Query("*", "commento", where);
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}

}
