package org.be.kuleuven.hci.openbadges.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class AwardedBadges implements Serializable{

	@Id Long id;
	@Index Date timestamp;
	@Index ArrayList<String> badgesIds;
	
	public AwardedBadges(){
		badgesIds = new ArrayList<String>();
	}
	
	public void addBadgeId(String badgeId){
		timestamp = Calendar.getInstance().getTime();
		badgesIds.add(badgeId);
	}
	
	public ArrayList<String> getBadgesId(){
		return badgesIds;
	}
	
	public boolean checkIfExists(String badgeId){
		if (badgesIds.contains(badgeId)) return true;
		else return false;
	}
	
	public Date getLastDateAwarded(){
		return timestamp;
	}
	
}
