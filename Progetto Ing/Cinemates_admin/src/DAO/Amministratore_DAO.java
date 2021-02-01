package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Amministatore;
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
				((Amministatore) admin).setUsername(usrname);
				((Amministatore) admin).setPassword(pw);
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
		String where = "username='"+Controller.admin.getUsername()+"'";
		Controller.conn.Update("amministratore", values, where);
	}
	

	@Override
	public ResultSet getDAO(Object admin) {
		admin = Controller.admin;
		
		ResultSet rs = null;
		try {
			rs = Controller.conn.Query("*", "amministratore", "username='"+ ((Amministatore) admin).getUsername() + "'");
		}catch(Error e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}

}
