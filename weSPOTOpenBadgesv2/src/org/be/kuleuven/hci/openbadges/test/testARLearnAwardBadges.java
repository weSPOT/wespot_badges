package org.be.kuleuven.hci.openbadges.test;

import java.io.UnsupportedEncodingException;




import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;



//import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.utils.RestClient;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class testARLearnAwardBadges {

	public static void main(String[] args) throws UnsupportedEncodingException, JSONException {
		// TODO Auto-generated method stub
		awardARLearndBadges();
		//System.out.println(queryresult);
	}
	
	static void awardARLearndBadges() throws UnsupportedEncodingException, JSONException{
		String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where verb='response' and originalrequest like '%arlearn%' order by starttime DESC\", \"pag\": \""+0+"\"}");
		JSONArray arlearn_events = new JSONArray(queryresult);
		Hashtable<String, Hashtable<String,ArrayList<String>>> structured_events = new Hashtable<String, Hashtable<String,ArrayList<String>>>();
		for (int i=0;i<arlearn_events.length();i++){
			JSONObject event = arlearn_events.getJSONObject(i);
			JSONObject context = new JSONObject(event.getString("context"));
			structured_events = addEventToInquiry(structured_events, context.getString("course"), event.getString("username"), event.getString("event_id"));
			
		}
		analyseInquiries(structured_events);
	}
	public static Hashtable<String, Hashtable<String,ArrayList<String>>> addEventToInquiry(Hashtable<String, Hashtable<String,ArrayList<String>>> hash, String inquiryId, String username, String event_id){
		if (!hash.containsKey(inquiryId)) hash.put(inquiryId, new Hashtable<String,ArrayList<String>>());
		else{
			Hashtable<String,ArrayList<String>> inquiry = hash.get(inquiryId);
			inquiry = addEventToUser(inquiry, username, event_id);
		}
		return hash;
	}
	
	public static Hashtable<String,ArrayList<String>> addEventToUser(Hashtable<String,ArrayList<String>> hash, String username, String event_id){
		if (!hash.containsKey(username)){
			ArrayList<String> evidences = new ArrayList<String>();
			evidences.add(event_id);
			hash.put(username, evidences);
		}
		else{
			ArrayList<String> evidences = hash.get(username);
			evidences.add(event_id);
			hash.put(username, evidences);
		}
		return hash;
	}
	
	public static void analyseInquiries(Hashtable<String, Hashtable<String,ArrayList<String>>> hash){
		Enumeration<String> inquiries = hash.keys();
		while (inquiries.hasMoreElements()){
			String inquiry = inquiries.nextElement();
			awardBadges(hash.get(inquiry), inquiry);
		}
	}
	
	public static void awardBadges(Hashtable<String,ArrayList<String>> hash, String inquiry){
		Enumeration<String> users = hash.keys();
		while (users.hasMoreElements()){
			String user = users.nextElement();
			ArrayList<String> evidences = hash.get(user);
			if (evidences.size()>=5) awardBronzeBadge(inquiry, user, evidences);
			if (evidences.size()>=10) awardSilverBadge(inquiry, user, evidences);
			if (evidences.size()>=15) awardGoldBadge(inquiry, user, evidences);			
		}
	}

	public static void awardGoldBadge(String inquiry, String user, ArrayList<String> evidences){
		//check if badge already exist
		String evidence_result = "1. Award Gold Badge in Inquiry "+inquiry+" to the student "+user+" evidences: ";
		for (String evidence:evidences){
			evidence_result += evidence+",";
		}
		System.out.println(evidence_result);
	}
	
	public static void awardSilverBadge(String inquiry, String user, ArrayList<String> evidences){
		//check if badge already exist
		String evidence_result = "2. Award Silver Badge in Inquiry "+inquiry+" to the student "+user+" evidences: ";
		for (String evidence:evidences){
			evidence_result += evidence+",";
		}
		System.out.println(evidence_result);
	}

	public static void awardBronzeBadge(String inquiry, String user, ArrayList<String> evidences){
		//check if badge already exist
		String evidence_result = "3. Award Bronze Badge in Inquiry "+inquiry+" to the student "+user+" evidences: ";
		for (String evidence:evidences){
			evidence_result += evidence+",";
		}
		System.out.println(evidence_result);
	}
}
