package controller;

import DAO.DAO_Interface;
import GUI.Dashboard;
import GUI.Login;
import GUI.Popup;


public class Controller {
    public static Connessione conn;
    public static Amministatore admin;
    private static Login login;
    private static Dashboard dashboard;
    private static Popup popup;
    
	public static void main(String[] args) {
		
		Controller ctr = new Controller();
		
	}
	
	public Controller() {
		conn = new Connessione();
		openLogin();
	}
	
	public Boolean checkExists(String usr, String pw,DAO_Interface classDAO) {
		return classDAO.checkExists(usr, pw, admin);
	}
	
	public void getAll(DAO_Interface classDAO) {
		
		admin.getAll(classDAO.getDAO(admin));
	}
	
	public void openLogin() {
		admin = new Amministatore();
		login = new Login(this);
		login.setVisible(true);
	}

	public void openDashboard() {
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
