package com.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.tasks.Continuation;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.OnSuccessListener;
import com.google.firebase.tasks.Task;
import com.google.gson.Gson;
import com.sw.entities.Match;
import com.sw.entities.Notification;
import com.sw.entities.NotificationMessage;
import com.sw.entities.Odds;
import com.sw.entities.Tournament;
import com.sw.entities.Users;
import com.sw.util.HttpURLConnectionUtil;

@Path("/notifications")
public class NotificationService {
	private static final Logger log = Logger.getLogger(NotificationService.class.getName());
	
	private static boolean hasBeenInitialized = false;

	@POST
	@Path("/registerTopic")
	@Produces({ MediaType.APPLICATION_JSON })
	public String registerTopic(@QueryParam("topic") String topic,@QueryParam("ckey") String ckey)
	{
		String skey = "AAAA9J64QK8:APA91bFJXEw51e_ncFGhj1exm_ytqhGBPxCwPc0rOdrWL_4Vq9N_NB4GeT6IAaefgr-erXAi9rsORJWg7wfoNdE46o_EzO_Ro51YqiAaH-2ymRRsa0GY367fo9Lfr_4q2oq1PrIWmSSz";
		
		String url="https://iid.googleapis.com/iid/v1/"+ckey+"/rel/topics/"+topic;
		log.info("url:::::::"+url);
		String res = "";
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		try {
			res = con.post(url, skey,null);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("catch - url:::::::"+url);
			return "[{\"error\": \""+e.getMessage()+"\"}]";
		}
		
		return "[{\"success\": \"Topic registered successfully"+res+"\"}]";
	}
	
	@POST
	@Path("/sendTopic")
	@Produces({ MediaType.APPLICATION_JSON })
	public String sendTopic(@QueryParam("topic") String topic)
	{
		String skey = "AAAA9J64QK8:APA91bFJXEw51e_ncFGhj1exm_ytqhGBPxCwPc0rOdrWL_4Vq9N_NB4GeT6IAaefgr-erXAi9rsORJWg7wfoNdE46o_EzO_Ro51YqiAaH-2ymRRsa0GY367fo9Lfr_4q2oq1PrIWmSSz";
		
		String url="https://fcm.googleapis.com/fcm/send";
		log.info("url:::::::"+url);
		String res = "";
		
		Notification notification = new Notification();
		notification.setBody("Background message body2");
		notification.setTitle("Background Message Title2");
		notification.setClick_action("https://www.google.com");
		
		NotificationMessage msg = new NotificationMessage();
		msg.setNotification(notification);
		msg.setTo("/topics/"+topic);
		
		Gson obj = new Gson();
		String param = obj.toJson(msg);
		
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		try {
			res = con.post(url, skey,param);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("catch - url:::::::"+url);
			return "[{\"error\": \""+e.getMessage()+"\"}]";
		}
		
		return "[{\"success\": \"Message sent successfully\"}]";
	}
	static String customTokenValue = "";
	public static String key = "";
	
