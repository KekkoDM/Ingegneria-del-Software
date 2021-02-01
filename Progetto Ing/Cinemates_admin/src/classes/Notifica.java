package classes;

public class Notifica {
	
	private int idNotifica;
	private String titolo;
	private String tipo;
	private String descrizione;
	private String utente;
	
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
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public int getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(int idNotifica) {
		this.idNotifica = idNotifica;
	}

}
