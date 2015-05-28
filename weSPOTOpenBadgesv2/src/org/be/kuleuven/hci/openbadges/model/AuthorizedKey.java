package org.be.kuleuven.hci.openbadges.model;

import java.util.Date;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class AuthorizedKey {

	@Id Long id;
	@Index String name;
	@Index String keyApp;
	
	public AuthorizedKey(){
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setKeyApp(String keyApp){
		this.keyApp=keyApp;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getKeyApp(){
		return this.keyApp;
	}

	public JSONObject getJSON(){
		return this.getJSON();
	}
}
