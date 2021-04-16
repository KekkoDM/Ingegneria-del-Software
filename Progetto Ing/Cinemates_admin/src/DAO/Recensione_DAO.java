package DAO;

import java.sql.ResultSet;

import classes.Recensione;
import controller.Controller;

public class Recensione_DAO implements DAO_Interface {
	


	@Override
	public Boolean checkExists(String idrecensione, String s, Object recensione) {
		
		recensione = Controller.recensione;
		
		String select = "idrecensione";
		String where = "idrecensione="+Integer.getInteger(idrecensione);
		
		try {
			ResultSet rs = Controller.conn.Query(select, "recensione", where);
			while(rs.next()) {
				((Recensione) recensione).setId(rs.getString(1));
				return true;
			}
			
		}catch (Exception e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getDAO(Object recensione) {
		recensione = Controller.recensione;
		
		ResultSet rs = null;
		try {
			rs = Controller.conn.Query("*", "recensione", "idrecensione="+ ((Recensione) recensione).getId());
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}

}
