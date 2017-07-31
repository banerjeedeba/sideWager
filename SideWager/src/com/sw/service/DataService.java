package com.sw.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sw.DBConstants;
import com.sw.entities.Game;
import com.sw.entities.Match;
import com.sw.entities.Odds;
import com.sw.util.HttpURLConnectionUtil;
import com.sw.util.StringUtils;

@Path("/data")
public class DataService {

	private static final Logger log = Logger.getLogger(DataService.class.getName());
	
	@GET
	@Path("/gamelist")
	@Produces({ MediaType.APPLICATION_JSON })
	public String gameList(@QueryParam("ckey") String key){
		String response = "";
		try{
			String url = DBConstants.DB_BASE_URL+DBConstants.TABLE_TOURNAMENT+"?auth="+key+"&orderBy=\"Sport\"&equalTo=19";
			HttpURLConnectionUtil con = new HttpURLConnectionUtil();
			String fbresponse = con.sendGet(url, null, null);
			Gson gsonObj = new Gson();
			ArrayList<Match> matches = new ArrayList<Match>();
			ArrayList<Game> gameList = new ArrayList<Game>();
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(fbresponse).getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String, JsonElement> entry : entrySet) {
				JsonElement jsonElement = jsonObject.getAsJsonObject(entry.getKey());
				JsonObject matchObject = jsonElement.getAsJsonObject();
				/*JsonArray oddsArray = matchObject.getAsJsonArray("Odds");
				System.out.println(oddsArray.isJsonArray()+"...."+oddsArray);
				ArrayList<Odds> odds = new ArrayList<Odds>();
				if(oddsArray != null){
					for (int i=0;i<oddsArray.size();i++){ 
						System.out.println("before odds element");
						JsonElement oddElement = oddsArray.get(i);
						System.out.println("after odds element before odds object"+oddElement);
						Odds odd = gsonObj.fromJson(oddElement, Odds.class);
						System.out.println("after odds object");
						odds.add(odd);
					}
					
				}
				match.setOddslist(odds);*/
				Match match = gsonObj.fromJson(jsonElement, Match.class);
				Game game = new Game();
				game.setAwayTeam(match.getAwayTeam());
				game.setAwayTeamShortName(StringUtils.getShortName(match.getAwayTeam()));
				game.setHomeTeam(match.getHomeTeam());
				game.setHomeTeamShortName(StringUtils.getShortName(match.getHomeTeam()));
				game.setId(match.getId());
				game.setMatchDate(StringUtils.getDate(match.getMatchTime(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy/MM/dd"));
				game.setMatchTime(StringUtils.getDate(match.getMatchTime(), "yyyy-MM-dd'T'HH:mm:ss", "HH:mma"));
				if(match.getOdds().size()>=1){
					Odds odd = match.getOdds().get(0);
					game.setPointSpread(game.getHomeTeamShortName()+odd.getPointSpreadHome());
					game.setUnderLine("u "+odd.getTotalNumber());
				}
				game.setSport(match.getSport());
				game.setSportName(DBConstants.SPORTS.get(match.getSport()));
				game.setWholeMatchTime(match.getMatchTime());
				gameList.add(game);
				
				matches.add(match);
			}
			
			response = gsonObj.toJson(gameList);
			
			System.out.println("Match Size: "+matches.size());
		}catch(Exception e){
			log.info(getClass().getName()+" - gameList() - "+e.getMessage());
			response = "[{\"error\": \"Unable to fetch game list\"}]";
		}
		return response;
	}
}
