package org.be.kuleuven.hci.openbadges.mailnotification;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.apphosting.api.ApiProxy.OverQuotaException;

public class Mail {
	
	private static final Logger log = Logger.getLogger(Mail.class.getName());

	
	public static String getEmail(String userId){
		String urlString = "";
		if (userId.contains("_")){
			String[] split = userId.split("_");
			urlString = "http://inquiry.wespot.net/services/api/rest/json/?method=user.email&api_key="+weSPOTBadgesConstants.KEYELGG+"&offset=10&oauthId="+split[1]+"&oauthProvider="+split[0];
		}
		else{
			urlString = "http://inquiry.wespot.net/services/api/rest/json/?method=user.email&api_key="+weSPOTBadgesConstants.KEYELGG+"&offset=10&oauthId="+userId+"&oauthProvider=";
		}
		try {
			JSONObject email = new JSONObject(RestClient.doGet(urlString));
			return email.getString("result");
		} catch (UnsupportedEncodingException e) {
			log.severe(e.toString());
		} catch (JSONException e) {
			log.severe(e.toString());
		} catch (Exception e) {
			log.severe(e.toString());
		}
		return null;
	}
	
	public static String getSubject(){
		String subject = "[weSPOT] New achievement - check it out!";		
		return subject;
	}
	
	public static String getBodyHTML(String file){		
		String body = "";
		body = "<img src=\"http://openbadges-wespot.appspot.com/badges/"+file+"\"/>";
		return body;		
	}

	public static void sendmail(String subject, String message, String destination) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("joseluis.santos.cs@gmail.com", "weSPOT Notifications"));
            InternetAddress[] addresses = new InternetAddress[1];
            addresses[0] = new InternetAddress("info@wespot.net", "weSPOT Notifications");
            msg.setReplyTo(addresses);
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(destination, destination));
           // msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("joseluis.santos.cs@gmail.com", "weSPOT Notifications"));
            msg.setSubject(subject);
            msg.setContent(message, "text/html");
           // msg.setText(message);
            Transport.send(msg);

        } catch (AddressException e) {
			log.severe(e.toString());
        } catch (MessagingException e) {
			log.severe(e.toString());
        } catch (UnsupportedEncodingException e) {
			log.severe(e.toString());
		} catch (OverQuotaException e){
			log.severe(e.toString());
		}
	}

}
