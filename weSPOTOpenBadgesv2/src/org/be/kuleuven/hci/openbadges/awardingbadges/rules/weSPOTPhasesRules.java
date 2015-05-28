package org.be.kuleuven.hci.openbadges.awardingbadges.rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;




import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;









import java.util.Scanner;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.badges.WeSPOT;
import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.model.AwardedBadges;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadges;
import org.be.kuleuven.hci.openbadges.utils.OpenBadgeAPI;
//import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class weSPOTPhasesRules {
	
	private static final Logger log = Logger.getLogger(weSPOTPhasesRules.class.getName());
	
	public static void main(String[] args) throws UnsupportedEncodingException, JSONException {
		awardPhaseBadges("43219","1");
	}
	
	
	public static void awardPhaseBadges(String inquiry, String phase) throws UnsupportedEncodingException, JSONException{
		Hashtable<String, String> create = new Hashtable<String, String>();
		Hashtable<String, ArrayList<String>> commentAndRating = new Hashtable<String, ArrayList<String>>();
		Hashtable<String, ArrayList<String>> rating = new Hashtable<String, ArrayList<String>>();
		
		/*String query = "select * from event where  (context like '%course\\\":\\\""+inquiry+"\\\",\\\"phase\\\":\\\""+phase+"%' or context like '%course\\\":\\\""+inquiry+"\\\",\\\"phase\\\":"+phase+"%') order by starttime DESC";
		
		String postQuery = "{ \"query\": \""+query +"\", \"pag\": \""+0+"\"}";
		log.info(query);*/
		String queryresult = RestClient.doGetAuth("http://ariadne.cs.kuleuven.be/wespot-ws/events?context="+inquiry);
		log.info(queryresult);
		JSONArray phase1Events = new JSONArray(queryresult);
		createHashtableCommentAndRating(inquiry, phase1Events, createHashtableCreate(inquiry, phase1Events));
		createHashtableRating(inquiry, phase1Events, createHashtableCreate(inquiry, phase1Events));
				
	}
	
	static Hashtable<String, String> createHashtableCreate(String inquiry, JSONArray phase1Events) throws JSONException {
		Hashtable<String, ArrayList<String>> bronzeMedalActivity = new Hashtable<String, ArrayList<String>>();
		Hashtable<String, String> create = new Hashtable<String, String>();		
		//todo
		for (int i=0;i<phase1Events.length();i++){
			JSONObject event = phase1Events.getJSONObject(i);			
			JSONObject context = new JSONObject(event.getString("context"));
			String username = event.getString("username");
			String object = event.getString("object");
			String verb = event.getString("verb");	
			String event_id = event.getString("_id");
			if ((verb.compareTo("comment")==0 || (verb.compareTo("create"))==0)&&(!create.containsKey(object))){				
				create.put(object, username);
				if (bronzeMedalActivity.containsKey(username)){					
					ArrayList<String> evidences = bronzeMedalActivity.get(username);
					evidences.add(event_id);
					bronzeMedalActivity.put(username, evidences);
				}else{
					ArrayList<String> evidences = new ArrayList<String>();
					evidences.add(event_id);
					bronzeMedalActivity.put(username, evidences);
				}
			}			
		}		
		awardBronzeBadges(bronzeMedalActivity, inquiry);
		return create;
	}
	
	static Hashtable<String, ArrayList<String>> createHashtableRating(String inquiry, JSONArray phase1Events, Hashtable<String, String> create) throws JSONException {
		Hashtable<String, ArrayList<String>> rating = new Hashtable<String, ArrayList<String>>();		
		Hashtable<String, ArrayList<String>> goldMedalActivity = new Hashtable<String, ArrayList<String>>();
		
		for (int i=0;i<phase1Events.length();i++){
			
			JSONObject event = phase1Events.getJSONObject(i);		
			//System.out.println(event.toString());
			JSONObject context = new JSONObject(event.getString("context"));
			String username = event.getString("username");
			String object = event.getString("object");
			if (object.lastIndexOf("-edited")==object.length()-"-edited".length()){
				
				object= object.substring(0, object.lastIndexOf("-edited"));
				System.out.println(object);
			}
			String verb = event.getString("verb");	
			String event_id = event.getString("_id");
			
			if ((verb.compareTo("rated")==0)&&(create.containsKey(object))){
				JSONObject rateJSON = new JSONObject(event.getString("originalrequest"));
				int rate = Integer.parseInt(rateJSON.getString("value"));
				if (username.compareTo(create.get(object))!=0){
					ArrayList<String> interactions;
					if (rating.containsKey(create.get(object)))
						interactions = rating.get(create.get(object));
					else 
						interactions = new ArrayList<String>();
					if (!interactions.contains(username)){
						interactions.add(username);
						rating.put(create.get(object), interactions);
						if (goldMedalActivity.containsKey(create.get(object))){
							ArrayList<String> evidences = goldMedalActivity.get(create.get(object));
							evidences.add(event_id);
							goldMedalActivity.put(create.get(object), evidences);
						}else{
							ArrayList<String> evidences = new ArrayList<String>();
							evidences.add(event_id);
							goldMedalActivity.put(create.get(object), evidences);
						}
					}
				}
			}			
		}		
		awardGoldBadges(goldMedalActivity, inquiry);
		return rating;
	}	
	
	static Hashtable<String, ArrayList<String>> createHashtableCommentAndRating(String inquiry, JSONArray phase1Events, Hashtable<String, String> create) throws JSONException {
		Hashtable<String, ArrayList<String>> commentAndRating = new Hashtable<String, ArrayList<String>>();		
		Hashtable<String, ArrayList<String>> silverMedalActivity = new Hashtable<String, ArrayList<String>>();
		int contador =0;
		for (int i=0;i<phase1Events.length();i++){
			JSONObject event = phase1Events.getJSONObject(i);			
			JSONObject context = new JSONObject(event.getString("context"));
			String username = event.getString("username");
			String object = event.getString("object");
			String verb = event.getString("verb");	
			String event_id = event.getString("_id");
			
			if ((verb.compareTo("comment")==0||verb.compareTo("rated")==0)&&(create.containsKey(object))){
				if (username.compareTo(create.get(object))!=0){

					ArrayList<String> interactions;
					if (commentAndRating.containsKey(create.get(object)))
						interactions = commentAndRating.get(create.get(object));
					else 
						interactions = new ArrayList<String>();
					if (!interactions.contains(username)){
						interactions.add(username);
						commentAndRating.put(create.get(object), interactions);
						if (silverMedalActivity.containsKey(create.get(object))){
							ArrayList<String> evidences = silverMedalActivity.get(create.get(object));
							evidences.add(event_id);
							silverMedalActivity.put(create.get(object), evidences);
						}else{
							ArrayList<String> evidences = new ArrayList<String>();
							evidences.add(event_id);
							silverMedalActivity.put(create.get(object), evidences);
						}
					}
				}
			}			
		}		
		awardSilverBadges(silverMedalActivity, inquiry);
		return commentAndRating;
	}

	

	static void awardGoldBadges(Hashtable<String, ArrayList<String>> goldMedalActivity, String inquiry){
		Enumeration<String> enumeration = goldMedalActivity.keys();
		while (enumeration.hasMoreElements()) {
			String username = enumeration.nextElement();
			ArrayList<String> evidences = goldMedalActivity.get(username);
			if (evidences.size()>weSPOTBadgesConstants.CONTRIBUTIONS_1_LEVEL) awardBadge( inquiry, username ,evidences,  1,  3);		   
		}
	}
	
	static void awardSilverBadges(Hashtable<String, ArrayList<String>> silverMedalActivity, String inquiry){
		Enumeration<String> enumeration = silverMedalActivity.keys();
		while (enumeration.hasMoreElements()) {
			String username = enumeration.nextElement();
			ArrayList<String> evidences = silverMedalActivity.get(username);
			if (evidences.size()>weSPOTBadgesConstants.CONTRIBUTIONS_2_LEVEL) awardBadge( inquiry, username ,evidences,  1,  2);	
		}
	}	
	
	static void awardBronzeBadges(Hashtable<String, ArrayList<String>> bronzeMedalActivity, String inquiry){
		Enumeration<String> enumeration = bronzeMedalActivity.keys();
		while (enumeration.hasMoreElements()) {
			String username = enumeration.nextElement();
		   ArrayList<String> evidences = bronzeMedalActivity.get(username);
		   if (evidences.size()>weSPOTBadgesConstants.CONTRIBUTIONS_3_LEVEL) awardBadge( inquiry, username ,evidences,  1,  1);	
		}
	}
	
	public static void awardBadge(String inquiry, String user, ArrayList<String> evidences, int phase, int level){
		String evidence_result = "";
		for (String evidence:evidences){
			evidence_result += evidence+",";
		}
		evidence_result = evidence_result.substring(0, evidence_result.lastIndexOf(",")-1);
		Badge badge = WeSPOT.weSPOTBadge(inquiry, OpenBadgeAPI.getAuthKey(), phase, level);
		log.info(user+"-"+phase+"-"+badge.getjsonBadge().getValue());
		AwardedBadges awardedBadges = PersistanceLayerAwardedBadges.getAwardedBadges();
		boolean check = PersistanceLayerAwardedBadges.checkIfAwardedBadges(awardedBadges, inquiry, WeSPOT.getFileName(phase, level), user);
		if (!check){
			log.info("awarded badge to: "+ Mail.getEmail(user) );
			String control = OpenBadgeAPI.sendBadge(badge, OpenBadgeAPI.getAuthKey().getKeyApp(), user, evidence_result);
			if (control.compareTo("")!=0){
				PersistanceLayerAwardedBadges.addAwardedBadge(awardedBadges, inquiry, WeSPOT.getFileName(phase, level), user);
				//awardedBadges.addBadgeId(inquiry + WeSPOT.getFileName(phase, level) + user);
				PersistanceLayerAwardedBadges.saveAwardedBadges(awardedBadges);
				if (Mail.getEmail(user)!=null){
					String content;
					try {
						content = new Scanner(new File("template.html")).useDelimiter("\\Z").next();
						content = content.replaceAll("nameofbadge", "7generic.png");
						content = content.replaceAll("Titleofthebadge", new JSONObject(badge.getjsonBadge().getValue()).getString("name"));
						content = content.replaceAll("DescriptionOftheBadge", new JSONObject(badge.getjsonBadge().getValue()).getString("description"));
						content = content.replaceAll("definitionofinquiry", badge.getContext());
						Mail.sendmail(Mail.getSubject(), Mail.getBodyHTML(content), Mail.getEmail(user));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}				
			}			
		}
	}
	

}
