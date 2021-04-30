package DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public class Recensione_DAO {
	
	private static final String TMDB = "https://api.themoviedb.org/3/";
    private static final String APIKEY = "?api_key=6ff4c2846a2910d753ff91a81eee4f6c";

    private static final String IT_IT = "&language=it-IT";
    private static final String EN_US = "&language=en-US";
    private static final String TRENDING_ALL = TMDB + "trending/all/week" + APIKEY + IT_IT;
    private static final String TRENDING =  TMDB + "trending/";
    private static final String SEARCH =  TMDB + "search/multi" + APIKEY + IT_IT;
    private static final String ADULT = "&include_adult=true";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }

	
	public JSONObject getDAO(String id) throws IOException, JSONException {
	    InputStream is = new URL(TMDB + "review/" + id + APIKEY + EN_US).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }

}