	@GET
	@Path("/servicedb")
	@Produces({ MediaType.APPLICATION_JSON })
	public String serviceDB() {
		
		String response = "";
		
		boolean result = false;
		try {
			
			//result = NotificationService.updateUser();
			System.out.println("Method execute every day. Current time is :: "+ new Date());
	        log.info("Method execute every day.");
	        
	        String url = "https://sidewager-163802.firebaseio.com/tournament.json?x-http-method-override=PUT&auth="+key;
	        String param = "[   {        \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",        \"HomeTeam\": \"ChicagoTest1\",        \"AwayTeam\": \"Orlando City\",        \"Sport\": 7,        \"MatchTime\": \"2017-06-25T00:30:00\",        \"Odds\": [            {                \"ID\": \"06245ba3-e776-4828-a658-83a89a08cfd1\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"105\",                \"MoneyLineAway\": \"330\",                \"PointSpreadHome\": \"-0.50\",                \"PointSpreadAway\": \"0.50\",                \"PointSpreadHomeLine\": \"105\",                \"PointSpreadAwayLine\": \"-125\",                \"TotalNumber\": \"1.25\",                \"OverLine\": \"101\",                \"UnderLine\": \"-121\",                \"DrawLine\": \"146\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"First Half\"            },            {                \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"-200\",                \"MoneyLineAway\": \"520\",                \"PointSpreadHome\": \"-1.50\",                \"PointSpreadAway\": \"1.50\",                \"PointSpreadHomeLine\": \"115\",                \"PointSpreadAwayLine\": \"-135\",                \"TotalNumber\": \"3.00\",                \"OverLine\": \"100\",                \"UnderLine\": \"-120\",                \"DrawLine\": \"300\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"League\": {            \"Name\": \"Major League Soccer\"        },        \"DisplayRegion\": \"USA\",        \"HomeROT\": \"16535\",        \"AwayROT\": \"16534\"    },    {        \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",        \"HomeTeam\": \"Neiman Gracie\",        \"AwayTeam\": \"Dave Marfone\",        \"Sport\": 11,        \"MatchTime\": \"2017-06-25T00:35:00\",        \"Odds\": [            {                \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"EventID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"MoneyLineHome\": \"-750\",                \"MoneyLineAway\": \"550\",                \"PointSpreadHome\": \"0.0\",                \"PointSpreadAway\": \"0.0\",                \"PointSpreadHomeLine\": \"0\",                \"PointSpreadAwayLine\": \"0\",                \"TotalNumber\": \"1.5\",                \"OverLine\": \"113\",                \"UnderLine\": \"-143\",                \"DrawLine\": \"0\",                \"LastUpdated\": \"2017-06-25T00:17:47\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"HomeROT\": \"24160\",        \"AwayROT\": \"24159\"    }]";
			System.out.println("put param"+param);
			HttpURLConnectionUtil con = new HttpURLConnectionUtil();
			try {
				con.post(url, null,param);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("catch - url:::::::"+url);
				//return "[{\"error\": \""+e.getMessage()+"\"}]";
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "[{\"error\": \""+e.getMessage()+"...."+result+"\"}]";
		}
		System.out.println("response : [{\"success\": \"servicedb successfully"+"...."+result+"\"}]");
		return "[{\"success\": \"servicedb successfully"+"...."+result+"\"}]";
	}
	
	@POST
	@Path("/readdb")
	@Produces({ MediaType.APPLICATION_JSON })
	public String readDB(@QueryParam("ckey") String ckey) {
		
		String response = "";
		key = ckey;
		boolean result = false;
		try {
			/*FileInputStream serviceAccount = new FileInputStream("WEB-INF/sidewager-163802-firebase-adminsdk-9pyjj-adc46948ea.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					  .build();
					//defaultApp = FirebaseApp.initializeApp(options);
			if(!hasBeenInitialized){
				FirebaseApp.initializeApp(options);
				hasBeenInitialized = true;
			}*/
			
			
			//customTokenValue = "";
			/*FirebaseAuth.getInstance().createCustomToken("T3409Qty3IOHAgXrDxGhe59u83W2")
		    .addOnSuccessListener(new OnSuccessListener<String>() {
		        @Override
		        public void onSuccess(String customToken) {
		            // Send token back to client
		        	customTokenValue = customToken;
		        }
		    });*/
			
			/*NotificationService.getAuthId(ckey).addOnCompleteListener(new OnCompleteListener<FirebaseToken>() {
				
				@Override
				public void onComplete(Task<FirebaseToken> arg0) {
					log.info("authid complete"+customTokenValue);
					NotificationService.updateUser();
					
				}
			});
			int count = 0;
			while(count<2){
				Thread.sleep(2000);
				count++;
			}*/
			result = NotificationService.updateUser();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "[{\"error\": \""+e.getMessage()+"...."+result+"\"}]";
		}
		System.out.println("response : [{\"success\": \"DB read successfully"+"...."+result+"\"}]");
		return "[{\"success\": \"DB read successfully"+"...."+result+"\"}]";
	}
	
	public static boolean updateUser(){
		HttpURLConnectionUtil http = new HttpURLConnectionUtil();
		System.out.println("flow: "+customTokenValue+" date: "+new Date());
		String url = "https://sidewager-163802.firebaseio.com/tournament.json?x-http-method-override=PUT&auth="+key;
		//String url = "https://sidewager-163802.firebaseio.com/users/rajib.json?x-http-method-override=PUT&auth="+key;
		try {
			Users user = new Users();
			user.setEmail("rajin@acb.com");
			user.setUsername("rajibuser");
			user.setId(customTokenValue);
			user.setKey(key);
			
			Match matchRef = new Match();
			matchRef.setId("testid"+new GregorianCalendar().getTimeInMillis());
			matchRef.setHomeTeam("testhomeTeam");
			matchRef.setAwayTeam("awayTeam");
			matchRef.setSport(1);
			matchRef.setMatchTime("10:50");
			matchRef.setLeague("league");
			matchRef.setDisplayRegion("displayRegion");
			matchRef.setHomeRot("homerot");
			matchRef.setAwayRot("awayrot");
			matchRef.setHomePitcher("homePitcher");
			matchRef.setAwayPitcher("awayPitcher");
			
			List<Match> matches = new ArrayList<Match>();
			matches.add(matchRef);
			
			
			Tournament tournament = new Tournament(matches);
			Gson obj = new Gson();
			//String param = obj.toJson(tournament);
			String param = "[   {        \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",        \"HomeTeam\": \"ChicagoT\",        \"AwayTeam\": \"Orlando City\",        \"Sport\": 7,        \"MatchTime\": \"2017-06-25T00:30:00\",        \"Odds\": [            {                \"ID\": \"06245ba3-e776-4828-a658-83a89a08cfd1\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"105\",                \"MoneyLineAway\": \"330\",                \"PointSpreadHome\": \"-0.50\",                \"PointSpreadAway\": \"0.50\",                \"PointSpreadHomeLine\": \"105\",                \"PointSpreadAwayLine\": \"-125\",                \"TotalNumber\": \"1.25\",                \"OverLine\": \"101\",                \"UnderLine\": \"-121\",                \"DrawLine\": \"146\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"First Half\"            },            {                \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"-200\",                \"MoneyLineAway\": \"520\",                \"PointSpreadHome\": \"-1.50\",                \"PointSpreadAway\": \"1.50\",                \"PointSpreadHomeLine\": \"115\",                \"PointSpreadAwayLine\": \"-135\",                \"TotalNumber\": \"3.00\",                \"OverLine\": \"100\",                \"UnderLine\": \"-120\",                \"DrawLine\": \"300\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"League\": {            \"Name\": \"Major League Soccer\"        },        \"DisplayRegion\": \"USA\",        \"HomeROT\": \"16535\",        \"AwayROT\": \"16534\"    },    {        \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",        \"HomeTeam\": \"Neiman Gracie\",        \"AwayTeam\": \"Dave Marfone\",        \"Sport\": 11,        \"MatchTime\": \"2017-06-25T00:35:00\",        \"Odds\": [            {                \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"EventID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"MoneyLineHome\": \"-750\",                \"MoneyLineAway\": \"550\",                \"PointSpreadHome\": \"0.0\",                \"PointSpreadAway\": \"0.0\",                \"PointSpreadHomeLine\": \"0\",                \"PointSpreadAwayLine\": \"0\",                \"TotalNumber\": \"1.5\",                \"OverLine\": \"113\",                \"UnderLine\": \"-143\",                \"DrawLine\": \"0\",                \"LastUpdated\": \"2017-06-25T00:17:47\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"HomeROT\": \"24160\",        \"AwayROT\": \"24159\"    }]";
			System.out.println("put param"+param);
			HttpURLConnectionUtil con = new HttpURLConnectionUtil();
			try {
				con.post(url, null,param);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("catch - url:::::::"+url);
				//return "[{\"error\": \""+e.getMessage()+"\"}]";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Task<FirebaseToken> getAuthId(String key){
		Task<FirebaseToken> task =  FirebaseAuth.getInstance().verifyIdToken(key)
		    .addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
		        @Override
		        public void onSuccess(FirebaseToken decodedToken) {
		        	customTokenValue = decodedToken.getUid();
		        	log.info("AuthId success: "+customTokenValue);
		            // ...fire
		        }
		})
		    .addOnFailureListener(new OnFailureListener() {
				
				@Override
				public void onFailure(Exception arg0) {
					// TODO Auto-generated method stub
					log.info("AuthId failure: "+arg0);
				}
			});
		
		return task;
	}
	private static DatabaseReference database;
	@POST
	@Path("/createdb")
	@Produces({ MediaType.APPLICATION_JSON })
	public String createDB() {
		FirebaseApp defaultApp = null;
		try {
			FileInputStream serviceAccount = new FileInputStream("WEB-INF/sidewager-163802-firebase-adminsdk-9pyjj-adc46948ea.json");
			//InputStream stream = this.getClass().getResourceAsStream("SideWager-6b3f1d7d98e3.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					  .setDatabaseUrl("https://sidewager-163802.firebaseio.com").build();
					//defaultApp = FirebaseApp.initializeApp(options);
			if(!hasBeenInitialized){
				System.out.println("firebase initialized");
				FirebaseApp.initializeApp(options);
				hasBeenInitialized = true;
			}else{
				System.out.println("firebase already initialized");
			}
			//System.out.println(defaultApp.getName());  // "[DEFAULT]"
					
		} catch (FileNotFoundException e) {
			 System.out.println("ERROR: FileNotFoundException :invalid service account credentials. See README.");
	         System.out.println(e.getMessage());

		} catch (IOException e) {
			System.out.println("ERROR: IOException : invalid service account credentials. See README.");
            System.out.println(e.getMessage());
		}
		

		

		// Retrieve services by passing the defaultApp variable...
		//FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
		//FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();  

		//FirebaseApp.initializeApp(options);
		
		//FirebaseAuth.getInstance();
		//final FirebaseDatabase database = defaultDatabase;
		database = FirebaseDatabase.getInstance().getReference();
		//DatabaseReference defaultDBRef = defaultDatabase.getReference("users");
		testCreateDB();
		
		return "[{\"success\": \"DB created\"}]";
	}
	
	private void testCreateDB(){
		DatabaseReference usersDBRef =database.child("users");
		usersDBRef.setValue("{\"test\":{ \"username\": \"name\",\"email\": \"email\"}");
		
		
		/*DatabaseReference matchDBRef = database.getReference("match");
		
		Match matchRef = new Match();
		matchRef.setId("testid"+new GregorianCalendar().getTimeInMillis());
		matchRef.setHomeTeam("testhomeTeam");
		matchRef.setAwayTeam("awayTeam");
		matchRef.setSport(1);
		matchRef.setMatchTime("10:50");
		matchRef.setLeague("league");
		matchRef.setDisplayRegion("displayRegion");
		matchRef.setHomeRot("homerot");
		matchRef.setAwayRot("awayrot");
		matchRef.setHomePitcher("homePitcher");
		matchRef.setAwayPitcher("awayPitcher");
		
		Map<String, Match> matches = new HashMap<String, Match>();
		matches.put(matchRef.getId(), matchRef);

		matchDBRef.setValue(matches);
		
		//Insert odds
		DatabaseReference oddsDBRef = database.getReference("odds");
		
		Odds oddsRef = new Odds();
		oddsRef.setId("oddsid"+new GregorianCalendar().getTimeInMillis());
		oddsRef.setEventId(matchRef.getId());
		oddsRef.setMoneyLineHome("moneyLineHome");
		oddsRef.setMoneyLineAway("moneyLineAway");
		oddsRef.setDrawLine("drawLine");
		oddsRef.setPointSpreadHome("pointSpreadHome");
		oddsRef.setPointSpreadAway("pointSpreadAway");
		oddsRef.setPointSpreadHomeLine("pointSpreadHomeLine");
		oddsRef.setPointSpreadAwayLine("pointSpreadAwayLine");
		oddsRef.setTotalNumber("totalNumber");
		oddsRef.setOverLine("overLine");
		oddsRef.setUnderLine("underLine");
		oddsRef.setUnderLine("underLine");
		oddsRef.setLastUpdated("lastUpdated");
		oddsRef.setParticipant("participant");
		
		Map<String, Odds> odds = new HashMap<String, Odds>();
		odds.put(oddsRef.getId(), oddsRef);

		oddsDBRef.setValue(odds);*/
	}
	
	@POST
	@Path("/userlogin")
	@Produces({ MediaType.APPLICATION_JSON })
	public String userLogin(@QueryParam("ckey") String ckey, @QueryParam("extime") String extime) {
		
		String response = "";
		OddsUpdate.key=ckey;
		OddsUpdate.expirationTime = extime!=null?Long.parseLong(extime):0;
		log.info("key updated: "+new Date());
		return "[{\"success\": \"key updated\"}]";
	}
	
	public static void main(String[] args) {
		Notification notification = new Notification();
		notification.setBody("Background message body");
		notification.setTitle("Background Message Title");
		notification.setClick_action("https://www.google.com");
		
		NotificationMessage msg = new NotificationMessage();
		msg.setNotification(notification);
		msg.setTo("/topics/"+"abc");
		
		Gson obj = new Gson();
		System.out.println(obj.toJson(msg));
	}
}
