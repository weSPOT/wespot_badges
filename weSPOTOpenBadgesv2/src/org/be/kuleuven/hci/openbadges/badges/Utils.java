package org.be.kuleuven.hci.openbadges.badges;

public class Utils {	
	public static String openBadgeString(String name, String badgeName, String description, String criteria){
		return "{\"version\": \"1.0.0\", \"name\": \""+name+"\", \"image\": \"http://openbadges-wespot.appspot.com/badges/"+badgeName+"\", \"description\": \""+description+"\", \"criteria\": \""+criteria+"\"}";	
	}	
}
