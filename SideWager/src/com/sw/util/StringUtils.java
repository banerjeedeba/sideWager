package com.sw.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
	
	public static void main(String[] args) {
		try {
			System.out.println(StringUtils.getDate("2017-07-10T09:00:00", "yyyy-MM-dd'T'HH:mm:ss", "yyyy/MM/dd"));
			System.out.println(StringUtils.getDate("2017-07-10T09:00:00", "yyyy-MM-dd'T'HH:mm:ss", "HH:mma"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
