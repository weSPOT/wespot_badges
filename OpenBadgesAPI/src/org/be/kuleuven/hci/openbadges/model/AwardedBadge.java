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
package org.be.kuleuven.hci.openbadges.model;

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class AwardedBadge {

	@Id Long id;
	@Index String username;
	@Index Date starttime;
	@Index String context;
	@Index String badgeId;
	Text awardedJSONBadge;
	@Index String nameApp;
	Text eventsRelated;
	
	public void setContext(String context){
		this.context=context;
	}
	
	public void setAwardedJSONBadge(Text awardedJSONBadge){
		this.awardedJSONBadge=awardedJSONBadge;
	}
	
	public void setNameApp(String nameApp){
		this.nameApp=nameApp;
	}
	
	public void setBadgeId(String badgeId){
		this.badgeId=badgeId;
	}
	
	public String getBadgeId(){
		return this.badgeId;
	}
	
	public void setEventsRelated(Text eventsRelated){
		this.eventsRelated=eventsRelated;
	}
	
	public void setStarttime(Date starttime){
		this.starttime=starttime;
	}
	
	public void setusername(String username){
		this.username=username;
	}
	
	public String getusername(){
		return this.username;
	}
	public Date getStarttime(){
		return this.starttime;
	}
	
	public Text getEventsRelated(){
		return this.eventsRelated;
	}
	
	public String getContext(){
		return this.context;
	}
	
	public Text getjAwardedJSONBadge(){
		return this.awardedJSONBadge;
	}
	
	public String getNameApp(){
		return this.nameApp;
	}
	
	public JSONObject getJSON() throws JSONException{
		JSONObject result = new JSONObject();
		result.put("id", this.id);
		result.put("eventsRelated", this.eventsRelated.getValue());
		result.put("context",this.context);
		result.put("username",this.username);
		result.put("starttime",this.starttime.getTime());
		result.put("jsonBadge",new JSONObject(this.awardedJSONBadge.getValue()));
		result.put("nameApp",this.nameApp);		
		return result;
	}
}
