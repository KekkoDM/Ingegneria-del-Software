package classes;

public class Segnalazione {
	
	private String tipo;
	private Recensione recensione;
	private int admin;
	private int utente;

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Recensione getRecensione() {
		return recensione;
	}

	public void setRecensione(Recensione recensione) {
		this.recensione = recensione;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public int getUtente() {
		return utente;
	}

	public void setUtente(int utente) {
		this.utente = utente;
	}



}
