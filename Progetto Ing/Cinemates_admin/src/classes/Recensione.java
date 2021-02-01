package classes;

public class Recensione {
	
	private int idRecensione;
	private String titolo;
	private String descrizione;
	private int valPositive;
	private int valNegative;
	private String utenteProp;
	
	public int getIdRecensione() {
		return idRecensione;
	}
	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getUtenteProp() {
		return utenteProp;
	}
	public void setUtenteProp(String utenteProp) {
		this.utenteProp = utenteProp;
	}
	public int getValPositive() {
		return valPositive;
	}
	public void setValPositive(int valPositive) {
		this.valPositive = valPositive;
	}
	public int getValNegative() {
		return valNegative;
	}
	public void setValNegative(int valNegative) {
		this.valNegative = valNegative;
	}
	
	

}
