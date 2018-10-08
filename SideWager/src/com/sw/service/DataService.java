package com.sw.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.firebase.database.Logger.Level;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sw.DBConstants;
import com.sw.OddsUpdate;
import com.sw.entities.Game;
import com.sw.entities.Match;
import com.sw.entities.Nickname;
import com.sw.entities.Odds;
import com.sw.entities.Result;
import com.sw.entities.Sports;
import com.sw.util.HttpURLConnectionUtil;
import com.sw.util.StringUtils;

@Path("/data")
public class DataService {

	private static final Logger log = Logger.getLogger(DataService.class.getName());
	
	private static long nextUpdate = 0l;
	
	public static HashMap<Integer, String> SPORTS = new HashMap<Integer, String>();
	public static ArrayList<String> SPORTSLIST = new ArrayList<String>();
	public static HashMap<String,HashMap<String,Nickname>> NICKNAMES = new HashMap<String,HashMap<String,Nickname>>();
	public static ArrayList<Match> MATCHES = new ArrayList<Match>();
	public HashMap<String,Sports> SPORTSMAP = null;
	
	private static String checkGameResultUrl = "https://us-central1-sidewager-163802.cloudfunctions.net/checkGameResult";
	private static String updateLiveGameResultUrl = "https://us-central1-sidewager-163802.cloudfunctions.net/updateLiveGameResult";
	private static String updateOpenGameResultUrl = "https://us-central1-sidewager-163802.cloudfunctions.net/updateOpenGameResult";
	
	
	private static long getNextDayInMillis(){
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		//date.set(Calendar.HOUR_OF_DAY, 0);
		//date.set(Calendar.MINUTE, 0);
		//date.set(Calendar.SECOND, 0);
		//date.set(Calendar.MILLISECOND, 0);

		// next day
		//date.add(Calendar.DAY_OF_MONTH, 1);
		date.add(Calendar.MINUTE, 5);
		long nextDayinMillis = date.getTimeInMillis();
		return nextDayinMillis;
	}
	
	@GET
	@Path("/gamelistupdate")
	@Produces({ MediaType.APPLICATION_JSON })
	public String gameListUpdate(){
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		String sportsUrl = "https://us-central1-sidewager-163802.cloudfunctions.net/getGameData";
		String res = "[{\"response\": \"Game list updated\"}]";
		try {
			con.sendGet(sportsUrl, null, null);
		} catch (Exception e) {
			res = "[{\"error\": \"Unable to update game list"+e.getMessage()+"\"}]";
		}
		return res;
	}
	
	@GET
	@Path("/gamelist")
	@Produces({ MediaType.APPLICATION_JSON })
	public String gameList(@QueryParam("ckey") String key, @QueryParam("sportsName") String sportsName){
		String response = "";
		Gson gsonObj = new Gson();
		JsonParser parser = new JsonParser();
		try{
			
			HttpURLConnectionUtil con = new HttpURLConnectionUtil();
			
			//fetch sports
			if(SPORTS.size()<=0 || nextUpdate<new Date().getTime()){
				System.out.println("fetch sports");
				SPORTS.clear();
				SPORTSLIST.clear();
				String sportsUrl = DBConstants.DB_BASE_URL+DBConstants.TABLE_SPORTS+"?auth="+key;
				String sportsList = con.sendGet(sportsUrl, null, null);
				JsonArray jsonarray = parser.parse(sportsList).getAsJsonArray();
				
				for(int i=0; i<jsonarray.size();i++){
					try{
						String sportsServiceName = jsonarray.get(i).getAsString();
						SPORTS.put(i, sportsServiceName);
						SPORTSLIST.add(sportsServiceName);
					}catch(Exception e){
						//Do nothing
					}
				}
				
				
			}
			
			//fetch nicknames
			if(NICKNAMES.size()<=0 || nextUpdate<new Date().getTime()){
				System.out.println("fetch nickname");
				NICKNAMES.clear();
				String nickNameUrl = DBConstants.DB_BASE_URL+DBConstants.TABLE_NICKNAME+"?auth="+key;
				String nickNameList = con.sendGet(nickNameUrl, null, null);
				
				JsonArray nicknameArray = parser.parse(nickNameList).getAsJsonArray();
				for(int i=0;i<nicknameArray.size();i++){
					JsonElement nicknameElement = nicknameArray.get(i);
					Nickname nickname = gsonObj.fromJson(nicknameElement, Nickname.class);
					HashMap<String, Nickname> team = null;
					//check if sports is available
					if(NICKNAMES.keySet().contains(nickname.getSports().toLowerCase())){
						team = NICKNAMES.get(nickname.getSports().toLowerCase());
					} else {
						team = new HashMap<String, Nickname>();
						
					}
					
					team.put(nickname.getFullName(), nickname);
					if(!NICKNAMES.keySet().contains(nickname.getSports())){
						NICKNAMES.put(nickname.getSports().toLowerCase(), team);
					}
					
				}
			}
			
			
			//Fatch match list
			if(MATCHES.size()<=0 || nextUpdate<new Date().getTime()){
				MATCHES.clear();
				System.out.println("fetch match");
				//fetch tournament from db
				//String url = DBConstants.DB_BASE_URL+DBConstants.TABLE_TOURNAMENT+"?auth="+key;//+"&orderBy=\"Sport\"&equalTo=19";
				//String tournamentList = con.sendGet(url, null, null);
				
				//fetch tournament from service
				String tournamentList = OddsUpdate.getOdds();
				
				//save in db
				//new OddsUpdate().updateDB(tournamentList,key);
				
				//for specific sports
				/*JsonObject jsonObject = parser.parse(tournamentList).getAsJsonObject();
				Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
				for(Map.Entry<String, JsonElement> entry : entrySet) {
					JsonElement jsonElement = jsonObject.getAsJsonObject(entry.getKey());
					JsonObject matchObject = jsonElement.getAsJsonObject();
					Match match = gsonObj.fromJson(jsonElement, Match.class);
					
					MATCHES.add(match);
				}*/
				
				//for all the sports
				JsonArray tournamentArray = parser.parse(tournamentList).getAsJsonArray();
				for(int i=0;i<tournamentArray.size();i++){
					
						
					
					JsonElement tournamentElement = tournamentArray.get(i);
					try{
					Match match = gsonObj.fromJson(tournamentElement, Match.class);
					
					MATCHES.add(match);
					}catch(Exception e){
						//e.printStackTrace();
						System.out.println("error parsing tournament: "+tournamentElement+" error message: "+e.getMessage());
						//log.info("error parsing tournament: "+tournamentElement+" error message: "+e.getMessage());
					}
				}
			}
			
			//update next update time
			if(nextUpdate<new Date().getTime()){
				nextUpdate=getNextDayInMillis();
			}
			
			
			SPORTSMAP = new HashMap<String,Sports>();
			for(Match match : MATCHES){
				int sportsId = match.getSport();
				//temp restriction on these games only
				if(sportsId == 0 || sportsId == 1 || sportsId == 4 || sportsId == 5){
					Sports sport = null;
					if(SPORTSMAP.keySet().contains(SPORTS.get(sportsId))){
						sport = SPORTSMAP.get(SPORTS.get(sportsId));
					} else {
						sport = new Sports();
						sport.setDisplayName(SPORTS.get(sportsId));
						sport.setId(sportsId);
						sport.setOriginalName(SPORTS.get(sportsId));
						sport.setShortName(SPORTS.get(sportsId));
					}
					
					Game game = new Game();
					game.setAwayTeam(match.getAwayTeam());
					
					String awayTeamShortName = "";
					try{
						awayTeamShortName = NICKNAMES.get(sport.getShortName()).get(game.getAwayTeam()).getNickname();
					}catch(Exception e){
						awayTeamShortName = StringUtils.getShortName(match.getAwayTeam());
					}
					game.setAwayTeamShortName(awayTeamShortName);
					String awayTeamName = "";
					try{
						awayTeamName = NICKNAMES.get(sport.getShortName()).get(game.getAwayTeam()).getCity();
					}catch(Exception e){
						//do nothing
					}
					if(!StringUtils.isEmpty(awayTeamName)){
						game.setAwayTeam(awayTeamName);
					}
					
					String homeTeamShortName = "";
					try{
						homeTeamShortName = NICKNAMES.get(sport.getShortName()).get(game.getHomeTeam()).getNickname();
					}catch(Exception e){
						homeTeamShortName = StringUtils.getShortName(match.getHomeTeam());
					}
					game.setHomeTeamShortName(homeTeamShortName);
					game.setHomeTeam(match.getHomeTeam());
					String homeTeamName = "";
					try{
						homeTeamName = NICKNAMES.get(sport.getShortName()).get(game.getHomeTeam()).getCity();
					}catch(Exception e){
						//do nothing
					}
					if(!StringUtils.isEmpty(homeTeamName)){
						game.setHomeTeam(homeTeamName);
					}
					game.setId(match.getId());
					game.setMatchDate(StringUtils.getDate(match.getMatchTime(), "yyyy-MM-dd'T'HH:mm:ss", "MMM dd, yyyy"));
					game.setMatchTime(StringUtils.getDate(match.getMatchTime(), "yyyy-MM-dd'T'HH:mm:ss", "HH:mma"));
					if(match.getOdds().size()>=1){
						Odds odd = match.getOdds().get(0);
						String pointSpread = odd.getPointSpreadHome();
						if(!StringUtils.isEmpty(pointSpread) && pointSpread.contains("-")){
							pointSpread=pointSpread.replace("-", "");
						}
						game.setPointSpread(pointSpread);
						String underLine = odd.getPointSpreadAway();
						if(!StringUtils.isEmpty(underLine) && underLine.contains("-")){
							underLine=underLine.replace("-", "");
						}
						game.setUnderLine(underLine);
					}
					game.setSport(match.getSport());
					game.setSportName(SPORTS.get(match.getSport()));
					game.setWholeMatchTime(match.getMatchTime());
					sport.getGameList().add(game);
					
					if(!SPORTSMAP.keySet().contains(SPORTS.get(sportsId))){
						SPORTSMAP.put(sport.getShortName(), sport);
					}
				}
				
			}
			
			ArrayList<Sports> requestSportsList = new ArrayList<Sports>();
			if(sportsName==null || !SPORTSMAP.containsKey(sportsName)){
				Set<String> sportsKeys = SPORTSMAP.keySet();
				for(String sport: sportsKeys){
					requestSportsList.add(SPORTSMAP.get(sport));
				}
			} else {
				if(SPORTSMAP.containsKey(sportsName)){
					requestSportsList.add(SPORTSMAP.get(sportsName));
				}else{
					throw new Exception("Invalid key");
				}
			}
			
			response = gsonObj.toJson(requestSportsList);
			
			
			System.out.println("response: "+response);
			System.out.println("Match Size: "+MATCHES.size());
			System.out.println("requestSportsList Size: "+requestSportsList.size());
		}catch(Exception e){
			e.printStackTrace();
			log.info(getClass().getName()+" - gameList() - "+e.getLocalizedMessage()+e.getMessage());
			response = "[{\"error\": \"Unable to fetch game list\"}]";
		}
		return response;
	}
	
