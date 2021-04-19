package classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Recensione {
	
	
	
	
	
	private String descrizione;
    private String data;
    private String id;
    private String user;



    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) { this.id = id; }

	
	public void setAll(JSONObject jsonObject) throws JSONException {
		this.user = jsonObject.getString("author");
		this.data = jsonObject.getString("created_at").substring(0,10);
		this.descrizione = jsonObject.getString("content");
	}

	

}
