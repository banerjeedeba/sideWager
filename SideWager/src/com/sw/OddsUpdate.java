package com.sw;

import java.io.FileInputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.OnSuccessListener;
import com.google.firebase.tasks.Task;
import com.sw.entities.Match;
import com.sw.entities.Odds;
import com.sw.util.HttpURLConnectionUtil;


@Path("/oddsupdate")
public class OddsUpdate {

	private static final Logger log = Logger.getLogger(OddsUpdate.class.getName());
	
	public static String key = "";
	public static Long expirationTime = 0l;
	
	private final String ODDS_KEY = "4a3cacf0-5879-11e7-b6b0-0afc106a1b07";
	//private static boolean hasBeenInitialized = false;
	
	//private static DatabaseReference database;
	
	@GET
	@Path("/updatedb")
	@Produces({MediaType.APPLICATION_JSON})
	public String updateDB(@QueryParam("email") String email){
		
		System.out.println("update db email: "+email);
		try {
			
			String oddsJson = getOdds();
			
			if(expirationTime!=0 && expirationTime>new Date().getTime()){
				System.out.println("Method execute every day. Current time is :: "+ new Date());
		        log.info("Method execute every day.");
		        
		        long date = new Date().getTime();
		        String url = "https://sidewager-163802.firebaseio.com/tournament.json?x-http-method-override=PUT&auth="+key;
		        //String param = "[   {        \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",        \"HomeTeam\": \"ChicagoT"+date+"\",        \"AwayTeam\": \"Orlando City\",        \"Sport\": 7,        \"MatchTime\": \"2017-06-25T00:30:00\",        \"Odds\": [            {                \"ID\": \"06245ba3-e776-4828-a658-83a89a08cfd1\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"105\",                \"MoneyLineAway\": \"330\",                \"PointSpreadHome\": \"-0.50\",                \"PointSpreadAway\": \"0.50\",                \"PointSpreadHomeLine\": \"105\",                \"PointSpreadAwayLine\": \"-125\",                \"TotalNumber\": \"1.25\",                \"OverLine\": \"101\",                \"UnderLine\": \"-121\",                \"DrawLine\": \"146\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"First Half\"            },            {                \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"-200\",                \"MoneyLineAway\": \"520\",                \"PointSpreadHome\": \"-1.50\",                \"PointSpreadAway\": \"1.50\",                \"PointSpreadHomeLine\": \"115\",                \"PointSpreadAwayLine\": \"-135\",                \"TotalNumber\": \"3.00\",                \"OverLine\": \"100\",                \"UnderLine\": \"-120\",                \"DrawLine\": \"300\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"League\": {            \"Name\": \"Major League Soccer\"        },        \"DisplayRegion\": \"USA\",        \"HomeROT\": \"16535\",        \"AwayROT\": \"16534\"    },    {        \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",        \"HomeTeam\": \"Neiman Gracie\",        \"AwayTeam\": \"Dave Marfone\",        \"Sport\": 11,        \"MatchTime\": \"2017-06-25T00:35:00\",        \"Odds\": [            {                \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"EventID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"MoneyLineHome\": \"-750\",                \"MoneyLineAway\": \"550\",                \"PointSpreadHome\": \"0.0\",                \"PointSpreadAway\": \"0.0\",                \"PointSpreadHomeLine\": \"0\",                \"PointSpreadAwayLine\": \"0\",                \"TotalNumber\": \"1.5\",                \"OverLine\": \"113\",                \"UnderLine\": \"-143\",                \"DrawLine\": \"0\",                \"LastUpdated\": \"2017-06-25T00:17:47\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"HomeROT\": \"24160\",        \"AwayROT\": \"24159\"    }]";
				//System.out.println("put param"+param);
				HttpURLConnectionUtil con = new HttpURLConnectionUtil();
				try {
					con.post(url, null,oddsJson);
				} catch (Exception e) {
					e.printStackTrace();
					log.info("catch - url:::::::"+url);
					log.log(Level.SEVERE, e.getMessage());
					//return "[{\"error\": \""+e.getMessage()+"\"}]";
				}
			} else{
				new Exception("Token Expired");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "[{\"error\": \"error occured while odds update in DB\"}]"+e.getMessage();
		}
		return "[{\"success\": \"odds update in DB\"}]";		
					
			
	}
	
	private String getOdds() throws Exception
	{
		String oddsUrl = "https://jsonodds.com/api/odds";
		Map<String, String> headerParams = new HashMap<String, String>();
		headerParams.put("JsonOdds-API-Key", ODDS_KEY );
		
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		try {
			return con.sendGet(oddsUrl, headerParams,null);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("catch - url:::::::"+oddsUrl);
			log.log(Level.SEVERE, "Exception while fetching odds : "+ e.getMessage());
			throw new Exception(e);
		}
	}
	
}

//------------------------------------------DB start ---------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------

/*FileInputStream serviceAccount =
new FileInputStream("WEB-INF/sidewager-163802-firebase-adminsdk-9pyjj-adc46948ea.json");

FirebaseOptions options = new FirebaseOptions.Builder()
.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
.setDatabaseUrl("https://sidewager-163802.firebaseio.com")
.build();

if(!hasBeenInitialized){
	FirebaseApp.initializeApp(options);
	hasBeenInitialized = true;
}

// Shared Database reference
System.out.println("before dbref");
FirebaseDatabase db = FirebaseDatabase.getInstance("https://sidewager-163802.firebaseio.com");
System.out.println("after dbref");
database = db.getReference("tournament");
System.out.println("before post");
final DatabaseReference postRef = database;
System.out.println("after post");
postRef.runTransaction(new Transaction.Handler() {
    public Transaction.Result doTransaction(MutableData mutableData) {
    	//Odds odds = mutableData.getValue(Odds.class);
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

		//matchDBRef.setValue(matches);
		
		//Insert odds
		//DatabaseReference oddsDBRef = database.getReference("odds");
		
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
        if (odds != null) {
            
            mutableData.setValue(odds);
            return Transaction.success(mutableData);
        } else {
            return Transaction.success(mutableData);
        }
    }

    public void onComplete(DatabaseError databaseError, boolean complete, DataSnapshot dataSnapshot) {
        System.out.println("updateOdds:onComplete:" + complete);
    }
});

int count = 0;
while(true){
	if(count<2){
		Thread.sleep(2000);
		//if(!task.isComplete()){
			count++;
		//}else{
		//	System.out.println("task complete");
		//	break;
		//}
	} else {
		break;
	}
}*/

//----------------------------DB END---------------------------------------------------
//-------------------------------------------------------------------------------------


//----------------------------Admin User Start--------------------------------------------
//----------------------------------------------------------------------------------------

/*FileInputStream serviceAccount =
new FileInputStream("WEB-INF/sidewager-163802-firebase-adminsdk-9pyjj-adc46948ea.json");

FirebaseOptions options = new FirebaseOptions.Builder()
.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
.setDatabaseUrl("https://sidewager-163802.firebaseio.com")
.build();

if(!hasBeenInitialized){
	FirebaseApp.initializeApp(options);
	hasBeenInitialized = true;
}


Task<UserRecord> task = FirebaseAuth.getInstance().getUserByEmail(email)
.addOnSuccessListener( new OnSuccessListener<UserRecord>() {

	@Override
	public void onSuccess(UserRecord userRecord) {
		String key = userRecord.getProviderId();
		System.out.println("Successfully fetched user data: " + userRecord.getEmail()+ " id: "+key);
		
		
		
		//result = NotificationService.updateUser();
		System.out.println("Method execute every day. Current time is :: "+ new Date());
        log.info("Method execute every day.");
        log.info("Method execute every day.");
        
        long date = new Date().getTime();
        String url = "https://sidewager-163802.firebaseio.com/tournament.json?x-http-method-override=PUT&auth="+key;
        String param = "[   {        \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",        \"HomeTeam\": \"ChicagoT"+date+"\",        \"AwayTeam\": \"Orlando City\",        \"Sport\": 7,        \"MatchTime\": \"2017-06-25T00:30:00\",        \"Odds\": [            {                \"ID\": \"06245ba3-e776-4828-a658-83a89a08cfd1\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"105\",                \"MoneyLineAway\": \"330\",                \"PointSpreadHome\": \"-0.50\",                \"PointSpreadAway\": \"0.50\",                \"PointSpreadHomeLine\": \"105\",                \"PointSpreadAwayLine\": \"-125\",                \"TotalNumber\": \"1.25\",                \"OverLine\": \"101\",                \"UnderLine\": \"-121\",                \"DrawLine\": \"146\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"First Half\"            },            {                \"ID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"EventID\": \"5872a210-9129-4287-9700-4c3617297f8c\",                \"MoneyLineHome\": \"-200\",                \"MoneyLineAway\": \"520\",                \"PointSpreadHome\": \"-1.50\",                \"PointSpreadAway\": \"1.50\",                \"PointSpreadHomeLine\": \"115\",                \"PointSpreadAwayLine\": \"-135\",                \"TotalNumber\": \"3.00\",                \"OverLine\": \"100\",                \"UnderLine\": \"-120\",                \"DrawLine\": \"300\",                \"LastUpdated\": \"2017-06-25T00:17:41\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"League\": {            \"Name\": \"Major League Soccer\"        },        \"DisplayRegion\": \"USA\",        \"HomeROT\": \"16535\",        \"AwayROT\": \"16534\"    },    {        \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",        \"HomeTeam\": \"Neiman Gracie\",        \"AwayTeam\": \"Dave Marfone\",        \"Sport\": 11,        \"MatchTime\": \"2017-06-25T00:35:00\",        \"Odds\": [            {                \"ID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"EventID\": \"7dfa19de-5e47-41d3-a8cc-2ff9453db731\",                \"MoneyLineHome\": \"-750\",                \"MoneyLineAway\": \"550\",                \"PointSpreadHome\": \"0.0\",                \"PointSpreadAway\": \"0.0\",                \"PointSpreadHomeLine\": \"0\",                \"PointSpreadAwayLine\": \"0\",                \"TotalNumber\": \"1.5\",                \"OverLine\": \"113\",                \"UnderLine\": \"-143\",                \"DrawLine\": \"0\",                \"LastUpdated\": \"2017-06-25T00:17:47\",                \"SiteID\": 3,                \"OddType\": \"Game\"            }        ],        \"Details\": null,        \"HomeROT\": \"24160\",        \"AwayROT\": \"24159\"    }]";
		System.out.println("put param"+param);
		HttpURLConnectionUtil con = new HttpURLConnectionUtil();
		try {
			con.post(url, null,param);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("catch - url:::::::"+url);
			log.log(Level.SEVERE, e.getMessage());
			//return "[{\"error\": \""+e.getMessage()+"\"}]";
		}
					
	
		
	}

	
}).addOnFailureListener(new OnFailureListener() {
	
	@Override
	public void onFailure(Exception e) {
		System.out.println("Error fetching user data: " + e.getMessage());
	}
});
		
System.out.println("task: "+task.getException());
int count = 0;
while(true){
	if(count<2){
		Thread.sleep(2000);
		if(!task.isComplete()){
			count++;
		}else{
			System.out.println("task complete");
			break;
		}
	} else {
		break;
	}
}
System.out.println("task exception: "+task.getException() + "success :"+task.isSuccessful());*/

//----------------------------Admin User End---------------------------------------------------------
//---------------------------------------------------------------------------------------------------

