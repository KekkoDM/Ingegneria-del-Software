package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Amministratore;
import controller.Controller;

public class Amministratore_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String usrname, String pw, Object admin) {
		
		admin = Controller.admin;
		
		String select = "username,password";
		String where = "username = '" + usrname + "' AND password = '" + pw + "'";
		System.out.println(select + where);
		try {
			ResultSet rs = Controller.conn.Query(select,"amministratore",where);
			while(rs.next()) {
				((Amministratore) admin).setUsername(usrname);
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
		System.out.println(this);
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

}
