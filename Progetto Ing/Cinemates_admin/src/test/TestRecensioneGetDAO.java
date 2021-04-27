package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;


import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import DAO.Recensione_DAO;

class TestRecensioneGetDAO {
	String expected = "{\"id\":\"608203c52b8a430029d0a07d\",\"author\":\"garethmb\",\"author_details\":{\"name\":\"\",\"username\":\"garethmb\",\"avatar_path\":\"/https://secure.gravatar.com/avatar/3593437cbd05cebe0a4ee753965a8ad1.jpg\",\"rating\":null},\"content\":\"Mortal Kombat Gives Fans Of The Franchise The Brutal Live-Action Version They Deserve\\r\\nFans of the Mortal Kombat series have known that the path to bringing the violent and controversial game to live-action formats has been a mixed bag. While the first film in 1995 was a decent hit; the follow-up in 1997 disappointed fans who had grown weary of the PG-13 take on the series.\\r\\n\\r\\nSubsequent efforts such as the 2011 television series also left fans wanting more; especially since the game series had become even more graphic and violent.\\r\\n\\r\\nAn animated film released in 2020 gave fans a taste of what they wanted as it featured graphical violence which many fans believed was essential to properly catch the spirit and action of the series.\\r\\n\\r\\nThe latest offering in the series “Mortal Kombat”; reboots the cinematic universe and gives fans the intense, brutal, and graphic violence that they have demanded. The film keeps the basic premise that the Outworld realm has won nine tournaments in a row, and based on the ancient laws; one more victory would allow them to take control of the Earth.\\r\\n\\r\\nRaiden the Thunder God (Tadanobu Asano); who has been tasked with protecting Earth looks to assemble and train a band of champions to save Earth. Naturally, this is not going to be easy as Shang Tsung (Chin Han); is not willing to follow the rules of the tournament and dispatches his top fighter (Sub Zero (Joe Taslim) to dispatch the champions of Earth before the tournament in a clear violation of the rules in order to ensure total victory.\\r\\n\\r\\nWhat follows is solid and very graphic action which contains gore and brutality on a level that almost kept the film from earning an R-rating. The action sequences are well-choreographed and there were some great recreations of classic moves by characters from the game series which were really well utilized and did not seem like gratuitous pandering.\\r\\n\\r\\nWhile the plot is fairly simplistic and does not deviate greatly from the source material; it does give a larger backstory to the universe. It was really enjoyable to see many nods to the franchise throughout both subtle and overt and while some characters were glaringly absent which was a surprise; the characters that were included were really solid to see and the door was wide open for their inclusion at a later date.\\r\\n\\r\\nWhile the cast does not contain any star power in terms of what Western audiences might expect from a major studio release; the ensemble works well and do a great job in bringing their characters to life.\\r\\n\\r\\nThe film leaves sequels wide open and teases a character that in my opinion was a glaring omission from the film. That being said; “Mortal Kombat” gives fans a solid adaptation that does not shy away from gore and violence and gives fans the cinematic experience that they have wanted.\\r\\n\\r\\n3.5 stars out of 5\",\"created_at\":\"2021-04-22T23:16:21.703Z\",\"iso_639_1\":\"en\",\"media_id\":460465,\"media_title\":\"Mortal Kombat\",\"media_type\":\"movie\",\"updated_at\":\"2021-04-22T23:16:21.703Z\",\"url\":\"https://www.themoviedb.org/review/"
			+ "608203c52b8a430029d0a07d\"}";
	
	String id = "608203c52b8a430029d0a07d";

	//608203c52b8a430029d0a07d
	@Test
	void RecensioneTrovata() throws JSONException, IOException {
		
		
		Recensione_DAO r = new Recensione_DAO();
		JSONAssert.assertEquals(expected, r.getDAO(id),true);
	}
	
	@Test
	void RecensioneNonTrovata() {
		Recensione_DAO r = new Recensione_DAO();
		assertThrows(IOException.class, () -> {
		    r.getDAO("non torvata");
		  });
	}

	
	@Test
	void RecensioneNull() {
		Recensione_DAO r = new Recensione_DAO();
		assertThrows(IOException.class, () -> {
		    r.getDAO(null);
		  });
	}
	
	@Test
	void RecensioneSbagliata() throws IOException, JSONException {
		Recensione_DAO r = new Recensione_DAO();
		assertNotEquals(expected, r.getDAO("60847e545c3247007764906c"));
	}
	
	@Test
	void RecensioneVuota() {
		Recensione_DAO r = new Recensione_DAO();
		assertThrows(IOException.class, () -> {
		    r.getDAO("");
		  });
	}
	
	@Test
	void GetVuoto() {
		Recensione_DAO r = new Recensione_DAO();
		assertThrows(JSONException.class, () -> {
		    r.getDAO(id).get("");
		  });
	}
	
	@Test
	void GetNull() {
		Recensione_DAO r = new Recensione_DAO();
		assertThrows(JSONException.class, () -> {
		    r.getDAO(id).get(null);
		  });
	}
	
	@Test
	void GetCampoSbagliato() {
		Recensione_DAO r = new Recensione_DAO();
		assertThrows(JSONException.class, () -> {
		    r.getDAO(id).get("campo");
		  });
	}
	
	@Test
	void GetCampoGiusto() throws JSONException, IOException {
		Recensione_DAO r = new Recensione_DAO();
		assertEquals("garethmb", r.getDAO(id).get("author"));
	}
	


}
