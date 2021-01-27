package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.net.httpserver.Authenticator.Result;

import controller.Amministatore;
import controller.Controller;

public class Amministratore_DAO implements DAO_Interface {

	@Override
	public Boolean checkExists(String usrname, String pw, Object admin) {
		System.out.println("classe DAO");
		admin = Controller.admin;
		
		String select = "username,password";
		String where = "username = '" + usrname + "' AND password = '" + pw + "'";
		System.out.println(select + where);
		try {
			ResultSet rs = Controller.conn.Query(select,"amministratore",where);
			while(rs.next()) {
				if(usrname.equals(rs.getString(1))&& pw.equals(rs.getString(2))) {
					
					((Amministatore) admin).setUsername(usrname);
					((Amministatore) admin).setPassword(pw);
					
					
					return true;
				}
					
				else
					return false;
			}	
		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		return false;
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
