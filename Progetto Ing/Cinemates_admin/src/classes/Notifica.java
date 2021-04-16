package classes;

import controller.Controller;

public class Notifica {
	
	private int idNotifica;
	private String titolo;
	private String tipo;
	private String descrizione;
	private String mittente;
	private String destinatario;
	private int amministratore;
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(int idNotifica) {
		this.idNotifica = idNotifica;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public int getAmministratore() {
		return amministratore;
	}
	public void setAmministratore(int amministratore) {
		this.amministratore = amministratore;
	}
	
	public void createNotificaProprietario(String messaggio) {
		setTitolo("Esito Segnlazione");
		System.out.println("Titolo: "+getTitolo());
		setTipo("Segnalazione");
		System.out.println("Tipo: "+getTipo());
		setDescrizione(messaggio);
		System.out.println("Messaggio: "+getDescrizione());
		setAmministratore(Controller.admin.getID());
		System.out.println("Amministratore: "+getAmministratore());
		setMittente(Controller.segnalazione.getUtente());
		System.out.println("Mittente: "+getMittente());
		setDestinatario(Controller.commento.getUsername());
		System.out.println("Destinatario: "+getDestinatario());
	}
	
	public void createNotificaSegnalatore(String messaggio) {
		setTitolo("Esito Segnlazione");
		System.out.println("Titolo: "+getTitolo());
		setTipo("Segnalazione");
		System.out.println("Tipo: "+getTipo());
		setDescrizione(messaggio);
		System.out.println("Messaggio: "+getDescrizione());
		setAmministratore(Controller.admin.getID());
		System.out.println("Amministratore: "+getAmministratore());
		setMittente(Controller.segnalazione.getUtente());
		System.out.println("Mittente: "+getMittente());
		setDestinatario(Controller.segnalazione.getSegnalatore());
		System.out.println("Destinatario: "+getDestinatario());
	}
	
	public String getAllInOne() {
		String values = "'"+getTitolo()+"','"+getTipo()+"','"+getDescrizione()+"',"+getAmministratore()+",'"+getDestinatario()+"'";
		return values;
	}
	
	
}
