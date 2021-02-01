package classes;
import java.net.URL;
import java.util.List;

public class DescrittoreFilm {
	private String titolo;
	private URL copertina;
	private int valP;
	private int valN;
	private List<Attore> cast;
	private String trama;
	private String genere;
	
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public URL getCopertina() {
		return copertina;
	}
	public void setCopertina(URL copertina) {
		this.copertina = copertina;
	}
	public int getValP() {
		return valP;
	}
	public void setValP(int valP) {
		this.valP = valP;
	}
	public int getValN() {
		return valN;
	}
	public void setValN(int valN) {
		this.valN = valN;
	}
	public List<Attore> getCast() {
		return cast;
	}
	public void setCast(List<Attore> cast) {
		this.cast = cast;
	}
	public String getTrama() {
		return trama;
	}
	public void setTrama(String trama) {
		this.trama = trama;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	

}
