/*******************************************************************************
 * Copyright (c) 2014 KU Leuven
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 *     Jose Luis Santos
 *******************************************************************************/
package org.be.kuleuven.hci.openbadges.persistanceLayer;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.model.AuthorizedKey;
import org.be.kuleuven.hci.openbadges.model.AwardedBadge;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.mortbay.log.Log;

import sun.util.logging.resources.logging;


import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;



public class PersistanceLayerAwardedBadge {
	
	private static final Logger log = Logger.getLogger(PersistanceLayerAwardedBadge.class.getName());

	public static String getAwardedBadge(String id, String username, Date starttime, String context, String keyApp, String resumptiontoken){
		System.out.println("access db");
		JSONObject result = new JSONObject();
		JSONArray badgesJSON = new JSONArray();
    	try{
    		Query<AwardedBadge> badges = null;
    		if (id.compareTo("")!=0)    		
    			badges = OfyService.getOfyService().ofy().load().type(AwardedBadge.class).filter("id", Long.parseLong(id)).filter("keyApp", keyApp);
    		else {
    			log.warning("data: username: "+username +" date:"+starttime.toString()+" key: "+keyApp);
    			badges = OfyService.getOfyService().ofy().load().type(AwardedBadge.class).filter("username", username).filter("starttime >", starttime);	        		
    		}
    		log.warning("number of badges back:"+badges.count());
    		if (resumptiontoken.compareTo("")==0)
    			badges = badges.startAt(Cursor.fromWebSafeString(resumptiontoken));
    		boolean notended=false;
    		QueryResultIterator<AwardedBadge> iterator = badges.iterator();
    		while (iterator.hasNext()) {
    			AwardedBadge badge = iterator.next();
    	        badgesJSON.put(badge.getJSON());
    	        notended = true;
    	    }
    		if (!iterator.hasNext()) notended = false;
	    	result.put("badges", badgesJSON);
	    	if (notended) {
	    		Cursor cursor = iterator.getCursor();
	    		result.put("resumptiontoken", cursor.toWebSafeString());
	    	}	    		
    		return result.toString();
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
	
	public static String getAwardedBadgeByBadgeId(String badgeId, String starttime, String resumptiontoken){
		System.out.println("access db");
		JSONArray badgesJSON = new JSONArray();
    	try{
    		Query<AwardedBadge> badges = null;
   			badges = OfyService.getOfyService().ofy().load().type(AwardedBadge.class).filter("badgeId", badgeId).filter("starttime >", Long.parseLong(starttime));	        		
    		if (resumptiontoken.compareTo("")==0)
    			badges = badges.startAt(Cursor.fromWebSafeString(resumptiontoken));
    		boolean notended=false;
    		QueryResultIterator<AwardedBadge> iterator = badges.iterator();
    		while (iterator.hasNext()) {
    			AwardedBadge badge = iterator.next();
    	        badgesJSON.put(badge.getJSON());
    	        notended = true;
    	    }
    		if (!iterator.hasNext()) notended = false;
	    	if (notended) {
	    		Cursor cursor = iterator.getCursor();
	    		badgesJSON.put((new JSONObject()).put("resumptiontoken", cursor.toWebSafeString()));
	    	}	    		
    		return badgesJSON.toString();
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
	
	public static String getAwardedBadgeByContext(String context, String starttime, String resumptiontoken){
		System.out.println("access db");
		JSONArray badgesJSON = new JSONArray();
    	try{
    		Query<AwardedBadge> badges = null;
   			badges = OfyService.getOfyService().ofy().load().type(AwardedBadge.class).filter("context", context).filter("starttime >", Long.parseLong(starttime));	        		
    		if (resumptiontoken.compareTo("")==0)
    			badges = badges.startAt(Cursor.fromWebSafeString(resumptiontoken));
    		boolean notended=false;
    		QueryResultIterator<AwardedBadge> iterator = badges.iterator();
    		while (iterator.hasNext()) {
    			AwardedBadge badge = iterator.next();
    	        badgesJSON.put(badge.getJSON());
    	        notended = true;
    	    }
    		if (!iterator.hasNext()) notended = false;
	    	if (notended) {
	    		Cursor cursor = iterator.getCursor();
	    		badgesJSON.put((new JSONObject()).put("resumptiontoken", cursor.toWebSafeString()));
	    	}	    		
    		return badgesJSON.toString();
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
	
	public static String getAwardedBadgeByUsername(String username, String starttime, String resumptiontoken){
		System.out.println("access db");
		JSONArray badgesJSON = new JSONArray();
    	try{
    		Query<AwardedBadge> badges = null;
   			badges = OfyService.getOfyService().ofy().load().type(AwardedBadge.class).filter("username", username).filter("starttime >", Long.parseLong(starttime));	        		
    		if (resumptiontoken.compareTo("")==0)
    			badges = badges.startAt(Cursor.fromWebSafeString(resumptiontoken));
    		boolean notended=false;
    		QueryResultIterator<AwardedBadge> iterator = badges.iterator();
    		while (iterator.hasNext()) {
    			AwardedBadge badge = iterator.next();
    	        badgesJSON.put(badge.getJSON());
    	        notended = true;
    	    }
    		if (!iterator.hasNext()) notended = false;
	    	if (notended) {
	    		Cursor cursor = iterator.getCursor();
	    		badgesJSON.put((new JSONObject()).put("resumptiontoken", cursor.toWebSafeString()));
	    	}	    		
    		return badgesJSON.toString();
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
	
	
	
	public static String saveAwardedBadge(AwardedBadge badge){
	    Key<AwardedBadge> key = OfyService.getOfyService().ofy().save().entity(badge).now();
	    return String.valueOf(key.getId());
	}
	
	public static String awardedBadgeId(Long id){
		System.out.println("access db");
    	try{
    		AwardedBadge badge = OfyService.getOfyService().ofy().load().type(AwardedBadge.class).filter("id", id).first().now();
	    	return badge.getJSON().toString();
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
	
	public static void deleteAwardedBadge(Long id){
		OfyService.getOfyService().ofy().delete().type(AwardedBadge.class).id(id); 
	}

	public static AuthorizedKey getbadgeById(String badgeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
