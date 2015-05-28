package org.be.kuleuven.hci.openbadges.test;

import java.io.UnsupportedEncodingException;

import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;

public class testingOpenBadgesAPI {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		//String badge = "{\"criteria\":\"/badges/html5-basic\",\"description\":\"Well done! You scored 75% on the test.\",\"name\":\"Passed phase 6!-\",\"image\":\"/img/badges/wespot_phase6_badge.png\",\"version\":\"1.0\"}";
		String badge = "{\"context\": \"123\", \"version\": \"1.0.0\", \"name\": \"Explorer\", \"image\": \"http://urlofyourbadge.com/testbadgeimage.png\",\"description\": \"Has displayed expertise in hand-made badge creation.\",\"criteria\": \"http://etec.hawaii.edu\"}";
		//JSONObject appkeyjson = new JSONObject(RestClient.doGet("http://openbadgesapi.appspot.com/rest/badges/getauthorizedappkey"));
		//String appkey = appkeyjson.getJSONArray("appkeys").getString(0);
		String appkey = "hhvm2g2fgf9m7tgv3ucl5hdml7";
		String badgeResult = RestClient.doPostAuth("http://openbadgesapi.appspot.com/rest/badges", badge, weSPOTBadgesConstants.KEYOPENBADGES);
		System.out.println(badgeResult);
	}

}
