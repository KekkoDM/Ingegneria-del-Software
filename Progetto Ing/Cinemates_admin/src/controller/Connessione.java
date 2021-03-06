package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

//INTERFACCIA CON IL DB
public class Connessione{
	
	private Connection connect;
	private ResultSet rs;
	
	
	
	//COLLEGAMENTODB
	public Connessione() {
		
		
		String url = "jdbc:mysql://cinemates-mysql.cxnubr8ec2ra.eu-central-1.rds.amazonaws.com:3306/cinemates";
		Properties props = new Properties();
		props.setProperty("user","root");
		props.setProperty("password","unina2021");
		
		this.connect = null;
		
		try {
			this.connect = DriverManager.getConnection(url, props);
		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
	}
	
	//METODO CHE PERMETTE DI AGGIUNGERE ELEMENTI NEL DATABASE
	public void Insert (String table ,String attr,String values) {
		
		try {
			
			String query = "insert into "+ table + "(" + attr + ") values(" + values + ");";
			System.out.println("Insert: "+query);
			PreparedStatement st = connect.prepareStatement(query);
			st.executeUpdate();
		}
		catch(SQLException e) {
			System.err.println("Errore: vincolo di limite utente non rispettato");
			JOptionPane.showMessageDialog(null,"Errore Inserimento");
		}
		
	}
	
	
	//METODO CHE RECUPERA GLI ELEMENTI DAL DATABASE
	public ResultSet Query(String select,String from ,String where) {
		String query=" ";
		try {
			
			if(where.contentEquals(" ")) {
				query = "select "+select +" from "+from;
			}else {
				query = "select "+select +" from "+from + " where " + where;
			}
			PreparedStatement s = connect.prepareStatement(query);
			rs = s.executeQuery();
		}catch(SQLException e) {
			System.err.println("Errore SQL");
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	//METODO CHE PERMETTE DI ELIMINARE ELEMENTI DAL DATABASE
	public void Delete(String from, String where) {
		
		try {
			
			String query = "delete from "+ from + " where "+ where;
			PreparedStatement st = connect.prepareStatement(query);
			st.executeUpdate();
		}
		catch(SQLException e) {
			System.err.println("Errore");
			e.printStackTrace();
		}
	}
	
	
	
	//METODO CHE PERMETTE DI AGGIORNRNARE GLI ELEMENTI DEL DATABASE
	public void Update(String table,String values, String where) {
		
		try {
			
			String query = "update "+ table + " set " + values + " where "+ where;
			PreparedStatement st = connect.prepareStatement(query);
			st.executeUpdate();
		}
		catch(SQLException e) {
			System.err.println("Errore");
			e.printStackTrace();
		}
		
	}
	
	
	public void ChiudiConn() {
		try {
			connect.close();
		}catch(SQLException e) {
			System.err.println("Errore Connessione Chiusura");
			e.printStackTrace();
		}
	}
	
	
	public Connection getConn() {
		return connect;
	}
	
}
