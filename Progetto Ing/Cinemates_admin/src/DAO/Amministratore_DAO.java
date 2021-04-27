package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Amministratore;
import classes.Segnalazione;
import controller.Controller;

public class Amministratore_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String username, String pw, Object admin) {
		
		admin = Controller.admin;
		
		String select = "username,password";
		String where = "username = '" + username + "' AND password = '" + pw + "'";
		System.out.println(select + where);
		try {
			ResultSet rs = Controller.conn.Query(select,"amministratore",where);
			while(rs.next()) {
				((Amministratore) admin).setUsername(username);
				((Amministratore) admin).setPassword(pw);
				return true;
			}
				

		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void updatePassword(String newPw) {
		String values = "password='"+newPw+"'";
		String where = "idamministratore="+Controller.admin.getID();
		Controller.conn.Update("amministratore", values, where);
	}
	

	@Override
	public ResultSet getDAO(Object admin) {
		admin = Controller.admin;
		
		ResultSet rs = null;
		try {
			rs = Controller.conn.Query("*", "amministratore", "username='"+ ((Amministratore) admin).getUsername() + "'");
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void approva(Segnalazione s) {
		String where = "idsegnalazione="+s.getId();
		Controller.conn.Delete("segnalazione", where);	
	}
	
	public void disapprova(Segnalazione s) {
	
		if(Controller.commento != null) {
			String where = "idcommento="+Controller.commento.getId();
			Controller.conn.Delete("commento", where);
			
		}
		else if(Controller.recensione != null) {
			String where = "recensione='"+s.getRecensione()+"'";
			Controller.conn.Update("segnalazione", "approvato = false", where);
		}
		
	}

}
