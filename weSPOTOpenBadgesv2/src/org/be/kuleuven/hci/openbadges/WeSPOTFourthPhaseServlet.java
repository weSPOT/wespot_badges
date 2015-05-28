package org.be.kuleuven.hci.openbadges;

import java.io.IOException;

import javax.servlet.http.*;

import org.be.kuleuven.hci.openbadges.awardingbadges.rules.weSPOTPhasesRules;
import org.be.kuleuven.hci.openbadges.model.AwardedBadges;
import org.be.kuleuven.hci.openbadges.persistanceLayer.OfyService;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadges;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class WeSPOTFourthPhaseServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			JSONObject result = new JSONObject(RestClient.doGet("http://inquiry.wespot.net/services/api/rest/json/?method=site.inquiries&api_key="+weSPOTBadgesConstants.KEYELGG));
			JSONArray inquiries_list = result.getJSONArray("result");
			
			for (int i =0; i<inquiries_list.length(); i++)
				weSPOTPhasesRules.awardPhaseBadges(inquiries_list.getJSONObject(i).getString("inquiryId"),"1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