	@GET
	@Path("/checkGameStatus")
	@Produces({ MediaType.APPLICATION_JSON })
	public String checkGameStatus(){
		String response = "";
		Gson gsonObj = new Gson();
		JsonParser parser = new JsonParser();
		//Date will return local time in Java  
	     Date localTime = new Date(); 
	     //creating DateFormat for converting time from local timezone to GMT
	     DateFormat dateConverter = new SimpleDateFormat("MMM dd, yyyy");
	     //getting GMT timezone, you can get any timezone e.g. UTC
	     dateConverter.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	     String todaysDate = dateConverter.format(localTime);
	     
	     HttpURLConnectionUtil con = new HttpURLConnectionUtil();
	     
	     try {
	    	 
	    	//get the result list
			String resultList = OddsUpdate.getResults();
			JsonArray resultArray = parser.parse(resultList).getAsJsonArray();
			HashMap<String,Result> results = new HashMap<String,Result>();
			for(int i=0;i<resultArray.size();i++){
				
				JsonElement resultElement = resultArray.get(i);
				try{
				Result result = gsonObj.fromJson(resultElement, Result.class);
				results.put(result.getId(), result);
				}catch(Exception e){
					//e.printStackTrace();
					//System.out.println("error parsing tournament: "+tournamentElement+" error message: "+e.getMessage());
					log.info("error parsing result: "+resultElement+" error message: "+e.getMessage());
				}
			}
	    	 
	    	 
			//get active games for today
	    	String url = checkGameResultUrl+"?a="+URLEncoder.encode(todaysDate, "UTF-8");
			String activeGames = con.sendGet(url, null, null);
			JsonArray activeGameArray = parser.parse(activeGames).getAsJsonArray();
			//ArrayList<Game> games = new ArrayList<>();
			for(int i=0;i<activeGameArray.size();i++){
				
				JsonElement activeGameElement = activeGameArray.get(i);
				try{
				Game game = gsonObj.fromJson(activeGameElement, Game.class);
				String cleardb="";
				Result result = results.get(game.getId());
				System.out.println(result);
				String homeScoreString = result.getHomeScore();
				String awayScoreString = result.getAwayScore();
				if(homeScoreString!=null && awayScoreString!=null){
					//int homeScore = Integer.parseInt(homeScoreString);
					//int awayScore = Integer.parseInt(awayScoreString);
					/*switch(game.getType()){
						case "openlive":
							cleardb = "no";
						case "live":
							if(StringUtils.isEmpty(cleardb)){
								cleardb = "yes";
							}
							updateLiveWager(game.getId(),cleardb);
							if(cleardb.equals("yes")){
								break;
							}
						case "open":
							if("Finished".equals(result.getFinalType())){
								updateOpenWager(game.getId(),homeScoreString,awayScoreString,todaysDate,"yes");
							} else {
								updateOpenWager(game.getId(),null,null,todaysDate,"no");
							}
					}*/
					System.out.println("game type"+game.getType());
					if("openlive".equals(game.getType())){
						cleardb = "no";
						updateLiveWager(game.getId(),cleardb);
						if("Finished".equals(result.getFinalType())){
							updateOpenWager(game.getId(),homeScoreString,awayScoreString,todaysDate,"yes");
						} else {
							updateOpenWager(game.getId(),null,null,todaysDate,"no");
						}
					} else if("live".equals(game.getType())){
						cleardb = "yes";
						updateLiveWager(game.getId(),cleardb);
					}else if("open".equals(game.getType())){
						if("Finished".equals(result.getFinalType())){
							updateOpenWager(game.getId(),homeScoreString,awayScoreString,todaysDate,"yes");
						} else {
							updateOpenWager(game.getId(),null,null,todaysDate,"no");
						}
					}
				}
				
				//games.add(game);
				}catch(Exception e){
					//e.printStackTrace();
					//System.out.println("error parsing tournament: "+tournamentElement+" error message: "+e.getMessage());
					log.info("error parsing game: "+activeGameElement+" error message: "+e.getMessage());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(getClass().getName()+" - checkGameStatus() - "+e.getLocalizedMessage()+e.getMessage());
			response = "[{\"error\": \"Unable to fetch game list\"}]";
		}

		return "[{\"response\": \"Game status updated\"}]";
	}
	

	private void updateOpenWager(String id, String homeScoreString, String awayScoreString, String todaysDate, String gameFinished) throws Exception {
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		String url = updateOpenGameResultUrl+"?a="+URLEncoder.encode(id, "UTF-8");
		if(!StringUtils.isEmpty(homeScoreString)){
			url=url+"&b="+URLEncoder.encode(homeScoreString, "UTF-8");
		}
		if(!StringUtils.isEmpty(awayScoreString)){
			url=url+"&c="+URLEncoder.encode(awayScoreString, "UTF-8");
		}
		url=url+"&d="+URLEncoder.encode(todaysDate, "UTF-8");
		url=url+"&e="+gameFinished;
		System.out.println(url);
		String updateResult = con.sendGet(url, null, null);
		System.out.println(updateResult);
	}

	private void updateLiveWager(String id, String cleardb) throws Exception {
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		String url=updateLiveGameResultUrl+"?a="+id+"&b="+cleardb;
		String updateResult = con.sendGet(url, null, null);
		System.out.println(updateResult);
	}

	public static void main(String[] args) {
		
	/*	String nicknames = "[{\"city\":\"Arizona\",\"fullName\":\"Arizona Diamondbacks\",\"nickname\":\"ARI\",\"sports\":\"MLB\",\"teamName\":\"Diamondbacks\"},{\"city\":\"Atlanta\",\"fullName\":\"Atlanta Braves\",\"nickname\":\"ATL\",\"sports\":\"MLB\",\"teamName\":\"Braves\"},{\"city\":\"Baltimore\",\"fullName\":\"Baltimore Orioles\",\"nickname\":\"BAL\",\"sports\":\"MLB\",\"teamName\":\"Orioles\"},{\"city\":\"Boston\",\"fullName\":\"Boston Red Sox\",\"nickname\":\"BOS\",\"sports\":\"MLB\",\"teamName\":\"Red Sox\"},{\"city\":\"Chi. Cubs\",\"fullName\":\"Chicago Cubs\",\"nickname\":\"CHC\",\"sports\":\"MLB\",\"teamName\":\"Cubs\"},{\"city\":\"Chi Sox\",\"fullName\":\"Chicago White Sox\",\"nickname\":\"CHW\",\"sports\":\"MLB\",\"teamName\":\"White Sox\"},{\"city\":\"Cincinnati\",\"fullName\":\"Cincinnati Reds\",\"nickname\":\"CIN\",\"sports\":\"MLB\",\"teamName\":\"Reds\"},{\"city\":\"Cleveland\",\"fullName\":\"Cleveland Indians\",\"nickname\":\"CLE\",\"sports\":\"MLB\",\"teamName\":\"Indians\"},{\"city\":\"Colorado\",\"fullName\":\"Colorado Rockies\",\"nickname\":\"COL\",\"sports\":\"MLB\",\"teamName\":\"Rockies\"},{\"city\":\"Detroit\",\"fullName\":\"Detroit Tigers\",\"nickname\":\"DET\",\"sports\":\"MLB\",\"teamName\":\"Tigers\"},{\"city\":\"Houston\",\"fullName\":\"Houston Astros\",\"nickname\":\"HOU\",\"sports\":\"MLB\",\"teamName\":\"Astros\"},{\"city\":\"Kansas City\",\"fullName\":\"Kansas City Royals\",\"nickname\":\"KC\",\"sports\":\"MLB\",\"teamName\":\"Royals\"},{\"city\":\"L.A. Angels\",\"fullName\":\"Los Angeles Angels\",\"nickname\":\"LAA\",\"sports\":\"MLB\",\"teamName\":\"Angels\"},{\"city\":\"L.A. Dodgers\",\"fullName\":\"Los Angeles Dodgers\",\"nickname\":\"LAD\",\"sports\":\"MLB\",\"teamName\":\"Dodgers\"},{\"city\":\"Miami\",\"fullName\":\"Miami Marlins\",\"nickname\":\"MIA\",\"sports\":\"MLB\",\"teamName\":\"Marlins\"},{\"city\":\"Milwaukee\",\"fullName\":\"Milwaukee Brewers\",\"nickname\":\"MIL\",\"sports\":\"MLB\",\"teamName\":\"Brewers\"},{\"city\":\"Minnesota\",\"fullName\":\"Minnesota Twins\",\"nickname\":\"MIN\",\"sports\":\"MLB\",\"teamName\":\"Twins\"},{\"city\":\"N.Y. Mets\",\"fullName\":\"New York Mets\",\"nickname\":\"NYM\",\"sports\":\"MLB\",\"teamName\":\"Mets\"},{\"city\":\"N.Y. Yankees\",\"fullName\":\"New York Yankees\",\"nickname\":\"NYY\",\"sports\":\"MLB\",\"teamName\":\"Yankees\"},{\"city\":\"Oakland\",\"fullName\":\"Oakland Athletics\",\"nickname\":\"OAK\",\"sports\":\"MLB\",\"teamName\":\"Athletics\"},{\"city\":\"Philadelphia\",\"fullName\":\"Philadelphia Phillies\",\"nickname\":\"PHI\",\"sports\":\"MLB\",\"teamName\":\"Phillies\"},{\"city\":\"Pittsburgh\",\"fullName\":\"Pittsburgh Pirates\",\"nickname\":\"PIT\",\"sports\":\"MLB\",\"teamName\":\"Pirates\"},{\"city\":\"San Diego\",\"fullName\":\"San Diego Padres\",\"nickname\":\"SD\",\"sports\":\"MLB\",\"teamName\":\"Padres\"},{\"city\":\"San Francisco\",\"fullName\":\"San Francisco Giants\",\"nickname\":\"SF\",\"sports\":\"MLB\",\"teamName\":\"Giants\"},{\"city\":\"Seattle\",\"fullName\":\"Seattle Mariners\",\"nickname\":\"SEA\",\"sports\":\"MLB\",\"teamName\":\"Mariners\"},{\"city\":\"St. Louis\",\"fullName\":\"St. Louis Cardinals\",\"nickname\":\"STL\",\"sports\":\"MLB\",\"teamName\":\"Cardinals\"},{\"city\":\"Tampa Bay\",\"fullName\":\"Tampa Bay Rays\",\"nickname\":\"TB\",\"sports\":\"MLB\",\"teamName\":\"Rays\"},{\"city\":\"Texas\",\"fullName\":\"Texas Rangers\",\"nickname\":\"TEX\",\"sports\":\"MLB\",\"teamName\":\"Rangers\"},{\"city\":\"Toronto\",\"fullName\":\"Toronto Blue Jays\",\"nickname\":\"TOR\",\"sports\":\"MLB\",\"teamName\":\"Blue Jays\"},{\"city\":\"Washington\",\"fullName\":\"Washington Nationals\",\"nickname\":\"WAS\",\"sports\":\"MLB\",\"teamName\":\"Nationals\"},{\"city\":\"Atlanta\",\"fullName\":\"Atlanta Hawks\",\"nickname\":\"ATL\",\"sports\":\"NBA\",\"teamName\":\"Hawks\"},{\"city\":\"Boston\",\"fullName\":\"Boston Celtics\",\"nickname\":\"BOS\",\"sports\":\"NBA\",\"teamName\":\"Celtics\"},{\"city\":\"Brooklyn\",\"fullName\":\"Brooklyn Nets\",\"nickname\":\"BKN\",\"sports\":\"NBA\",\"teamName\":\"Nets\"},{\"city\":\"Charlotte\",\"fullName\":\"Charlotte Hornets\",\"nickname\":\"CHA\",\"sports\":\"NBA\",\"teamName\":\"Hornets\"},{\"city\":\"Chicago\",\"fullName\":\"Chicago Bulls\",\"nickname\":\"CHI\",\"sports\":\"NBA\",\"teamName\":\"Bulls\"},{\"city\":\"Cleveland\",\"fullName\":\"Cleveland Cavaliers\",\"nickname\":\"CLE\",\"sports\":\"NBA\",\"teamName\":\"Cavaliers\"},{\"city\":\"Dallas\",\"fullName\":\"Dallas Mavericks\",\"nickname\":\"DAL\",\"sports\":\"NBA\",\"teamName\":\"Mavericks\"},{\"city\":\"Denver\",\"fullName\":\"Denver Nuggets\",\"nickname\":\"DEN\",\"sports\":\"NBA\",\"teamName\":\"Nuggets\"},{\"city\":\"Detroit\",\"fullName\":\"Detroit Pistons\",\"nickname\":\"DET\",\"sports\":\"NBA\",\"teamName\":\"Pistons\"},{\"city\":\"Golden State\",\"fullName\":\"Golden State Warriors\",\"nickname\":\"GSW\",\"sports\":\"NBA\",\"teamName\":\"Warriors\"},{\"city\":\"Houston\",\"fullName\":\"Houston Rockets\",\"nickname\":\"HOU\",\"sports\":\"NBA\",\"teamName\":\"Rockets\"},{\"city\":\"Indianapolis\",\"fullName\":\"Indianapolis Pacers\",\"nickname\":\"IND\",\"sports\":\"NBA\",\"teamName\":\"Pacers\"},{\"city\":\"L.A. Clippers\",\"fullName\":\"Los Angeles Clippers\",\"nickname\":\"LAC\",\"sports\":\"NBA\",\"teamName\":\"Clippers\"},{\"city\":\"L.A. Lakers\",\"fullName\":\"Los Angeles Lakers\",\"nickname\":\"LAL\",\"sports\":\"NBA\",\"teamName\":\"Lakers\"},{\"city\":\"Memphis\",\"fullName\":\"Memphis Grizzlies\",\"nickname\":\"MEM\",\"sports\":\"NBA\",\"teamName\":\"Grizzlies\"},{\"city\":\"Miami\",\"fullName\":\"Miami Heat\",\"nickname\":\"MIA\",\"sports\":\"NBA\",\"teamName\":\"Heat\"},{\"city\":\"Milwaukee\",\"fullName\":\"Milwaukee Bucks\",\"nickname\":\"MIL\",\"sports\":\"NBA\",\"teamName\":\"Bucks\"},{\"city\":\"Minnesota\",\"fullName\":\"Minnesota Timberwolves\",\"nickname\":\"MIN\",\"sports\":\"NBA\",\"teamName\":\"Timberwolves\"},{\"city\":\"New Orleans\",\"fullName\":\"New Orleans Pelicans\",\"nickname\":\"NOP\",\"sports\":\"NBA\",\"teamName\":\"Pelicans\"},{\"city\":\"New York\",\"fullName\":\"New York Knicks\",\"nickname\":\"NYK\",\"sports\":\"NBA\",\"teamName\":\"Knicks\"},{\"city\":\"Oklahoma City\",\"fullName\":\"Oklahoma City Thunder\",\"nickname\":\"OKC\",\"sports\":\"NBA\",\"teamName\":\"Thunder\"},{\"city\":\"Orlando\",\"fullName\":\"Orlando Magic\",\"nickname\":\"ORL\",\"sports\":\"NBA\",\"teamName\":\"Magic\"},{\"city\":\"Philadelphia\",\"fullName\":\"Philadelphia 76ers\",\"nickname\":\"PHI\",\"sports\":\"NBA\",\"teamName\":\"76ers\"},{\"city\":\"Phoenix\",\"fullName\":\"Phoenix Suns\",\"nickname\":\"PHX\",\"sports\":\"NBA\",\"teamName\":\"Suns\"},{\"city\":\"Portland\",\"fullName\":\"Portland Trailblazers\",\"nickname\":\"POR\",\"sports\":\"NBA\",\"teamName\":\"Trailblazers\"},{\"city\":\"Sacramento\",\"fullName\":\"Sacramento Kings\",\"nickname\":\"SAC\",\"sports\":\"NBA\",\"teamName\":\"Kings\"},{\"city\":\"San Antonio\",\"fullName\":\"San Antonio Spurs\",\"nickname\":\"SAS\",\"sports\":\"NBA\",\"teamName\":\"Spurs\"},{\"city\":\"Toronto\",\"fullName\":\"Toronto Raptors\",\"nickname\":\"TOR\",\"sports\":\"NBA\",\"teamName\":\"Raptors\"},{\"city\":\"Utah\",\"fullName\":\"Utah Jazz\",\"nickname\":\"UTA\",\"sports\":\"NBA\",\"teamName\":\"Jazz\"},{\"city\":\"Washington\",\"fullName\":\"Washington Wizards\",\"nickname\":\"WAS\",\"sports\":\"NBA\",\"teamName\":\"Wizards\"},{\"city\":\"Arizona\",\"fullName\":\"Arizona Cardinals\",\"nickname\":\"ARI\",\"sports\":\"NFL\",\"teamName\":\"Cardinals\"},{\"city\":\"Atlanta\",\"fullName\":\"Atlanta Falcons\",\"nickname\":\"ATL\",\"sports\":\"NFL\",\"teamName\":\"Falcons\"},{\"city\":\"Baltimore\",\"fullName\":\"Baltimore Ravens\",\"nickname\":\"BAL\",\"sports\":\"NFL\",\"teamName\":\"Ravens\"},{\"city\":\"Buffalo\",\"fullName\":\"Buffalo Bills\",\"nickname\":\"BUF\",\"sports\":\"NFL\",\"teamName\":\"Bills\"},{\"city\":\"Carolina\",\"fullName\":\"Carolina Panthers\",\"nickname\":\"CAR\",\"sports\":\"NFL\",\"teamName\":\"Panthers\"},{\"city\":\"Chicago\",\"fullName\":\"Chicago Bears\",\"nickname\":\"CHI\",\"sports\":\"NFL\",\"teamName\":\"Bears\"},{\"city\":\"Cincinnati\",\"fullName\":\"Cincinnati Bengals\",\"nickname\":\"CIN\",\"sports\":\"NFL\",\"teamName\":\"Bengals\"},{\"city\":\"Cleveland\",\"fullName\":\"Cleveland Browns\",\"nickname\":\"CLE\",\"sports\":\"NFL\",\"teamName\":\"Browns\"},{\"city\":\"Dallas\",\"fullName\":\"Dallas Cowboys\",\"nickname\":\"DAL\",\"sports\":\"NFL\",\"teamName\":\"Cowboys\"},{\"city\":\"Denver\",\"fullName\":\"Denver Broncos\",\"nickname\":\"DEN\",\"sports\":\"NFL\",\"teamName\":\"Broncos\"},{\"city\":\"Detroit\",\"fullName\":\"Detroit Lions\",\"nickname\":\"DET\",\"sports\":\"NFL\",\"teamName\":\"Lions\"},{\"city\":\"Green Bay\",\"fullName\":\"Green Bay Packers\",\"nickname\":\"GB\",\"sports\":\"NFL\",\"teamName\":\"Packers\"},{\"city\":\"Houston\",\"fullName\":\"Houston Texans\",\"nickname\":\"HOU\",\"sports\":\"NFL\",\"teamName\":\"Texans\"},{\"city\":\"Indianapolis\",\"fullName\":\"Indianapolis Colts\",\"nickname\":\"IND\",\"sports\":\"NFL\",\"teamName\":\"Colts\"},{\"city\":\"Jacksonville\",\"fullName\":\"Jacksonville Jaguars\",\"nickname\":\"JAC\",\"sports\":\"NFL\",\"teamName\":\"Jaguars\"},{\"city\":\"Kansas City\",\"fullName\":\"Kansas City Chiefs\",\"nickname\":\"KC\",\"sports\":\"NFL\",\"teamName\":\"Chiefs\"},{\"city\":\"L.A. Chargers\",\"fullName\":\"Los Angeles Chargers\",\"nickname\":\"LAC\",\"sports\":\"NFL\",\"teamName\":\"Chargers\"},{\"city\":\"L.A. Rams\",\"fullName\":\"Los Angeles Rams\",\"nickname\":\"LAR\",\"sports\":\"NFL\",\"teamName\":\"Rams\"},{\"city\":\"Miami\",\"fullName\":\"Miami Dolphins\",\"nickname\":\"MIA\",\"sports\":\"NFL\",\"teamName\":\"Dolphins\"},{\"city\":\"Minnesota\",\"fullName\":\"Minnesota Vikings\",\"nickname\":\"MIN\",\"sports\":\"NFL\",\"teamName\":\"Vikings\"},{\"city\":\"New England\",\"fullName\":\"New England Patriots\",\"nickname\":\"NE\",\"sports\":\"NFL\",\"teamName\":\"Patriots\"},{\"city\":\"New Orleans\",\"fullName\":\"New Orleans Saints\",\"nickname\":\"NO\",\"sports\":\"NFL\",\"teamName\":\"Saints\"},{\"city\":\"N.Y. Giants\",\"fullName\":\"New York Giants\",\"nickname\":\"NYG\",\"sports\":\"NFL\",\"teamName\":\"Giants\"},{\"city\":\"N.Y Jets\",\"fullName\":\"New York Jets\",\"nickname\":\"NYJ\",\"sports\":\"NFL\",\"teamName\":\"Jets\"},{\"city\":\"Oakland\",\"fullName\":\"Oakland Raiders\",\"nickname\":\"OAK\",\"sports\":\"NFL\",\"teamName\":\"Raiders\"},{\"city\":\"Philadelphia\",\"fullName\":\"Philadelphia Eagles\",\"nickname\":\"PHI\",\"sports\":\"NFL\",\"teamName\":\"Eagles\"},{\"city\":\"Pittsburgh\",\"fullName\":\"Pittsburgh Steelers\",\"nickname\":\"PIT\",\"sports\":\"NFL\",\"teamName\":\"Steelers\"},{\"city\":\"San Francisco\",\"fullName\":\"San Francisco 49ers\",\"nickname\":\"SF\",\"sports\":\"NFL\",\"teamName\":\"49ers\"},{\"city\":\"Seattle\",\"fullName\":\"Seattle Seahawks\",\"nickname\":\"SEA\",\"sports\":\"NFL\",\"teamName\":\"Seahawks\"},{\"city\":\"Tampa Bay\",\"fullName\":\"Tampa Bay Tampa Bay\",\"nickname\":\"TB\",\"sports\":\"NFL\",\"teamName\":\"Tampa Bay\"},{\"city\":\"Tennessee\",\"fullName\":\"Tennessee Titans\",\"nickname\":\"TEN\",\"sports\":\"NFL\",\"teamName\":\"Titans\"},{\"city\":\"Washington\",\"fullName\":\"Washington Redskins\",\"nickname\":\"WAS\",\"sports\":\"NFL\",\"teamName\":\"Redskins\"},{\"city\":\"Anaheim\",\"fullName\":\"Anaheim Ducks\",\"nickname\":\"ANA\",\"sports\":\"NHL\",\"teamName\":\"Ducks\"},{\"city\":\"Arizona\",\"fullName\":\"Arizona Coyotes\",\"nickname\":\"ARI\",\"sports\":\"NHL\",\"teamName\":\"Coyotes\"},{\"city\":\"Boston\",\"fullName\":\"Boston Bruins\",\"nickname\":\"BOS\",\"sports\":\"NHL\",\"teamName\":\"Bruins\"},{\"city\":\"Buffalo\",\"fullName\":\"Buffalo Sabres\",\"nickname\":\"BUF\",\"sports\":\"NHL\",\"teamName\":\"Sabres\"},{\"city\":\"Calgary\",\"fullName\":\"Calgary Flames\",\"nickname\":\"CGY\",\"sports\":\"NHL\",\"teamName\":\"Flames\"},{\"city\":\"Carolina\",\"fullName\":\"Carolina Hurricanes\",\"nickname\":\"CAR\",\"sports\":\"NHL\",\"teamName\":\"Hurricanes\"},{\"city\":\"Chicago\",\"fullName\":\"Chicago Blackhawks\",\"nickname\":\"CHI\",\"sports\":\"NHL\",\"teamName\":\"Blackhawks\"},{\"city\":\"Colorado\",\"fullName\":\"Colorado Avalanche\",\"nickname\":\"COL\",\"sports\":\"NHL\",\"teamName\":\"Avalanche\"},{\"city\":\"Columbus\",\"fullName\":\"Columbus Blue Jackets\",\"nickname\":\"CBJ\",\"sports\":\"NHL\",\"teamName\":\"Blue Jackets\"},{\"city\":\"Dallas\",\"fullName\":\"Dallas Stars\",\"nickname\":\"DAL\",\"sports\":\"NHL\",\"teamName\":\"Stars\"},{\"city\":\"Detroit\",\"fullName\":\"Detroit Red Wings\",\"nickname\":\"DET\",\"sports\":\"NHL\",\"teamName\":\"Red Wings\"},{\"city\":\"Edmonton\",\"fullName\":\"Edmonton Oilers\",\"nickname\":\"EDM\",\"sports\":\"NHL\",\"teamName\":\"Oilers\"},{\"city\":\"Florida\",\"fullName\":\"Florida Panthers\",\"nickname\":\"FLA\",\"sports\":\"NHL\",\"teamName\":\"Panthers\"},{\"city\":\"Las Vegas\",\"fullName\":\"Las Vegas Golden Knights\",\"nickname\":\"VGK\",\"sports\":\"NHL\",\"teamName\":\"Golden Knights\"},{\"city\":\"Los Angeles\",\"fullName\":\"Los Angeles Kings\",\"nickname\":\"LAK\",\"sports\":\"NHL\",\"teamName\":\"Kings\"},{\"city\":\"Minnesota\",\"fullName\":\"Minnesota Wild\",\"nickname\":\"MIN\",\"sports\":\"NHL\",\"teamName\":\"Wild\"},{\"city\":\"Montreal\",\"fullName\":\"Montreal Canadiens\",\"nickname\":\"MTL\",\"sports\":\"NHL\",\"teamName\":\"Canadiens\"},{\"city\":\"Nashville\",\"fullName\":\"Nashville Predators\",\"nickname\":\"NSH\",\"sports\":\"NHL\",\"teamName\":\"Predators\"},{\"city\":\"New Jersey\",\"fullName\":\"New Jersey Devils\",\"nickname\":\"NJD\",\"sports\":\"NHL\",\"teamName\":\"Devils\"},{\"city\":\"N.Y. Islanders\",\"fullName\":\"N.Y. Islanders Islanders\",\"nickname\":\"NYI\",\"sports\":\"NHL\",\"teamName\":\"Islanders\"},{\"city\":\"N.Y. Rangers\",\"fullName\":\"N.Y. Rangers Rangers\",\"nickname\":\"NYR\",\"sports\":\"NHL\",\"teamName\":\"Rangers\"},{\"city\":\"Ottawa\",\"fullName\":\"Ottawa Senators\",\"nickname\":\"OTT\",\"sports\":\"NHL\",\"teamName\":\"Senators\"},{\"city\":\"Philadelphia\",\"fullName\":\"Philadelphia Flyers\",\"nickname\":\"PHI\",\"sports\":\"NHL\",\"teamName\":\"Flyers\"},{\"city\":\"Pittsburgh\",\"fullName\":\"Pittsburgh Penguins\",\"nickname\":\"PIT\",\"sports\":\"NHL\",\"teamName\":\"Penguins\"},{\"city\":\"San Jose\",\"fullName\":\"San Jose Sharks\",\"nickname\":\"SJS\",\"sports\":\"NHL\",\"teamName\":\"Sharks\"},{\"city\":\"St. Louis\",\"fullName\":\"St. Louis Blues\",\"nickname\":\"STL\",\"sports\":\"NHL\",\"teamName\":\"Blues\"},{\"city\":\"Tampa Bay\",\"fullName\":\"Tampa Bay Lightning\",\"nickname\":\"TBL\",\"sports\":\"NHL\",\"teamName\":\"Lightning\"},{\"city\":\"Toronto\",\"fullName\":\"Toronto Maple Leafs\",\"nickname\":\"TOR\",\"sports\":\"NHL\",\"teamName\":\"Maple Leafs\"},{\"city\":\"Vancouver\",\"fullName\":\"Vancouver Canucks\",\"nickname\":\"VAN\",\"sports\":\"NHL\",\"teamName\":\"Canucks\"},{\"city\":\"Washington\",\"fullName\":\"Washington Capitals\",\"nickname\":\"WSH\",\"sports\":\"NHL\",\"teamName\":\"Capitals\"},{\"city\":\"Winnipeg\",\"fullName\":\"Winnipeg Jets\",\"nickname\":\"WPG\",\"sports\":\"NHL\",\"teamName\":\"Jets\"},{\"fullName\":\"Afghanistan\",\"nickname\":\"AFG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Albania\",\"nickname\":\"ALB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Algeria\",\"nickname\":\"DZA\",\"sports\":\"International Soccer\"},{\"fullName\":\"American Samoa\",\"nickname\":\"ASM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Andorra\",\"nickname\":\"AND\",\"sports\":\"International Soccer\"},{\"fullName\":\"Angola\",\"nickname\":\"AGO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Anguilla\",\"nickname\":\"AIA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Antarctica\",\"nickname\":\"ATA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Antigua and Barbuda\",\"nickname\":\"ATG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Argentina\",\"nickname\":\"ARG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Armenia\",\"nickname\":\"ARM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Aruba\",\"nickname\":\"ABW\",\"sports\":\"International Soccer\"},{\"fullName\":\"Australia\",\"nickname\":\"AUS\",\"sports\":\"International Soccer\"},{\"fullName\":\"Austria\",\"nickname\":\"AUT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Azerbaijan\",\"nickname\":\"AZE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bahamas\",\"nickname\":\"BHS\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bahrain\",\"nickname\":\"BHR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bangladesh\",\"nickname\":\"BGD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Barbados\",\"nickname\":\"BRB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Belarus\",\"nickname\":\"BLR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Belgium\",\"nickname\":\"BEL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Belize\",\"nickname\":\"BLZ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Benin\",\"nickname\":\"BEN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bermuda\",\"nickname\":\"BMU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bhutan\",\"nickname\":\"BTN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bolivia (Plurinational State of)\",\"nickname\":\"BOL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bonaire Sint Eustatius and Saba\",\"nickname\":\"BES\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bosnia and Herzegovina\",\"nickname\":\"BIH\",\"sports\":\"International Soccer\"},{\"fullName\":\"Botswana\",\"nickname\":\"BWA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bouvet Island\",\"nickname\":\"BVT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Brazil\",\"nickname\":\"BRA\",\"sports\":\"International Soccer\"},{\"fullName\":\"British Indian Ocean Territory\",\"nickname\":\"IOT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Brunei Darussalam\",\"nickname\":\"BRN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Bulgaria\",\"nickname\":\"BGR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Burkina Faso\",\"nickname\":\"BFA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Burundi\",\"nickname\":\"BDI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cabo Verde\",\"nickname\":\"CPV\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cambodia\",\"nickname\":\"KHM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cameroon\",\"nickname\":\"CMR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Canada\",\"nickname\":\"CAN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cayman Islands\",\"nickname\":\"CYM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Central African Republic\",\"nickname\":\"CAF\",\"sports\":\"International Soccer\"},{\"fullName\":\"Chad\",\"nickname\":\"TCD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Chile\",\"nickname\":\"CHL\",\"sports\":\"International Soccer\"},{\"fullName\":\"China\",\"nickname\":\"CHN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Christmas Island\",\"nickname\":\"CXR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cocos (Keeling) Islands\",\"nickname\":\"CCK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Colombia\",\"nickname\":\"COL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Comoros\",\"nickname\":\"COM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Congo\",\"nickname\":\"COG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Congo (Democratic Republic of the)\",\"nickname\":\"COD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cook Islands\",\"nickname\":\"COK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Costa Rica\",\"nickname\":\"CRI\",\"sports\":\"International Soccer\"},{\"fullName\":\"C�te d'Ivoire\",\"nickname\":\"CIV\",\"sports\":\"International Soccer\"},{\"fullName\":\"Croatia\",\"nickname\":\"CRO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cuba\",\"nickname\":\"CUB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cura�ao\",\"nickname\":\"CUW\",\"sports\":\"International Soccer\"},{\"fullName\":\"Cyprus\",\"nickname\":\"CYP\",\"sports\":\"International Soccer\"},{\"fullName\":\"Czech Republic\",\"nickname\":\"CZE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Denmark\",\"nickname\":\"DNK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Djibouti\",\"nickname\":\"DJI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Dominica\",\"nickname\":\"DMA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Dominican Republic\",\"nickname\":\"DOM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Ecuador\",\"nickname\":\"ECU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Egypt\",\"nickname\":\"EGY\",\"sports\":\"International Soccer\"},{\"fullName\":\"El Salvador\",\"nickname\":\"SLV\",\"sports\":\"International Soccer\"},{\"fullName\":\"England\",\"nickname\":\"ENG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Equatorial Guinea\",\"nickname\":\"GNQ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Eritrea\",\"nickname\":\"ERI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Estonia\",\"nickname\":\"EST\",\"sports\":\"International Soccer\"},{\"fullName\":\"Ethiopia\",\"nickname\":\"ETH\",\"sports\":\"International Soccer\"},{\"fullName\":\"Falkland Islands (Malvinas)\",\"nickname\":\"FLK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Faroe Islands\",\"nickname\":\"FRO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Fiji\",\"nickname\":\"FJI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Finland\",\"nickname\":\"FIN\",\"sports\":\"International Soccer\"},{\"fullName\":\"France\",\"nickname\":\"FRA\",\"sports\":\"International Soccer\"},{\"fullName\":\"French Guiana\",\"nickname\":\"GUF\",\"sports\":\"International Soccer\"},{\"fullName\":\"French Polynesia\",\"nickname\":\"PYF\",\"sports\":\"International Soccer\"},{\"fullName\":\"French Southern Territories\",\"nickname\":\"ATF\",\"sports\":\"International Soccer\"},{\"fullName\":\"Gabon\",\"nickname\":\"GAB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Gambia\",\"nickname\":\"GMB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Georgia\",\"nickname\":\"GEO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Germany\",\"nickname\":\"GER\",\"sports\":\"International Soccer\"},{\"fullName\":\"Ghana\",\"nickname\":\"GHA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Gibraltar\",\"nickname\":\"GIB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Greece\",\"nickname\":\"GRC\",\"sports\":\"International Soccer\"},{\"fullName\":\"Greenland\",\"nickname\":\"GRL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Grenada\",\"nickname\":\"GRD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guadeloupe\",\"nickname\":\"GLP\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guam\",\"nickname\":\"GUM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guatemala\",\"nickname\":\"GTM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guernsey\",\"nickname\":\"GGY\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guinea\",\"nickname\":\"GIN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guinea-Bissau\",\"nickname\":\"GNB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Guyana\",\"nickname\":\"GUY\",\"sports\":\"International Soccer\"},{\"fullName\":\"Haiti\",\"nickname\":\"HTI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Heard Island and McDonald Islands\",\"nickname\":\"HMD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Holy See\",\"nickname\":\"VAT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Honduras\",\"nickname\":\"HND\",\"sports\":\"International Soccer\"},{\"fullName\":\"Hong Kong\",\"nickname\":\"HKG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Hungary\",\"nickname\":\"HUN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Iceland\",\"nickname\":\"ICE\",\"sports\":\"International Soccer\"},{\"fullName\":\"India\",\"nickname\":\"IND\",\"sports\":\"International Soccer\"},{\"fullName\":\"Indonesia\",\"nickname\":\"IDN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Iran (Islamic Republic of)\",\"nickname\":\"IRN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Iraq\",\"nickname\":\"IRQ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Ireland\",\"nickname\":\"IRL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Isle of Man\",\"nickname\":\"IMN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Israel\",\"nickname\":\"ISR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Italy\",\"nickname\":\"ITA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Jamaica\",\"nickname\":\"JAM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Japan\",\"nickname\":\"JPN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Jersey\",\"nickname\":\"JEY\",\"sports\":\"International Soccer\"},{\"fullName\":\"Jordan\",\"nickname\":\"JOR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Kazakhstan\",\"nickname\":\"KAZ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Kenya\",\"nickname\":\"KEN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Kiribati\",\"nickname\":\"KIR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Korea (Democratic People's Republic of)\",\"nickname\":\"PRK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Korea (Republic of)\",\"nickname\":\"KOR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Kosovo\",\"nickname\":\"KVX\",\"sports\":\"International Soccer\"},{\"fullName\":\"Kuwait\",\"nickname\":\"KWT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Kyrgyzstan\",\"nickname\":\"KGZ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Lao People's Democratic Republic\",\"nickname\":\"LAO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Latvia\",\"nickname\":\"LVA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Lebanon\",\"nickname\":\"LBN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Lesotho\",\"nickname\":\"LSO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Liberia\",\"nickname\":\"LBR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Libya\",\"nickname\":\"LBY\",\"sports\":\"International Soccer\"},{\"fullName\":\"Liechtenstein\",\"nickname\":\"LIE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Lithuania\",\"nickname\":\"LTU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Luxembourg\",\"nickname\":\"LUX\",\"sports\":\"International Soccer\"},{\"fullName\":\"Macao\",\"nickname\":\"MAC\",\"sports\":\"International Soccer\"},{\"fullName\":\"Macedonia (the former Yugoslav Republic of)\",\"nickname\":\"MKD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Madagascar\",\"nickname\":\"MDG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Malawi\",\"nickname\":\"MWI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Malaysia\",\"nickname\":\"MYS\",\"sports\":\"International Soccer\"},{\"fullName\":\"Maldives\",\"nickname\":\"MDV\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mali\",\"nickname\":\"MLI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Malta\",\"nickname\":\"MLT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Marshall Islands\",\"nickname\":\"MHL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Martinique\",\"nickname\":\"MTQ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mauritania\",\"nickname\":\"MRT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mauritius\",\"nickname\":\"MUS\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mayotte\",\"nickname\":\"MYT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mexico\",\"nickname\":\"MEX\",\"sports\":\"International Soccer\"},{\"fullName\":\"Micronesia (Federated States of)\",\"nickname\":\"FSM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Moldova (Republic of)\",\"nickname\":\"MDA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Monaco\",\"nickname\":\"MCO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mongolia\",\"nickname\":\"MNG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Montenegro\",\"nickname\":\"MNE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Montserrat\",\"nickname\":\"MSR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Morocco\",\"nickname\":\"MAR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Mozambique\",\"nickname\":\"MOZ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Myanmar\",\"nickname\":\"MMR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Namibia\",\"nickname\":\"NAM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Nauru\",\"nickname\":\"NRU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Nepal\",\"nickname\":\"NPL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Netherlands\",\"nickname\":\"NLD\",\"sports\":\"International Soccer\"},{\"fullName\":\"New Caledonia\",\"nickname\":\"NCL\",\"sports\":\"International Soccer\"},{\"fullName\":\"New Zealand\",\"nickname\":\"NZL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Nicaragua\",\"nickname\":\"NIC\",\"sports\":\"International Soccer\"},{\"fullName\":\"Niger\",\"nickname\":\"NER\",\"sports\":\"International Soccer\"},{\"fullName\":\"Nigeria\",\"nickname\":\"NGA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Niue\",\"nickname\":\"NIU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Norfolk Island\",\"nickname\":\"NFK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Northern Ireland\",\"nickname\":\"NIR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Northern Mariana Islands\",\"nickname\":\"MNP\",\"sports\":\"International Soccer\"},{\"fullName\":\"Norway\",\"nickname\":\"NOR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Oman\",\"nickname\":\"OMN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Pakistan\",\"nickname\":\"PAK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Palau\",\"nickname\":\"PLW\",\"sports\":\"International Soccer\"},{\"fullName\":\"Palestine State of Palestine\",\"nickname\":\"PSE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Panama\",\"nickname\":\"PAN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Papua New Guinea\",\"nickname\":\"PNG\",\"sports\":\"International Soccer\"},{\"fullName\":\"Paraguay\",\"nickname\":\"PRY\",\"sports\":\"International Soccer\"},{\"fullName\":\"Peru\",\"nickname\":\"PER\",\"sports\":\"International Soccer\"},{\"fullName\":\"Philippines\",\"nickname\":\"PHL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Pitcairn\",\"nickname\":\"PCN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Poland\",\"nickname\":\"POL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Portugal\",\"nickname\":\"POR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Puerto Rico\",\"nickname\":\"PRI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Qatar\",\"nickname\":\"QAT\",\"sports\":\"International Soccer\"},{\"fullName\":\"R�union\",\"nickname\":\"REU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Romania\",\"nickname\":\"ROM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Russian Federation\",\"nickname\":\"RUS\",\"sports\":\"International Soccer\"},{\"fullName\":\"Rwanda\",\"nickname\":\"RWA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Barth�lemy\",\"nickname\":\"BLM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Helena Ascension and Tristan da Cunha\",\"nickname\":\"SHN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Kitts and Nevis\",\"nickname\":\"KNA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Lucia\",\"nickname\":\"LCA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Martin (French part)\",\"nickname\":\"MAF\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Pierre and Miquelon\",\"nickname\":\"SPM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saint Vincent and the Grenadines\",\"nickname\":\"VCT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Samoa\",\"nickname\":\"WSM\",\"sports\":\"International Soccer\"},{\"fullName\":\"San Marino\",\"nickname\":\"SMR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Sao Tome and Principe\",\"nickname\":\"STP\",\"sports\":\"International Soccer\"},{\"fullName\":\"Saudi Arabia\",\"nickname\":\"SAU\",\"sports\":\"International Soccer\"},{\"fullName\":\"Scotland\",\"nickname\":\"SCO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Senegal\",\"nickname\":\"SEN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Serbia\",\"nickname\":\"SRB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Seychelles\",\"nickname\":\"SYC\",\"sports\":\"International Soccer\"},{\"fullName\":\"Sierra Leone\",\"nickname\":\"SLE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Singapore\",\"nickname\":\"SGP\",\"sports\":\"International Soccer\"},{\"fullName\":\"Sint Maarten (Dutch part)\",\"nickname\":\"SXM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Slovakia\",\"nickname\":\"SVK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Slovenia\",\"nickname\":\"SVN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Solomon Islands\",\"nickname\":\"SLB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Somalia\",\"nickname\":\"SOM\",\"sports\":\"International Soccer\"},{\"fullName\":\"South Africa\",\"nickname\":\"ZAF\",\"sports\":\"International Soccer\"},{\"fullName\":\"South Georgia and the South Sandwich Islands\",\"nickname\":\"SGS\",\"sports\":\"International Soccer\"},{\"fullName\":\"South Sudan\",\"nickname\":\"SSD\",\"sports\":\"International Soccer\"},{\"fullName\":\"Spain\",\"nickname\":\"ESP\",\"sports\":\"International Soccer\"},{\"fullName\":\"Sri Lanka\",\"nickname\":\"LKA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Sudan\",\"nickname\":\"SDN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Suriname\",\"nickname\":\"SUR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Svalbard and Jan Mayen\",\"nickname\":\"SJM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Swaziland\",\"nickname\":\"SWZ\",\"sports\":\"International Soccer\"},{\"fullName\":\"Sweden\",\"nickname\":\"SWE\",\"sports\":\"International Soccer\"},{\"fullName\":\"Switzerland\",\"nickname\":\"SWI\",\"sports\":\"International Soccer\"},{\"fullName\":\"Syrian Arab Republic\",\"nickname\":\"SYR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tahiti\",\"nickname\":\"TAH\",\"sports\":\"International Soccer\"},{\"fullName\":\"Taiwan Province of China\",\"nickname\":\"TWN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tajikistan\",\"nickname\":\"TJK\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tanzania United Republic of Tanzania\",\"nickname\":\"TZA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Thailand\",\"nickname\":\"THA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Timor-Leste\",\"nickname\":\"TLS\",\"sports\":\"International Soccer\"},{\"fullName\":\"Togo\",\"nickname\":\"TGO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tokelau\",\"nickname\":\"TKL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tonga\",\"nickname\":\"TON\",\"sports\":\"International Soccer\"},{\"fullName\":\"Trinidad and Tobago\",\"nickname\":\"TTO\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tunisia\",\"nickname\":\"TUN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Turkey\",\"nickname\":\"TUR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Turkmenistan\",\"nickname\":\"TKM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Turks and Caicos Islands\",\"nickname\":\"TCA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Tuvalu\",\"nickname\":\"TUV\",\"sports\":\"International Soccer\"},{\"fullName\":\"Uganda\",\"nickname\":\"UGA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Ukraine\",\"nickname\":\"UKR\",\"sports\":\"International Soccer\"},{\"fullName\":\"United Arab Emirates\",\"nickname\":\"ARE\",\"sports\":\"International Soccer\"},{\"fullName\":\"United Kingdom of Great Britain and Northern Ireland\",\"nickname\":\"GBR\",\"sports\":\"International Soccer\"},{\"fullName\":\"United States Minor Outlying Islands\",\"nickname\":\"UMI\",\"sports\":\"International Soccer\"},{\"fullName\":\"United States of America\",\"nickname\":\"USA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Uruguay\",\"nickname\":\"URY\",\"sports\":\"International Soccer\"},{\"fullName\":\"Uzbekistan\",\"nickname\":\"UZB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Vanuatu\",\"nickname\":\"VUT\",\"sports\":\"International Soccer\"},{\"fullName\":\"Venezuela (Bolivarian Republic of)\",\"nickname\":\"VEN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Viet Nam\",\"nickname\":\"VNM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Virgin Islands (British)\",\"nickname\":\"VGB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Virgin Islands (U.S.)\",\"nickname\":\"VIR\",\"sports\":\"International Soccer\"},{\"fullName\":\"Wales\",\"nickname\":\"WAL\",\"sports\":\"International Soccer\"},{\"fullName\":\"Wallis and Futuna\",\"nickname\":\"WLF\",\"sports\":\"International Soccer\"},{\"fullName\":\"West Indies\",\"nickname\":\"WIN\",\"sports\":\"International Soccer\"},{\"fullName\":\"Western Sahara\",\"nickname\":\"ESH\",\"sports\":\"International Soccer\"},{\"fullName\":\"Yemen\",\"nickname\":\"YEM\",\"sports\":\"International Soccer\"},{\"fullName\":\"Zambia\",\"nickname\":\"ZMB\",\"sports\":\"International Soccer\"},{\"fullName\":\"Zimbabwe\",\"nickname\":\"ZWE\",\"sports\":\"International Soccer\"},{\"fullName\":\"�land Islands\",\"nickname\":\"ALA\",\"sports\":\"International Soccer\"},{\"fullName\":\"Air Force (CO)\",\"nickname\":\"AF\",\"sports\":\"College Football\"},{\"fullName\":\"Akron (OH)\",\"nickname\":\"AKRN\",\"sports\":\"College Football\"},{\"fullName\":\"Alabama\",\"nickname\":\"BAMA\",\"sports\":\"College Football\"},{\"fullName\":\"Alabama-Birmingham\",\"nickname\":\"UAB\",\"sports\":\"College Football\"},{\"fullName\":\"Arizona\",\"nickname\":\"ARIZ\",\"sports\":\"College Football\"},{\"fullName\":\"Arizona St.\",\"nickname\":\"AZST\",\"sports\":\"College Football\"},{\"fullName\":\"Arkansas\",\"nickname\":\"ARK\",\"sports\":\"College Football\"},{\"fullName\":\"Arkansas St.\",\"nickname\":\"ARST\",\"sports\":\"College Football\"},{\"fullName\":\"Army (NY)\",\"nickname\":\"ARMY\",\"sports\":\"College Football\"},{\"fullName\":\"Auburn\",\"nickname\":\"AUB\",\"sports\":\"College Football\"},{\"fullName\":\"Ball St. (IN)\",\"nickname\":\"BALL\",\"sports\":\"College Football\"},{\"fullName\":\"Baylor (TX)\",\"nickname\":\"BAY\",\"sports\":\"College Football\"},{\"fullName\":\"Boise St. (ID)\",\"nickname\":\"BOISE\",\"sports\":\"College Football\"},{\"fullName\":\"Boston College (MA)\",\"nickname\":\"BC\",\"sports\":\"College Football\"},{\"fullName\":\"Bowling Green (OH)\",\"nickname\":\"BG\",\"sports\":\"College Football\"},{\"fullName\":\"Brigham Young (UT)\",\"nickname\":\"BYU\",\"sports\":\"College Football\"},{\"fullName\":\"Buffalo (NY)\",\"nickname\":\"BUF\",\"sports\":\"College Football\"},{\"fullName\":\"California\",\"nickname\":\"CAL\",\"sports\":\"College Football\"},{\"fullName\":\"Central Florida\",\"nickname\":\"UCF\",\"sports\":\"College Football\"},{\"fullName\":\"Central Michigan\",\"nickname\":\"CMU\",\"sports\":\"College Football\"},{\"fullName\":\"Cincinnati (OH)\",\"nickname\":\"CIN\",\"sports\":\"College Football\"},{\"fullName\":\"Clemson (SC)\",\"nickname\":\"CLEM\",\"sports\":\"College Football\"},{\"fullName\":\"Colorado\",\"nickname\":\"COL\",\"sports\":\"College Football\"},{\"fullName\":\"Colorado St.\",\"nickname\":\"CSU\",\"sports\":\"College Football\"},{\"fullName\":\"Connecticut\",\"nickname\":\"CONN\",\"sports\":\"College Football\"},{\"fullName\":\"Duke (NC)\",\"nickname\":\"DUKE\",\"sports\":\"College Football\"},{\"fullName\":\"East Carolina (NC)\",\"nickname\":\"ECU\",\"sports\":\"College Football\"},{\"fullName\":\"Eastern Michigan\",\"nickname\":\"EMU\",\"sports\":\"College Football\"},{\"fullName\":\"Florida\",\"nickname\":\"UF\",\"sports\":\"College Football\"},{\"fullName\":\"Florida Atlantic\",\"nickname\":\"FAU\",\"sports\":\"College Football\"},{\"fullName\":\"Florida International\",\"nickname\":\"FIU\",\"sports\":\"College Football\"},{\"fullName\":\"Florida St.\",\"nickname\":\"FSU\",\"sports\":\"College Football\"},{\"fullName\":\"Fresno St. (CA)\",\"nickname\":\"FRSNO\",\"sports\":\"College Football\"},{\"fullName\":\"Georgia\",\"nickname\":\"UGA\",\"sports\":\"College Football\"},{\"fullName\":\"Georgia Tech\",\"nickname\":\"GTECH\",\"sports\":\"College Football\"},{\"fullName\":\"Hawaii\",\"nickname\":\"HAW\",\"sports\":\"College Football\"},{\"fullName\":\"Houston (TX)\",\"nickname\":\"HOU\",\"sports\":\"College Football\"},{\"fullName\":\"Idaho\",\"nickname\":\"IDAHO\",\"sports\":\"College Football\"},{\"fullName\":\"Illinois\",\"nickname\":\"ILL\",\"sports\":\"College Football\"},{\"fullName\":\"Indiana\",\"nickname\":\"IND\",\"sports\":\"College Football\"},{\"fullName\":\"Iowa\",\"nickname\":\"IOWA\",\"sports\":\"College Football\"},{\"fullName\":\"Iowa St.\",\"nickname\":\"IAST\",\"sports\":\"College Football\"},{\"fullName\":\"Kansas\",\"nickname\":\"KAN\",\"sports\":\"College Football\"},{\"fullName\":\"Kansas St.\",\"nickname\":\"KSST\",\"sports\":\"College Football\"},{\"fullName\":\"Kent St. (OH)\",\"nickname\":\"KENT\",\"sports\":\"College Football\"},{\"fullName\":\"Kentucky\",\"nickname\":\"UK\",\"sports\":\"College Football\"},{\"fullName\":\"Louisiana St.\",\"nickname\":\"LSU\",\"sports\":\"College Football\"},{\"fullName\":\"Louisiana Tech\",\"nickname\":\"LTECH\",\"sports\":\"College Football\"},{\"fullName\":\"Louisiana-Lafayette\",\"nickname\":\"ULL\",\"sports\":\"College Football\"},{\"fullName\":\"Louisiana-Monroe\",\"nickname\":\"ULM\",\"sports\":\"College Football\"},{\"fullName\":\"Louisville (KY)\",\"nickname\":\"LOU\",\"sports\":\"College Football\"},{\"fullName\":\"Marshall (WV)\",\"nickname\":\"MRSHL\",\"sports\":\"College Football\"},{\"fullName\":\"Maryland\",\"nickname\":\"MARY\",\"sports\":\"College Football\"},{\"fullName\":\"Massachusetts\",\"nickname\":\"MASS\",\"sports\":\"College Football\"},{\"fullName\":\"Memphis (TN)\",\"nickname\":\"MEM\",\"sports\":\"College Football\"},{\"fullName\":\"Miami (FL)\",\"nickname\":\"MIAF\",\"sports\":\"College Football\"},{\"fullName\":\"Miami (OH)\",\"nickname\":\"MIAO\",\"sports\":\"College Football\"},{\"fullName\":\"Michigan\",\"nickname\":\"MICH\",\"sports\":\"College Football\"},{\"fullName\":\"Michigan St.\",\"nickname\":\"MIST\",\"sports\":\"College Football\"},{\"fullName\":\"Middle Tennessee\",\"nickname\":\"MTENN\",\"sports\":\"College Football\"},{\"fullName\":\"Minnesota\",\"nickname\":\"MINN\",\"sports\":\"College Football\"},{\"fullName\":\"Mississippi\",\"nickname\":\"MISS\",\"sports\":\"College Football\"},{\"fullName\":\"Mississippi St.\",\"nickname\":\"MSST\",\"sports\":\"College Football\"},{\"fullName\":\"Missouri\",\"nickname\":\"MIZZ\",\"sports\":\"College Football\"},{\"fullName\":\"Navy (MD)\",\"nickname\":\"NAVY\",\"sports\":\"College Football\"},{\"fullName\":\"Nebraska\",\"nickname\":\"NEB\",\"sports\":\"College Football\"},{\"fullName\":\"Nevada\",\"nickname\":\"NEV\",\"sports\":\"College Football\"},{\"fullName\":\"Nevada-Las Vegas\",\"nickname\":\"UNLV\",\"sports\":\"College Football\"},{\"fullName\":\"New Mexico\",\"nickname\":\"UNM\",\"sports\":\"College Football\"},{\"fullName\":\"New Mexico St.\",\"nickname\":\"NMST\",\"sports\":\"College Football\"},{\"fullName\":\"North Carolina\",\"nickname\":\"UNC\",\"sports\":\"College Football\"},{\"fullName\":\"North Carolina St.\",\"nickname\":\"NCST\",\"sports\":\"College Football\"},{\"fullName\":\"North Texas\",\"nickname\":\"NTEX\",\"sports\":\"College Football\"},{\"fullName\":\"Northern Illinois\",\"nickname\":\"NILL\",\"sports\":\"College Football\"},{\"fullName\":\"Northwestern (IL)\",\"nickname\":\"NWEST\",\"sports\":\"College Football\"},{\"fullName\":\"Notre Dame (IN)\",\"nickname\":\"NDAME\",\"sports\":\"College Football\"},{\"fullName\":\"Ohio\",\"nickname\":\"OHIO\",\"sports\":\"College Football\"},{\"fullName\":\"Ohio St.\",\"nickname\":\"OHST\",\"sports\":\"College Football\"},{\"fullName\":\"Oklahoma\",\"nickname\":\"OKLA\",\"sports\":\"College Football\"},{\"fullName\":\"Oklahoma St.\",\"nickname\":\"OKST\",\"sports\":\"College Football\"},{\"fullName\":\"Oregon\",\"nickname\":\"OREG\",\"sports\":\"College Football\"},{\"fullName\":\"Oregon St.\",\"nickname\":\"ORST\",\"sports\":\"College Football\"},{\"fullName\":\"Penn St. (PA)\",\"nickname\":\"PSU\",\"sports\":\"College Football\"},{\"fullName\":\"Pittsburgh\",\"nickname\":\"PITT\",\"sports\":\"College Football\"},{\"fullName\":\"Purdue (IN)\",\"nickname\":\"PURD\",\"sports\":\"College Football\"},{\"fullName\":\"Rice (TX)\",\"nickname\":\"RICE\",\"sports\":\"College Football\"},{\"fullName\":\"Rutgers (NJ)\",\"nickname\":\"RUTG\",\"sports\":\"College Football\"},{\"fullName\":\"San Diego St. (CA)\",\"nickname\":\"SDSU\",\"sports\":\"College Football\"},{\"fullName\":\"San Jose St. (CA)\",\"nickname\":\"SJSU\",\"sports\":\"College Football\"},{\"fullName\":\"South Alabama\",\"nickname\":\"SBAMA\",\"sports\":\"College Football\"},{\"fullName\":\"South Carolina\",\"nickname\":\"SCAR\",\"sports\":\"College Football\"},{\"fullName\":\"South Florida\",\"nickname\":\"USF\",\"sports\":\"College Football\"},{\"fullName\":\"Southern California\",\"nickname\":\"SCAL\",\"sports\":\"College Football\"},{\"fullName\":\"Southern Methodist (TX)\",\"nickname\":\"SMU\",\"sports\":\"College Football\"},{\"fullName\":\"Southern Mississippi\",\"nickname\":\"SMISS\",\"sports\":\"College Football\"},{\"fullName\":\"Stanford (CA)\",\"nickname\":\"STAN\",\"sports\":\"College Football\"},{\"fullName\":\"Syracuse (NY)\",\"nickname\":\"SYR\",\"sports\":\"College Football\"},{\"fullName\":\"Temple (PA)\",\"nickname\":\"TMPLE\",\"sports\":\"College Football\"},{\"fullName\":\"Tennessee\",\"nickname\":\"TENN\",\"sports\":\"College Football\"},{\"fullName\":\"Texas\",\"nickname\":\"TEX\",\"sports\":\"College Football\"},{\"fullName\":\"Texas A&M\",\"nickname\":\"TXAM\",\"sports\":\"College Football\"},{\"fullName\":\"Texas Christian\",\"nickname\":\"TCU\",\"sports\":\"College Football\"},{\"fullName\":\"Texas St.\",\"nickname\":\"TXST\",\"sports\":\"College Football\"},{\"fullName\":\"Texas Tech\",\"nickname\":\"TTECH\",\"sports\":\"College Football\"},{\"fullName\":\"Texas-El Paso\",\"nickname\":\"UTEP\",\"sports\":\"College Football\"},{\"fullName\":\"Texas-San Antonio\",\"nickname\":\"UTSA\",\"sports\":\"College Football\"},{\"fullName\":\"Toledo (OH)\",\"nickname\":\"TOL\",\"sports\":\"College Football\"},{\"fullName\":\"Troy (AL)\",\"nickname\":\"TROY\",\"sports\":\"College Football\"},{\"fullName\":\"Tulane (LA)\",\"nickname\":\"TLNE\",\"sports\":\"College Football\"},{\"fullName\":\"Tulsa (OK)\",\"nickname\":\"TULSA\",\"sports\":\"College Football\"},{\"fullName\":\"UCLA (CA)\",\"nickname\":\"UCLA\",\"sports\":\"College Football\"},{\"fullName\":\"Utah\",\"nickname\":\"UTAH\",\"sports\":\"College Football\"},{\"fullName\":\"Utah St.\",\"nickname\":\"UTST\",\"sports\":\"College Football\"},{\"fullName\":\"Vanderbilt (TN)\",\"nickname\":\"VAND\",\"sports\":\"College Football\"},{\"fullName\":\"Virginia\",\"nickname\":\"UVA\",\"sports\":\"College Football\"},{\"fullName\":\"Virginia Tech\",\"nickname\":\"VTECH\",\"sports\":\"College Football\"},{\"fullName\":\"Wake Forest (NC)\",\"nickname\":\"WAKE\",\"sports\":\"College Football\"},{\"fullName\":\"Washington\",\"nickname\":\"WASH\",\"sports\":\"College Football\"},{\"fullName\":\"Washington St.\",\"nickname\":\"WSU\",\"sports\":\"College Football\"},{\"fullName\":\"West Virginia\",\"nickname\":\"WVU\",\"sports\":\"College Football\"},{\"fullName\":\"Western Kentucky\",\"nickname\":\"WKU\",\"sports\":\"College Football\"},{\"fullName\":\"Western Michigan\",\"nickname\":\"WMU\",\"sports\":\"College Football\"},{\"fullName\":\"Wisconsin\",\"nickname\":\"WIS\",\"sports\":\"College Football\"},{\"fullName\":\"Wyoming\",\"nickname\":\"WYO\",\"sports\":\"College Football\"}]";
		Gson gsonObj = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray nicknameArray = parser.parse(nicknames).getAsJsonArray();
		for(int i=0;i<nicknameArray.size();i++){
			JsonElement nicknameElement = nicknameArray.get(i);
			Nickname nickname = gsonObj.fromJson(nicknameElement, Nickname.class);
			HashMap<String, Nickname> team = null;
			//check if sports is available
			if(NICKNAMES.keySet().contains(nickname.getSports())){
				team = NICKNAMES.get(nickname.getSports());
			} else {
				team = new HashMap<String, Nickname>();
				
			}
			
			team.put(nickname.getFullName(), nickname);
			NICKNAMES.put(nickname.getSports(), team);
		}
		
		for(String sportsKey : NICKNAMES.keySet()){
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+sportsKey);
			for(String team : NICKNAMES.get(sportsKey).keySet()){
				Nickname nn = NICKNAMES.get(sportsKey).get(team);
				System.out.println("===="+nn.getFullName()+" : "+nn.getNickname());
			}
		}*/
		
		String key ="eyJhbGciOiJSUzI1NiIsImtpZCI6IjE3ZTBmNDI1NjRlYjc0Y2FlNGZkNDhiZGE5ZjA0YmE2OTRmNDExNDQifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc2lkZXdhZ2VyLTE2MzgwMiIsIm5hbWUiOiJEZWJhbmphbiBCYW5lcmplZSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vLUpOajhjaWNCN3dvL0FBQUFBQUFBQUFJL0FBQUFBQUFBTDJjL0ZuNUt5NFllQ3JVL3Bob3RvLmpwZyIsImF1ZCI6InNpZGV3YWdlci0xNjM4MDIiLCJhdXRoX3RpbWUiOjE1MzY1MjQ3MDgsInVzZXJfaWQiOiIzeUN6Z1U4VmpGVlg4N3MwRWNoSUdaaEZOcGkxIiwic3ViIjoiM3lDemdVOFZqRlZYODdzMEVjaElHWmhGTnBpMSIsImlhdCI6MTUzNzEzMDg2NywiZXhwIjoxNTM3MTM0NDY3LCJlbWFpbCI6ImJhbmVyamVlLmRlYmFAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMDA0MTkwMDQwNjQyMjQ4MTQ1NjMiXSwiZW1haWwiOlsiYmFuZXJqZWUuZGViYUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.bSA6vigPg45B0uaQ4MgrTOccv5mpzQPbp9lKGpAgNmevu3g5EX9lVGudCgLf-tGS_Pe8mSjfwwcVH60raB6zoYxlGsrBR_MkKjD8GhEgkxSbbut2kqe9t8qqe1GY8ZRmyLKYYbG0Nz-RVo8H70WcR64Qb-nrtQemZmLv25Ae43_j_8C45nR2P7wQQZsa7nE0rTeqyvwteogji2HeiEcxBd0suW8CcAlAkWR9O_jw8nfyKUsKNLEr5TBojsitvAxl1CN2Sb1dk5n52WB2Cc_CyqKyohH1eLHnsjlaY-vWXZP8N_vzEL9Xq2V2yCKnEOwLCsj0CmRj9mb4_Ay1Ax0nLg";
		
		//new DataService().gameList(key, null);
		
		new DataService().checkGameStatus();
		
	}
}
