package controller;

import DAO.Amministratore_DAO;
import DAO.DAO_Interface;
import GUI.Dashboard;
import GUI.Login;
import GUI.Popup;
import classes.Amministatore;
import classes.Commento;
import classes.Notifica;


public class Controller {
    public static Connessione conn;
    public static Amministatore admin;
    public static Commento commento;
    public static Notifica notifica;
    public static Amministratore_DAO adminDAO;
    private static Login login;
    private static Dashboard dashboard;
    private static Popup popup;
    
	public static void main(String[] args) {
		
		Controller ctr = new Controller();
		ctr.openLogin();
	}
	
	public Controller() {
		conn = new Connessione();
	}
	
	public Boolean checkExists(String usr, String pw,DAO_Interface classDAO) {
		return classDAO.checkExists(usr, pw, classDAO);
	}
	
//	public void setAllAdmin(DAO_Interface classDAO) {
//		admin.setAll(classDAO.getDAO(admin));
//	}
	
	
	public void openLogin() {
		admin = new Amministatore();
		login = new Login(this);
		login.setVisible(true);
	}
	

	public void openDashboard(DAO_Interface classDAO) {
		login.dispose();
		dashboard = new Dashboard(this);
		dashboard.setVisible(true);
	}
	
	
	public void closeDashboard() {
		dashboard.dispose();
	}
	
	
	public void showPopup(String s) {
		popup=new Popup(this, s);
		popup.setVisible(true);
	}
	
	
}
