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
package org.be.kuleuven.hci.openbadges.services;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.be.kuleuven.hci.openbadges.model.AuthorizedKey;
import org.be.kuleuven.hci.openbadges.model.AwardedBadge;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.model.Issuer;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAuthorizedKey;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerBadge;
import org.be.kuleuven.hci.openbadges.utils.JSONExamples;
import org.be.kuleuven.hci.openbadges.utils.KeyGenerator;
import org.be.kuleuven.hci.openbadges.utils.Exceptions.UnauthorizedException;



import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/badges")


public class API {
	private static final Logger log = Logger.getLogger(API.class.getName());
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//The JSON should contain an Open Badge +context field
	// Ex:
	//{ "version": "1.0.0", "name": "Explorer", "image": "http://urlofyourbadge.com/testbadgeimage.png", "description": "Has displayed expertise in hand-made badge creation.", "criteria": "http://etec.hawaii.edu"}
	public String createBadge(@HeaderParam("Authorization") String authorization, String json) {	
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		JSONObject result = new JSONObject();		
		Badge badge = new Badge();
		try {			
			JSONObject badgejson = new JSONObject(json);
			if (badgejson.has("context")&&badgejson.has("version")&&badgejson.has("name")&&badgejson.has("image")&&badgejson.has("description")&&badgejson.has("criteria")){
				badgejson.put("issuer", Issuer.getIssuer());
				badge.setNameApp(authorizedKey.getName());
				badge.setContext(badgejson.getString("context"));
				badgejson.remove("context");
				badge.setjsonBadge(new Text(badgejson.toString()));
				String id = PersistanceLayerBadge.saveBadge(badge);
				result.put("id", id);
				return result.toString();
			}else 
				throw new JSONException("Incorrect JSON. Example: "+JSONExamples.createBadgeExample());
			} catch (JSONException e) {
				return result.toString();
		}
	} 
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/appkey/{name}")
	public String createkey(@PathParam("name") String name) {	
		KeyGenerator key = new KeyGenerator();
		String k = key.nextKeyId();
		AuthorizedKey auth = new AuthorizedKey();
		auth.setKeyApp(k);
		auth.setName(name);
		PersistanceLayerAuthorizedKey.saveAuthorizedIP(auth);
		return k;
	} 
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/badgeId/{badgeId}/award")
	public String awardBadge(@HeaderParam("Authorization") String authorization, @PathParam("badgeId") String badgeId, String json) {	
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		JSONObject result = new JSONObject();
		JSONObject data;
		try {
			data = new JSONObject(json);
			if (data.has("recipient")&&data.has("evidence")){
				Badge badge = PersistanceLayerBadge.getbadgeById(badgeId);
				System.out.println(badge.getJSON().toString());
				AwardedBadge awardedBadge = new AwardedBadge();
				awardedBadge.setContext(badge.getContext());
				awardedBadge.setBadgeId(badgeId);
				awardedBadge.setusername(data.getString("recipient"));
				awardedBadge.setStarttime(Calendar.getInstance().getTime());				
				awardedBadge.setEventsRelated(new Text(data.getString("evidence")));
				
				JSONObject completedBadge = new JSONObject();
				completedBadge.put("badge", new JSONObject(badge.getjsonBadge().getValue()));
				completedBadge.put("recipient", data.getString("recipient"));
				completedBadge.put("issued_on", awardedBadge.getStarttime().toString());
				completedBadge.put("evidence", data.getString("evidence"));				
				System.out.println("badge: "+badge.getjsonBadge().getValue());
								
				awardedBadge.setAwardedJSONBadge(new Text(completedBadge.toString()));
				System.out.println("completed: "+completedBadge.toString());
				System.out.println("awarded: "+awardedBadge.getJSON().toString());
				awardedBadge.setNameApp(authorizedKey.getName());
				System.out.println(awardedBadge.getJSON().toString());
				String id = PersistanceLayerAwardedBadge.saveAwardedBadge(awardedBadge);
				result.put("award_id", id);
			}else
				throw new JSONException("Incorrect JSON. Example: "+JSONExamples.awardBadgeExample());
			return result.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return result.toString();
		}
	} 
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/award")
	public String awardBadgeJSONComplete(@HeaderParam("Authorization") String authorization, String json) {	
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		JSONObject result = new JSONObject();
		JSONObject data;
		try {
			data = new JSONObject(json);
			if (data.has("recipient")&&data.has("evidence")){
				AwardedBadge awardedBadge = new AwardedBadge();
				awardedBadge.setContext(data.getString("context"));
				awardedBadge.setBadgeId("null");
				awardedBadge.setusername(data.getString("recipient"));
				awardedBadge.setStarttime(Calendar.getInstance().getTime());				
				awardedBadge.setEventsRelated(new Text(data.getString("evidence")));			
				awardedBadge.setAwardedJSONBadge(new Text(data.toString()));
				awardedBadge.setNameApp(authorizedKey.getName());

				String id = PersistanceLayerAwardedBadge.saveAwardedBadge(awardedBadge);
				result.put("award_id", id);
			}else
				throw new JSONException("Incorrect JSON. Example: "+JSONExamples.awardBadgeExample());
			return result.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return result.toString();
		}
	} 
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/context/{context}")
	public String getBadgebyContext(@HeaderParam("Authorization") String authorization, @PathParam("context") String context, @DefaultValue("") @QueryParam("resumptiontoken") String resumptiontoken)  {	
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		return PersistanceLayerBadge.getbadgeByContext(context, resumptiontoken);
	} 
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/badgeIdentifier/{badgeId}")
	public String getBadgebyId(@HeaderParam("Authorization") String authorization, @PathParam("badgeId") String badgeId) throws JSONException  {	
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		String result = "{}";
		try {
			result = PersistanceLayerBadge.getbadgeById(badgeId).getJSON().toString();
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new JSONException("Some problems with JSON. Probably someone screwed up the code while having a conversation about sex.");
		}		
	} 
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}/awarded")
	public String getAwardedBadgesForUser(@HeaderParam("Authorization") String authorization, @PathParam("userId") String userId, 
			@DefaultValue("1354615295762") @QueryParam("from") String starttime, @DefaultValue("") @QueryParam("resumptiontoken") String resumptiontoken) 
					throws JSONException  {
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		String result = "{}";
		result = PersistanceLayerAwardedBadge.getAwardedBadgeByUsername(userId, starttime, resumptiontoken);
		return result;
	} 
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/badgeId/{badgeId}/awarded")
	public String getAwardedBadgesByBadgeId(@HeaderParam("Authorization") String authorization, @PathParam("badgeId") String badgeId, 
			@DefaultValue("1354615295762") @QueryParam("from") String starttime, @DefaultValue("") @QueryParam("resumptiontoken") String resumptiontoken) 
					throws JSONException  {
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		String result = "{}";
		result = PersistanceLayerAwardedBadge.getAwardedBadgeByBadgeId(badgeId, starttime, resumptiontoken);
		return result;
	} 
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/context/{context}/awarded")
	public String getAwardedBadgesByContext(@HeaderParam("Authorization") String authorization, @PathParam("context") String context, 
			@DefaultValue("1354615295762") @QueryParam("from") String starttime, @DefaultValue("") @QueryParam("resumptiontoken") String resumptiontoken) 
					throws JSONException  {
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		String result = "{}";
		result = PersistanceLayerAwardedBadge.getAwardedBadgeByContext(context, starttime, resumptiontoken);
		return result;
	} 
	
	
}
