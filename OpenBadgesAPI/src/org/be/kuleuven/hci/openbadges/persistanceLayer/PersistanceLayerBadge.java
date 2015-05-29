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



public class PersistanceLayerBadge {
	
	private static final Logger log = Logger.getLogger(PersistanceLayerBadge.class.getName());

	public static String saveBadge(Badge badge){
	    Key<Badge> key = OfyService.getOfyService().ofy().save().entity(badge).now();
	    return String.valueOf(key.getId());
	}
	
	public static Badge getbadgeById(String id){
		Badge badge = OfyService.getOfyService().ofy().load().type(Badge.class).filter("id", Long.parseLong(id)).first().now();
		//System.out.println("Badge Id:\n\n\n"+badge.getContext());
		return badge;
	}
	
	public static String getbadge(String id, String context, String keyApp, String resumptiontoken){
		System.out.println("access db");
		JSONObject result = new JSONObject();
		JSONArray badgesJSON = new JSONArray();
    	try{
    		Query<Badge> badges = null;
    		if (context.compareTo("")==0)    		
    			badges = OfyService.getOfyService().ofy().load().type(Badge.class).filter("id", Long.parseLong(id)).filter("keyApp", keyApp);
    		else if (id.compareTo("")==0)
    			badges = OfyService.getOfyService().ofy().load().type(Badge.class).filter("context", context).filter("keyApp", keyApp);	
    		if (resumptiontoken.compareTo("")!=0)
    			badges = badges.startAt(Cursor.fromWebSafeString(resumptiontoken));
    		boolean notended=false;
    		QueryResultIterator<Badge> iterator = badges.iterator();
    		while (iterator.hasNext()) {
    	        Badge badge = iterator.next();
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
	
	public static String getbadgeByContext(String context, String resumptiontoken){
		System.out.println("access db");
		JSONArray badgesJSON = new JSONArray();
    	try{
    		Query<Badge> badges = null;
   			badges = OfyService.getOfyService().ofy().load().type(Badge.class).filter("context", context);	
   			System.out.println("Comparison="+resumptiontoken.compareTo(""));
    		if (resumptiontoken.compareTo("")!=0)
    			badges = badges.startAt(Cursor.fromWebSafeString(resumptiontoken));
    		boolean notended=false;
    		QueryResultIterator<Badge> iterator = badges.iterator();
    		while (iterator.hasNext()) {
    	        Badge badge = iterator.next();
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
	
}
