package org.be.kuleuven.hci.openbadges.utils;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class JSONExamples {

	public static String createBadgeExample(){
		JSONObject badgeExample = new JSONObject();
		try {
			badgeExample.put("context", "123");
			badgeExample.put("version", "1.0.0");
			badgeExample.put("name", "Explorer");
			badgeExample.put("image", "http://urlofyourbadge.com/testbadgeimage.png");
			badgeExample.put("description", "Has displayed expertise in hand-made badge creation.");
			badgeExample.put("criteria", "http://etec.hawaii.edu");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return badgeExample.toString();
	}
	
	public static String awardBadgeExample(){
		JSONObject badgeExample = new JSONObject();
		try {
			badgeExample.put("badgeId", "4001");
			badgeExample.put("recipient", "learner.name@gmail.com");
			badgeExample.put("evidence", "http://google.com");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return badgeExample.toString();
	}
	
	
}
