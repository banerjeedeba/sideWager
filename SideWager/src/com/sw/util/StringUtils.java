package com.sw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.sw.entities.Nickname;


public class StringUtils {

	public static String getShortName(String value){
		String result = value;
		if(value!=null){
			if(value.length()>2){
				value = value.substring(0, 2);
				result = value.toUpperCase();
			} else {
				result = value.toUpperCase();
			}
		}
		
		return result;
	}
	
	public static boolean isEmpty(String value){
		if(value!=null && value.trim().length()>0)
			return false;
		return true;
	}

	public static String getDate(String date, String fromFormat,  String toFormat) throws ParseException{
		String result = date;
		DateFormat df = new SimpleDateFormat(fromFormat);//"MM/dd/yyyy"); 
		Date startDate;
		try {
		    startDate = df.parse(date);
		    DateFormat todf = new SimpleDateFormat(toFormat);
		    result = todf.format(startDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		    throw e;
		}
		return result;
	}
	
	public static String getJsonString(String filePath) throws IOException{
		String json="";
		try {

            File f = new File(filePath);
            System.out.println("file exist: "+f.exists());
            
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";


            ArrayList<Nickname> nicknames = new ArrayList<Nickname>();
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);
                Nickname nickname = new Nickname();
                String[] values = readLine.split(",");
                
                if(!StringUtils.isEmpty(values[0])){
                	System.out.println(values[0]);
                	nickname.setFullName(values[0]);
                }
                if(!StringUtils.isEmpty(values[1])){
                	System.out.println(values[1]);
                	nickname.setCity(values[1]);
                }
                if(!StringUtils.isEmpty(values[2])){
                	nickname.setTeamName(values[2]);
                }
                if(!StringUtils.isEmpty(values[3])){
                	nickname.setNickname(values[3]);
                }
                if(!StringUtils.isEmpty(values[4])){
                	nickname.setSports(values[4]);
                }
                nicknames.add(nickname);
            }
            
            Gson gson = new Gson();
            json = gson.toJson(nicknames);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
		
		return json;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(StringUtils.getDate("2017-07-10T09:00:00", "yyyy-MM-dd'T'HH:mm:ss", "yyyy/MM/dd"));
			System.out.println(StringUtils.getDate("2017-07-10T09:00:00", "yyyy-MM-dd'T'HH:mm:ss", "HH:mma"));
			getJsonString("test.txt");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
