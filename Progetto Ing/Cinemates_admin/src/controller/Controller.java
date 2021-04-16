package controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import DAO.Amministratore_DAO;
import DAO.Commento_DAO;
import DAO.DAO_Interface;
import DAO.Notifica_DAO;
import DAO.Recensione_DAO;
import DAO.Segnalazione_DAO;
import DAO.Valutazione_DAO;
import GUI.Dashboard;
import GUI.Login;
import GUI.Popup;
import GUI.Visualizzazione;
import classes.Amministratore;
import classes.Commento;
import classes.Notifica;
import classes.Recensione;
import classes.RequestJson;
import classes.Segnalazione;
import classes.Valutazione;


public class Controller {
    public static Connessione conn;
    public static Amministratore admin;
    public static Commento commento;
    public static Notifica notifica;
    public static Recensione recensione;
    public static Segnalazione segnalazione;
    public static Valutazione valutazione;
    public static Notifica_DAO notification_DAO;
    public static Commento_DAO comment_DAO;
    public static Recensione_DAO reviewDAO;
    public static Amministratore_DAO adminDAO;
    public static Segnalazione_DAO reportDAO;
    public static Valutazione_DAO valutation_DAO;

    
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
		valutazione = new Valutazione();
		notification_DAO = new Notifica_DAO();
		adminDAO = new Amministratore_DAO();
		reportDAO = new Segnalazione_DAO();
		segnalazione = new Segnalazione();
		reviewDAO = new Recensione_DAO();
		recensione = new Recensione();
		valutation_DAO = new Valutazione_DAO();
		comment_DAO = new Commento_DAO();
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
	
	
	public Recensione getRecensione(String idRecensione, String utente) throws JSONException, IOException {
		reviewDAO.checkExists(idRecensione, utente, recensione);
		recensione.setAll(new RequestJson().getReview(recensione.getId()));
		return recensione;
	}
	
	 
	public void sendReportResult(Boolean decisione) throws JSONException, IOException{
		
		segnalazione.setAll(reportDAO.getDAO(segnalazione));
		String msgProp = null;
		String msgSeg = null;
		
		
		if (decisione) {
			if(commento != null) {
				msgProp ="Il tuo commento n." + commento.getId() + " ha subito una segnalazione per " + segnalazione.getMotivo()+
					" , ma non viola nessuna regola, pertanto è stato approvato";
			
				msgSeg ="La tua segnalazione n. " + segnalazione.getId() +"  per " +segnalazione.getMotivo() + " al commento di " + commento.getUsername() + 
				" non è stata ritenuta valida per eliminare il contenuto";
				
			}else {
				msgSeg ="La tua segnalazione n. " + segnalazione.getId() +"  per " +segnalazione.getMotivo() + " alla recensione di " + recensione.getUser() + 
						" non è stata ritenuta valida per eliminare il contenuto";
			}
			reportDAO.approva(segnalazione);
			
		}else {
			if(commento != null) {
				msgProp ="Il tuo commento n. " + commento.getId() + " ha subito una segnalazione per " + segnalazione.getMotivo()+
					"  e non è stato approvato dal nostro admin " + admin.getUsername() + ", pertanto è stato cancellato";
			
				msgSeg ="La tua segnalazione n. " + segnalazione.getId() +"  per " +segnalazione.getMotivo() + " al commento di " + commento.getUsername() + 
				" è stata ritenuta valida, pertanto il contenuto è stato eliminato";
				
			}else {
				msgSeg ="La tua segnalazione n. " + segnalazione.getId() +"  per " +segnalazione.getMotivo() + " alla recensione di " + recensione.getUser() + 
						" è stata ritenuta valida, pertanto il contenuto è stato eliminato";
			}
				
			reportDAO.disapprova(segnalazione);
		}
		
		if(msgProp != null) {
			notifica.createNotificaProprietario(msgProp);
			notification_DAO.sendReportDecision();
		}
		
		notifica.createNotificaSegnalatore(msgSeg);
		notification_DAO.sendReportDecision();
		
	}
	
	
	public void showReport() throws JSONException, IOException {
		
		visualizza = new Visualizzazione(this);
		if(recensione != null) {
			recensione.setAll(new RequestJson().getReview(recensione.getId()));
			valutazione.setValutazione(valutation_DAO.getDAO(recensione));
			visualizza.idRew.setText(recensione.getId());
			visualizza.autoreRew.setText(recensione.getUser());
			visualizza.posRew.setText(String.valueOf(valutazione.getValore()));
			visualizza.descrRew.setCaretPosition(visualizza.descrRew.getDocument().getLength());
			visualizza.descrRew.append(recensione.getDescrizione());
		}
		else {
			commento.setAll(comment_DAO.getDAO(commento));
			visualizza.idRew.setText(String.valueOf(commento.getId()));
			visualizza.autoreRew.setText(commento.getUsername());
			visualizza.descrRew.setCaretPosition(visualizza.descrRew.getDocument().getLength());
			visualizza.descrRew.append(commento.getDescrizione());
		}
		
		segnalazione.setAll(reportDAO.getDAO(segnalazione));
		visualizza.segnalatoreRew.setText(segnalazione.getSegnalatore());
		visualizza.motivoSeg.setText(segnalazione.getMotivo());
		
		visualizza.setVisible(true);
	}
	
	
	public void showReports() {
		dashboard.homePanel.setVisible(false);
		dashboard.settingsPanel.setVisible(false);
		dashboard.reviewsPanel.setVisible(false);
		dashboard.reviewsPanelStart(this);
	}
}
