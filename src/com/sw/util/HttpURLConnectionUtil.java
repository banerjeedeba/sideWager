package com.sw.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

public class HttpURLConnectionUtil {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		HttpURLConnectionUtil http = new HttpURLConnectionUtil();

		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet("http://www.google.com/search?q=mkyong");

		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost("https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/","");

	}

	// HTTP GET request
	public void sendGet(String url) throws Exception {


		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	// HTTP POST request
	public String sendPost(String url, String urlParameters) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");


		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		if(urlParameters!=null && urlParameters.length()>0){
			wr.writeBytes(urlParameters);
		}
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		if(urlParameters!=null && urlParameters.length()>0){
			System.out.println("Post parameters : " + urlParameters);
		}
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();
	}
	
	public String post(String urlString,String apiKey,String param){

	    try{

	        // 1. URL
	        URL url = new URL(urlString);

	        // 2. Open connection
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        // 3. Specify POST method
	        conn.setRequestMethod("POST");

	        // 4. Set the headers
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Authorization", "key=" + apiKey);

	        conn.setDoOutput(true);

	        // 5. Add JSON data into POST request body

	        // 5.1 Convert object to JSON
	        Gson gson = new Gson();
	        //Type type = new TypeToken<Data>() {}.getType();

	        //String json = gson.toJson(data, type);

	        //System.out.println(json);
	        // The printed string is
	        // {"registration_ids":["APA91..."]}
	        // with the correct registration id of my device.


	        // 5.2 Get connection output stream
	        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());


	        // 5.3 Copy Content "JSON" into
	        //wr.writeUTF(param.getBytes("UTF-8"));
	        if(param!=null){
	        	wr.write(param.getBytes("UTF-8"));
	        }

	        // 5.4 Send the request
	        wr.flush();

	        // 5.5 close
	        wr.close();

	        // 6. Get the response
	        int responseCode = conn.getResponseCode();
	        System.out.println("\nSending 'POST' request to URL : " + url);
	        System.out.println("Response Code : " + responseCode);

	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        // 7. Print result
	        System.out.println(response.toString());
	        return response.toString();

	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	        return e.getMessage();
	    } catch (IOException e) {
	    	return e.getMessage();
	    }
	}
}
