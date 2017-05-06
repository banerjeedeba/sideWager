package com.sw;

import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sw.entities.Notification;
import com.sw.entities.NotificationMessage;
import com.sw.util.HttpURLConnectionUtil;

@Path("/notifications")
public class NotificationService {
	private static final Logger log = Logger.getLogger(NotificationService.class.getName());

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
