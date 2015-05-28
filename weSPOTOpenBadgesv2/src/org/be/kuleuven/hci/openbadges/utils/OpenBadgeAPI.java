package org.be.kuleuven.hci.openbadges.utils;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.model.AuthorizedKey;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadges;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class OpenBadgeAPI {
	
	private static final Logger log = Logger.getLogger(OpenBadgeAPI.class.getName());
	
	public static AuthorizedKey getAuthKey(){
		AuthorizedKey authKey = new AuthorizedKey();
		authKey.setKeyApp(weSPOTBadgesConstants.KEYOPENBADGES);
		authKey.setName("automaticbadges");
		return authKey;
	}

	public static String sendBadge(Badge badge, String key, String username, String evidences) {
		//System.out.println(badge);
		JSONObject badgeJSON;
		String badgeResult = "";
		try {
			badgeJSON = new JSONObject();
			badgeJSON.put("badge", new JSONObject(badge.getjsonBadge().getValue()));
			badgeJSON.put("context", badge.getContext());
			badgeJSON.put("recipient", username);
			badgeJSON.put("issued_on", Calendar.getInstance().getTime().toString());
			badgeJSON.put("evidence", evidences);
			badgeResult = RestClient.doPostAuth(weSPOTBadgesConstants.URL_OPENBADGESAPI+"/rest/badges/award", badgeJSON.toString(), key);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());
		}
		return badgeResult;
	}
}
