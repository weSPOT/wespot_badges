package org.be.kuleuven.hci.openbadges.services;

import java.io.UnsupportedEncodingException;
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

import org.be.kuleuven.hci.openbadges.badges.WeSPOT;
import org.be.kuleuven.hci.openbadges.model.AuthorizedKey;
import org.be.kuleuven.hci.openbadges.model.AwardedBadge;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.model.Issuer;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAuthorizedKey;
import org.be.kuleuven.hci.openbadges.utils.JSONExamples;
import org.be.kuleuven.hci.openbadges.utils.KeyGenerator;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;
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
	@Path("/wespot/{inquiryid}")
	//The JSON should contain an Open Badge +context field
	// Ex:
	//{ "version": "1.0.0", "name": "Explorer", "image": "http://urlofyourbadge.com/testbadgeimage.png", "description": "Has displayed expertise in hand-made badge creation.", "criteria": "http://etec.hawaii.edu"}
	public String createWeSPOTBadge(@HeaderParam("Authorization") String authorization, @PathParam("inquiryid") String inquiryid, String json) {	
		AuthorizedKey authorizedKey = PersistanceLayerAuthorizedKey.isAuthorizedIP(authorization);
		if (authorizedKey == null) throw new UnauthorizedException(); 
		JSONObject result = new JSONObject();		
		try {
			for (int phase=0;phase<=6;phase++){
				for (int level=1; level<=3; level++){
					JSONObject badge = new JSONObject(WeSPOT.weSPOTBadge(inquiryid, authorizedKey, phase, level).getjsonBadge().getValue());
					badge.put("context", WeSPOT.weSPOTBadge(inquiryid, authorizedKey, phase, level).getContext());					
					badge.put("nameApp", WeSPOT.weSPOTBadge(inquiryid, authorizedKey, phase, level).getNameApp());
					log.info(RestClient.doPostAuth(weSPOTBadgesConstants.URL_CREATEBADGES, badge.toString(), authorizedKey.getKeyApp()));	
				}					
			}			
			result.put("message", "ok");			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return e.toString();	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return e.toString();
		}
		return result.toString();
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
}
