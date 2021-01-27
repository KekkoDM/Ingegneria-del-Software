package DAO;

import java.sql.ResultSet;

import controller.Amministatore;

public interface DAO_Interface {
	
	public Boolean checkExists(String s, String s1, Object ob);
	public ResultSet getDAO(Object object);
	
}
