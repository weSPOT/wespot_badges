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
import org.be.kuleuven.hci.openbadges.model.AwardedBadges;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;
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

public class PersistanceLayerAwardedBadges {
	
	private static final Logger log = Logger.getLogger(PersistanceLayerAwardedBadges.class.getName());
	
	public static boolean checkIfAwardedBadges(AwardedBadges awardedBadges, String inquiryId, String badgeFile, String username){
		if (awardedBadges.checkIfExists(inquiryId+badgeFile+username))
			return true;
		else
			return false;
	}
	
	public static void addAwardedBadge(AwardedBadges awardedBadges, String inquiryId, String badgeFile, String username){
		awardedBadges.addBadgeId(inquiryId + badgeFile + username);
	}

	public static String saveAwardedBadges(AwardedBadges awardedBadges){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.put(weSPOTBadgesConstants.BADGESINCACHE, awardedBadges);
	    Key<AwardedBadges> key = OfyService.getOfyService().ofy().save().entity(awardedBadges).now();
	    return String.valueOf(key.getId());
	}
	
	public static AwardedBadges getAwardedBadges(){
		//log.info("access awarded badges");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get(weSPOTBadgesConstants.BADGESINCACHE)!=null){
	    	//log.info("access awarded badges in cache");
	    	return (AwardedBadges) syncCache.get(weSPOTBadgesConstants.BADGESINCACHE);	    	
	    }else{
	    	//log.info("access awarded badges in datastore");
	    	AwardedBadges awardedBadges = OfyService.getOfyService().ofy().load().type(AwardedBadges.class).first().now();
	    	if (awardedBadges==null) awardedBadges = new AwardedBadges();
		    return awardedBadges;   	
	    }
	}


}
