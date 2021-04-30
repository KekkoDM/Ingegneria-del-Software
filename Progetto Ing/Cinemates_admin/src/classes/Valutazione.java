package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Valutazione {
	private int valore;

	public int getValore() {
		return valore;
	}

	public void setValore(int valore) {
		this.valore = valore;
	}
	
	public void setValutazione(ResultSet rs) {
		try {
			while(rs.next()) {
				setValore(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
