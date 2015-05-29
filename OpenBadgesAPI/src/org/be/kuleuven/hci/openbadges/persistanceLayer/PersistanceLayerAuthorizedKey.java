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



public class PersistanceLayerAuthorizedKey {
	
	private static final Logger log = Logger.getLogger(PersistanceLayerAuthorizedKey.class.getName());

	public static String saveAuthorizedIP(AuthorizedKey authorizedKey){
	    Key<AuthorizedKey> key = OfyService.getOfyService().ofy().save().entity(authorizedKey).now();
	    return String.valueOf(key.getId());
	}
	
	public static AuthorizedKey isAuthorizedIP(String keyApp){
		System.out.println("access db");
    	try{
    		AuthorizedKey authorizedKey = OfyService.getOfyService().ofy().load().type(AuthorizedKey.class).filter("keyApp", keyApp).first().now();
	    	if (authorizedKey!=null)
	    		return authorizedKey;
	    	else
	    		return null;
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
	
	public static void deleteAuthorizedIP(Long id){
		OfyService.getOfyService().ofy().delete().type(AuthorizedKey.class).id(id); 
	}

}
