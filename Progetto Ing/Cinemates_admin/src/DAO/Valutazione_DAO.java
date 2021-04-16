package DAO;

import java.sql.ResultSet;

import classes.Recensione;
import controller.Controller;

public class Valutazione_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String s, String s1, Object ob) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getDAO(Object recensione) {
		recensione = Controller.recensione;
		
		ResultSet rs = null;
		try {
			rs = Controller.conn.Query("count(*) as valutazioni", "valutazione", "recensione='"+ ((Recensione) recensione).getId()+"'");
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}

}
