package controller;

import java.util.ArrayList;

import DAO.Amministratore_DAO;
import DAO.DAO_Interface;
import DAO.Notifica_DAO;
import DAO.Recensione_DAO;
import DAO.Segnalazione_DAO;
import GUI.Dashboard;
import GUI.Login;
import GUI.Popup;
import GUI.Visualizzazione;
import classes.Amministratore;
import classes.Commento;
import classes.Notifica;
import classes.Recensione;
import classes.Segnalazione;


public class Controller {
    public static Connessione conn;
    public static Amministratore admin;
    public static Commento commento;
    public static Notifica notifica;
    public static Recensione recensione;
    public static Segnalazione segnalazione;
    public static Notifica_DAO notification_DAO;
    public static Recensione_DAO reviewDAO;
    public static Amministratore_DAO adminDAO;
    public static Segnalazione_DAO reportDAO;

    
    private static Login login;
    private static Dashboard dashboard;
    private static Popup popup;
    private static Visualizzazione visualizza;
    
	public static void main(String[] args) {
		
		Controller ctr = new Controller();
		ctr.openLogin();
	}
	
	
	public Controller() {
		conn = new Connessione();
		admin = new Amministratore();
		commento = new Commento();
		notifica = new Notifica();
		notification_DAO = new Notifica_DAO();
		adminDAO = new Amministratore_DAO();
		reportDAO = new Segnalazione_DAO();
		segnalazione = new Segnalazione();
		reviewDAO = new Recensione_DAO();
		recensione = new Recensione();
	}
	
	
	public Boolean checkExists(String usr, String pw,DAO_Interface classDAO) {
		return classDAO.checkExists(usr, pw, classDAO);
	}
	
	
	public void openLogin() {
		admin = new Amministratore();
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
	
	
	public void showPopup(String msg, String ctx) {
		popup = new Popup(this, msg, ctx);
		popup.setVisible(true);
	}
	
	
	public ArrayList<Segnalazione> getSegnalazioni(){
		segnalazione.setReports(reportDAO.getAllDAO());
		return segnalazione.getReports();
	}
	
	
	public Recensione getRecensione(int idRecensione, String utente) {
		reviewDAO.checkExists(String.valueOf(idRecensione), utente, recensione);
		recensione.setAll(reviewDAO.getDAO(recensione));
		return recensione;
	}
	
	 
	public void sendReportResult(Boolean decisione){
		recensione.setAll(reviewDAO.getDAO(recensione));
		segnalazione.setAll(reportDAO.getDAO(segnalazione));
		
		String messaggio = null;
		if(decisione) {
			messaggio ="La tua recensione non viola nessuna regola del sito, pertanto è stata approvata";
			reportDAO.approva(segnalazione);
		}else {
			messaggio ="La tua recensione viola le politiche del sito, pertanto è stata cancellata";
			reportDAO.disapprova(segnalazione);
		}
		notifica.createNotifica(messaggio);
		notification_DAO.sendReportDecision();
	}
	
	public void showReports() {
		dashboard.reviewsPanelStart(this);
	}
	
	public void showReview() {
		recensione.setAll(reviewDAO.getDAO(recensione));
		segnalazione.setAll(reportDAO.getDAO(segnalazione));
		
		visualizza = new Visualizzazione(this);
		visualizza.idRew.setText(String.valueOf(recensione.getIdRecensione()));
		visualizza.autoreRew.setText(recensione.getUtenteProp());
		visualizza.posRew.setText(String.valueOf(recensione.getValPositive()));
		visualizza.negRew.setText(String.valueOf(recensione.getValNegative()));
		visualizza.titoloRew.setText(recensione.getTitolo());
		visualizza.descrRew.setCaretPosition(visualizza.descrRew.getDocument().getLength());
		visualizza.descrRew.append(recensione.getDescrizione());
		
		visualizza.segnalatoreRew.setText(segnalazione.getUtente());
		visualizza.motivoSeg.setText(segnalazione.getTipo());
		
		visualizza.setVisible(true);
	}
}
